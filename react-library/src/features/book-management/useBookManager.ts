import { useState, useEffect } from 'react';
import * as BookService from '../../services/BookService';
import * as WebSocketService from '../../services/WebSocketService';
import type { Book } from '../../types';
import { BookStatusValues } from '../../types';

interface BookUpdateMessage {
    bookId: number;
    status: 'SUCCESS' | 'FAILURE';
    error?: string;
}

export const useBookManager = () => {
    const [books, setBooks] = useState<Book[]>([]);
    const [currentBook, setCurrentBook] = useState<Book | null>(null);
    const [isDialogOpen, setDialogOpen] = useState<boolean>(false);
    const [page, ] = useState<number>(0); // setPage is unused for now
    const [, setTotalElements] = useState<number>(0); // totalElements is unused for now
    const pageSize = 5;

    const fetchBooks = async () => {
        try {
            const data = await BookService.getBooksPaginated(page, pageSize);
            // ✅ DEFENSIVE FIX: Use optional chaining and a fallback to an empty array.
            // This ensures that `setBooks` is ALWAYS called with an array, even if `data`
            // is undefined or `data.content` is missing.
            setBooks(data?.content || []);
            setTotalElements(data?.totalElements || 0);
        } catch (error) {
            console.error("Failed to fetch books:", error);
            // Also set to an empty array on error to prevent crashes.
            setBooks([]);
            setTotalElements(0);
        }
    };

    useEffect(() => {
        fetchBooks();
    }, [page]);

    useEffect(() => {
        const handleBookUpdate = (message: BookUpdateMessage) => {
            if (message.status === 'SUCCESS') {
                setBooks(currentBooks =>
                    currentBooks.map(book =>
                        book.id === message.bookId ? { ...book, status: BookStatusValues.SOLD } : book
                    )
                );
            } else {
                alert(`Failed to purchase book (ID: ${message.bookId}): ${message.error}`);
            }
        };

        WebSocketService.connect(handleBookUpdate);
        return () => {
            WebSocketService.disconnect();
        };
    }, []);

    const handleCreate = () => {
        setCurrentBook({ title: '', author: '', isbn: '', price: 0, status: BookStatusValues.AVAILABLE });
        setDialogOpen(true);
    };

    const handleEdit = (book: Book) => {
        setCurrentBook(book);
        setDialogOpen(true);
    };

    const handleSave = async (book: Book) => {
        try {
            await BookService.saveBook(book);
            setDialogOpen(false);
            setCurrentBook(null);
            fetchBooks();
        } catch (error) {
            console.error("Failed to save book:", error);
        }
    };

    const handleCancel = () => {
        setDialogOpen(false);
        setCurrentBook(null);
    };

    const handleDelete = async (id: number) => {
        if (window.confirm('Are you sure you want to delete this book?')) {
            try {
                await BookService.deleteBook(id);
                fetchBooks();
            } catch (error) {
                console.error("Failed to delete book:", error);
            }
        }
    };

    const handleBuy = async (id: number) => {
        if (window.confirm('Are you sure you want to purchase this book?')) {
            try {
                await BookService.buyBook(id);
            } catch (error) {
                console.error("Failed to send buy request:", error);
                alert("Could not send the purchase request. Please check your connection.");
            }
        }
    };

    return {
        books,
        currentBook,
        isDialogOpen,
        handleCreate,
        handleEdit,
        handleSave,
        handleCancel,
        handleDelete,
        handleBuy,
    };
};
