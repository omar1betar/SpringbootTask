package com.neoleaptask.NeoleapTask.service.impl;

import com.neoleaptask.NeoleapTask.model.Transaction;
import com.neoleaptask.NeoleapTask.repository.TransactionRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
@Service
public class PaymentListenerImpl {

    private final TransactionRepository transactionRepository;


    public PaymentListenerImpl(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @RabbitListener(queues = "paymentQueue")
    public void handlePaymentMessage(String message) {
        // Parse the message
        String[] parts = message.split(" ");
        BigDecimal amount = new BigDecimal(parts[2]);  // Extract the amount
        Long orderId = Long.parseLong(parts[6]); // Extract the order ID

        // Create a new transaction record
        Transaction transaction = new Transaction(orderId, amount);
        transactionRepository.save(transaction);

        System.out.println("Processed payment: Order ID = " + orderId + ", Amount = " + amount);
    }
}
