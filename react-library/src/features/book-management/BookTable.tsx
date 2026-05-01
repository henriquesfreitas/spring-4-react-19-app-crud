import React from 'react';
import type { Book } from '../../types';

interface BookTableProps {
    books: Book[];
    onEdit: (book: Book) => void;
    onDelete: (id: number) => void;
    onBuy: (id: number) => void;
}

const BookTable: React.FC<BookTableProps> = ({ books, onEdit, onDelete, onBuy }) => {
    return (
        <div className="overflow-x-auto shadow-md sm:rounded-lg">
            <table className="w-full text-sm text-left text-gray-500">
                <thead className="text-xs text-gray-700 uppercase bg-gray-50">
                    <tr>
                        <th scope="col" className="px-6 py-3">Title</th>
                        <th scope="col" className="px-6 py-3">Author</th>
                        <th scope="col" className="px-6 py-3">ISBN</th>
                        <th scope="col" className="px-6 py-3">Price</th>
                        <th scope="col" className="px-6 py-3">Status</th>
                        <th scope="col" className="px-6 py-3">Actions</th>
                    </tr>
                </thead>
                <tbody>
                    {books.map((book) => (
                        <tr key={book.id} className="bg-white border-b hover:bg-gray-50">
                            <th scope="row" className="px-6 py-4 font-medium text-gray-900 whitespace-nowrap">
                                {book.title}
                            </th>
                            <td className="px-6 py-4">{book.author}</td>
                            <td className="px-6 py-4">{book.isbn}</td>
                            <td className="px-6 py-4">${book.price.toFixed(2)}</td>
                            <td className="px-6 py-4">
                                <span className={`px-2 inline-flex text-xs leading-5 font-semibold rounded-full ${
                                    book.status === 'AVAILABLE' ? 'bg-green-100 text-green-800' : 'bg-red-100 text-red-800'
                                }`}>
                                    {book.status}
                                </span>
                            </td>
                            <td className="px-6 py-4 space-x-2">
                                <button onClick={() => onEdit(book)} className="font-medium text-blue-600 hover:underline">Edit</button>
                                <button onClick={() => onDelete(book.id!)} className="font-medium text-red-600 hover:underline">Delete</button>
                                {book.status === 'AVAILABLE' && (
                                    <button onClick={() => onBuy(book.id!)} className="font-medium text-green-600 hover:underline">Buy</button>
                                )}
                            </td>
                        </tr>
                    ))}
                </tbody>
            </table>
        </div>
    );
};

export default BookTable;
