package com.library.springapilibrary.service;

import com.library.springapilibrary.config.properties.AppProperties;
import com.library.springapilibrary.dto.BookDTO;
import com.library.springapilibrary.entity.Book;
import com.library.springapilibrary.entity.BookStatus;
import com.library.springapilibrary.mapper.BookMapper;
import com.library.springapilibrary.repository.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @Mock
    private BookMapper bookMapper;

    @Mock
    private RabbitTemplate rabbitTemplate;

    @Mock
    private AppProperties appProperties;

    @InjectMocks
    private BookService bookService;

    private Book book;
    private BookDTO bookDTO;

    @BeforeEach
    void setUp() {
        book = new Book();
        book.setId(1L);
        book.setTitle("Test Book");
        book.setAuthor("Test Author");
        book.setStatus(BookStatus.AVAILABLE);

        bookDTO = new BookDTO();
        bookDTO.setId(1L);
        bookDTO.setTitle("Test Book DTO");
        bookDTO.setAuthor("Test Author DTO");
        bookDTO.setStatus(BookStatus.AVAILABLE);
    }

    @Test
    @DisplayName("Should return a paginated list of books")
    void getBooks_shouldReturnPaginatedBooks() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Book> bookPage = new PageImpl<>(List.of(book), pageable, 1);
        when(bookRepository.findAll(pageable)).thenReturn(bookPage);
        when(bookMapper.toDTO(any(Book.class))).thenReturn(bookDTO);

        Page<BookDTO> result = bookService.getBooks(0, 10);

        assertEquals(1, result.getTotalElements());
        verify(bookRepository).findAll(pageable);
    }

    @Test
    @DisplayName("Should find a book by ID")
    void findById_shouldReturnBookDTO() {
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        when(bookMapper.toDTO(book)).thenReturn(bookDTO);

        Optional<BookDTO> result = bookService.findById(1L);

        assertTrue(result.isPresent());
        assertEquals(bookDTO.getTitle(), result.get().getTitle());
    }

    @Test
    @DisplayName("Should create a new book")
    void saveBook_shouldCreateNewBook() {
        BookDTO newBookDTO = new BookDTO(); // No ID
        Book newBookEntity = new Book();
        when(bookMapper.toEntity(newBookDTO)).thenReturn(newBookEntity);
        when(bookRepository.save(newBookEntity)).thenReturn(newBookEntity);
        when(bookMapper.toDTO(newBookEntity)).thenReturn(newBookDTO);

        bookService.saveBook(newBookDTO);

        verify(bookMapper).toEntity(newBookDTO);
        verify(bookRepository).save(newBookEntity);
    }

    @Test
    @DisplayName("Should update an existing book")
    void saveBook_shouldUpdateExistingBook() {
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        when(bookRepository.save(book)).thenReturn(book);
        when(bookMapper.toDTO(book)).thenReturn(bookDTO);

        bookService.saveBook(bookDTO);

        verify(bookRepository).findById(1L);
        verify(bookMapper).updateEntityFromDTO(bookDTO, book);
        verify(bookRepository).save(book);
    }

    @Test
    @DisplayName("Should throw exception when updating non-existent book")
    void saveBook_shouldThrowExceptionForNonExistentBook() {
        BookDTO nonExistentBookDTO = new BookDTO();
        nonExistentBookDTO.setId(99L);
        when(bookRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> bookService.saveBook(nonExistentBookDTO));
    }

    @Test
    @DisplayName("Should delete a book by ID")
    void deleteBook_shouldDeleteBook() {
        when(bookRepository.existsById(1L)).thenReturn(true);
        doNothing().when(bookRepository).deleteById(1L);

        bookService.deleteBook(1L);

        verify(bookRepository).deleteById(1L);
    }

    @Test
    @DisplayName("Should throw exception when deleting non-existent book")
    void deleteBook_shouldThrowExceptionForNonExistentBook() {
        when(bookRepository.existsById(99L)).thenReturn(false);

        assertThrows(RuntimeException.class, () -> bookService.deleteBook(99L));
        verify(bookRepository, never()).deleteById(anyLong());
    }

    @Test
    @DisplayName("Should queue a book purchase message")
    void queueBookPurchase_shouldSendToRabbitMQ() {
        // Arrange: Setup the mock specifically for this test
        AppProperties.RabbitMQ rabbitMQProperties = new AppProperties.RabbitMQ();
        rabbitMQProperties.setExchangeName("test-exchange");
        rabbitMQProperties.setPurchaseRoutingKey("test.purchase");
        when(appProperties.getRabbitmq()).thenReturn(rabbitMQProperties);
        Long bookId = 1L;

        // Act
        bookService.queueBookPurchase(bookId);

        // Assert
        verify(rabbitTemplate).convertAndSend(
                "test-exchange",
                "test.purchase",
                bookId
        );
    }

    @Test
    @DisplayName("Should process a book purchase successfully")
    void processBookPurchase_shouldSetStatusToSold() {
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        when(bookRepository.save(book)).thenReturn(book);

        bookService.processBookPurchase(1L);

        assertEquals(BookStatus.SOLD, book.getStatus());
        verify(bookRepository).save(book);
    }

    @Test
    @DisplayName("Should throw exception if book is already sold")
    void processBookPurchase_shouldThrowExceptionIfBookAlreadySold() {
        book.setStatus(BookStatus.SOLD);
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));

        assertThrows(RuntimeException.class, () -> bookService.processBookPurchase(1L));
        verify(bookRepository, never()).save(any(Book.class));
    }
}
