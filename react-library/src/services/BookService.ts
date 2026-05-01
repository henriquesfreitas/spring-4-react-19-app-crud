import apiClient from '../api/apiClient';
import type { Book, Page } from '../types';

/**
 * Fetches a paginated list of books from the API.
 * The apiClient will automatically add the auth token.
 */
export const getBooksPaginated = async (page: number = 0, size: number = 10): Promise<Page<Book>> => {
    const response = await apiClient.get<Page<Book>>(`/books/page?page=${page}&size=${size}`);
    return response.data;
};

/**
 * Fetches a single book by its ID.
 */
export const getBookById = async (id: number): Promise<Book> => {
    const response = await apiClient.get<Book>(`/books/${id}`);
    return response.data;
};

/**
 * Creates a new book or updates an existing one.
 */
export const saveBook = async (book: Book): Promise<Book> => {
    if (book.id) {
        // Update existing book
        const response = await apiClient.put<Book>(`/books/${book.id}`, book);
        return response.data;
    } else {
        // Create new book
        const response = await apiClient.post<Book>('/books', book);
        return response.data;
    }
};

/**
 * Deletes a book by its ID.
 */
export const deleteBook = async (id: number): Promise<void> => {
    await apiClient.delete(`/books/${id}`);
};

/**
 * Triggers the 'buy' action for a book.
 */
export const buyBook = async (id: number): Promise<string> => {
    const response = await apiClient.post<string>(`/books/${id}/buy`);
    return response.data;
};
