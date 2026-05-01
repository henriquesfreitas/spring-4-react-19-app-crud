package com.library.springapilibrary.messaging;

import com.library.springapilibrary.dto.BookStatusUpdateDTO;
import com.library.springapilibrary.service.BookService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
public class BookPurchaseListener {

    @Autowired
    private BookService bookService;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    /**
     * Handles the book purchase message from the RabbitMQ queue.
     * The queue name is now read directly from application.properties via SpEL.
     *
     * @param bookId The ID of the book to be purchased.
     */
    @RabbitListener(queues = "${app.rabbitmq.purchase-queue-name}")
    public void handleBookPurchase(Long bookId) {
        System.out.println("Received book purchase request for ID: " + bookId + ". Pausing for 5 seconds for debugging...");
        try {
            // --- DEBUGGING DELAY ---
            // This is a simple way to make the message visible in the RabbitMQ admin UIhttp://localhost:15672/#/queues.
            // It forces the consumer to wait for 5 seconds before processing.
            // DO NOT use this in production code.
            Thread.sleep(10000);

            bookService.processBookPurchase(bookId);
            System.out.println("Successfully processed purchase for book ID: " + bookId);

            BookStatusUpdateDTO successPayload = new BookStatusUpdateDTO(bookId, "SUCCESS");
            messagingTemplate.convertAndSend("/topic/book-updates", successPayload);

        } catch (Exception e) {
            System.err.println("Failed to process purchase for book ID: " + bookId + ". Error: " + e.getMessage());

            BookStatusUpdateDTO failurePayload = new BookStatusUpdateDTO(bookId, "FAILURE", e.getMessage());
            messagingTemplate.convertAndSend("/topic/book-updates", failurePayload);
        }
    }
}
