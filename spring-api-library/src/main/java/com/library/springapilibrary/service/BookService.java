package com.library.springapilibrary.service;

import com.library.springapilibrary.config.properties.AppProperties;
import com.library.springapilibrary.dto.BookDTO;
import com.library.springapilibrary.entity.Book;
import com.library.springapilibrary.entity.BookStatus;
import com.library.springapilibrary.mapper.BookMapper;
import com.library.springapilibrary.repository.BookRepository;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private BookMapper bookMapper;

    @Autowired
    private RabbitTemplate rabbitTemplate;
    
    @Autowired
    private AppProperties appProperties;

    @Transactional(readOnly = true)
    public Page<BookDTO> getBooks(int page, int size) {
        return bookRepository.findAll(PageRequest.of(page, size))
                .map(bookMapper::toDTO);
    }

    @Transactional(readOnly = true)
    public List<BookDTO> getAllBooks() {
        return bookRepository.findAll().stream()
                .map(bookMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Optional<BookDTO> findById(Long id) {
        return bookRepository.findById(id).map(bookMapper::toDTO);
    }

    @Transactional
    public BookDTO saveBook(BookDTO bookDTO) {
        Book bookToSave;
        if (bookDTO.getId() != null) {
            bookToSave = bookRepository.findById(bookDTO.getId())
                    .orElseThrow(() -> new RuntimeException("Book not found"));
            bookMapper.updateEntityFromDTO(bookDTO, bookToSave);
        } else {
            bookToSave = bookMapper.toEntity(bookDTO);
        }
        Book savedBook = bookRepository.save(bookToSave);
        return bookMapper.toDTO(savedBook);
    }

    @Transactional
    public void deleteBook(Long id) {
        if (!bookRepository.existsById(id)) {
            throw new RuntimeException("Cannot delete. Book not found with ID: " + id);
        }
        bookRepository.deleteById(id);
    }

    public void queueBookPurchase(Long bookId) {
        rabbitTemplate.convertAndSend(
            appProperties.getRabbitmq().getExchangeName(), 
            appProperties.getRabbitmq().getPurchaseRoutingKey(), 
            bookId
        );
        System.out.println("Book purchase request for ID: " + bookId + " sent to queue.");
    }

    @Transactional
    public void processBookPurchase(Long bookId) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new RuntimeException("Book not found with ID: " + bookId));

        if (book.getStatus() == BookStatus.SOLD) {
            throw new RuntimeException("Book with ID: " + bookId + " is already sold.");
        }

        book.setStatus(BookStatus.SOLD);
        bookRepository.save(book);
    }

    @Transactional(readOnly = true)
    public long countBooks() {
        return bookRepository.count();
    }
}
