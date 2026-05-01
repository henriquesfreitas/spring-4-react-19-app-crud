import React from 'react';
import { useBookManager } from './useBookManager';
import BookTable from './BookTable';
import BookDialog from './BookDialog';

/**
 * A "presentational" component for the book management feature.
 */
const BookManager: React.FC = () => {
    const {
        books,
        currentBook,
        isDialogOpen,
        handleCreate,
        handleEdit,
        handleSave,
        handleCancel,
        handleDelete,
        handleBuy,
    } = useBookManager();

    return (
        <div className="p-6 md:p-8">
            <div className="flex justify-between items-center mb-6">
                <h1 className="text-3xl font-bold text-gray-800">Book Management meu deus</h1>
                <button 
                    onClick={handleCreate} 
                    className="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded focus:outline-none focus:shadow-outline"
                >
                    Create New Book
                </button>
            </div>
            
            <BookTable 
                books={books} 
                onEdit={handleEdit} 
                onDelete={handleDelete} 
                onBuy={handleBuy} 
            />
            
            {/* We will add pagination controls here later */}

            {isDialogOpen && currentBook && (
                <BookDialog 
                    book={currentBook} 
                    onSave={handleSave} 
                    onCancel={handleCancel} 
                />
            )}
        </div>
    );
};

export default BookManager;
