package com.library.springapilibrary.config;

import com.library.springapilibrary.entity.Book;
import com.library.springapilibrary.entity.BookStatus;
import com.library.springapilibrary.entity.User;
import com.library.springapilibrary.repository.BookRepository;
import com.library.springapilibrary.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

/**
 * Component to seed the database with initial data on application startup.
 */
@Component
public class DatabaseSeeder implements CommandLineRunner {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        // Seed users if the user table is empty
        if (userRepository.count() == 0) {
            System.out.println("User database is empty. Seeding initial user...");
            seedUserTable();
        }

        // Seed books if the book table is empty
        if (bookRepository.count() == 0) {
            System.out.println("Book database is empty. Seeding initial books...");
            seedBookTable();
        }

        if (userRepository.count() > 0 && bookRepository.count() > 0) {
            System.out.println("Database already contains data. Skipping seeding.");
        }
    }

    private void seedUserTable() {
        User adminUser = new User();
        adminUser.setUsername("admin");
        // IMPORTANT: Always encode passwords before saving them
        adminUser.setPassword(passwordEncoder.encode("password"));
        adminUser.setRoles(Set.of("ROLE_ADMIN", "ROLE_USER"));
        userRepository.save(adminUser);
        System.out.println("Default admin user created (admin/password)");
    }

    private void seedBookTable() {
        List<Book> books = Arrays.asList(
            createBook("The Lord of the Rings", "J.R.R. Tolkien", "9780618640157", 39.99, BookStatus.AVAILABLE),
            createBook("Pride and Prejudice", "Jane Austen", "9780141439518", 14.99, BookStatus.AVAILABLE),
            createBook("To Kill a Mockingbird", "Harper Lee", "9780061120084", 18.50, BookStatus.SOLD),
            createBook("1984", "George Orwell", "9780451524935", 15.75, BookStatus.AVAILABLE),
            createBook("The Great Gatsby", "F. Scott Fitzgerald", "9780743273565", 12.00, BookStatus.AVAILABLE)
        );
        bookRepository.saveAll(books);
        System.out.println("Sample books created.");
    }

    private Book createBook(String title, String author, String isbn, double price, BookStatus status) {
        Book book = new Book();
        book.setTitle(title);
        book.setAuthor(author);
        book.setIsbn(isbn);
        book.setPrice(price);
        book.setStatus(status);
        return book;
    }
}
