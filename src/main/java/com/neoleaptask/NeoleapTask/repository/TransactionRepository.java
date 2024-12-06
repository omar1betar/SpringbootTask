package com.neoleaptask.NeoleapTask.repository;

import com.neoleaptask.NeoleapTask.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}
