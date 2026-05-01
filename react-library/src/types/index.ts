// Represents the status of a book as a type-safe string union.
// This is an "erasable" type that satisfies strict TypeScript rules.
export type BookStatus = 'AVAILABLE' | 'SOLD';

// We can also export the values for convenience in our components.
export const BookStatusValues = {
    AVAILABLE: 'AVAILABLE' as const,
    SOLD: 'SOLD' as const,
};

// Represents the structure of a Book object
export interface Book {
    id?: number;
    title: string;
    author: string;
    isbn: string;
    price: number;
    status: BookStatus;
}

// Represents the paginated response from the Spring Boot API
export interface Page<T> {
    content: T[];
    totalElements: number;
    totalPages: number;
    size: number;
    number: number; // Current page number
}
