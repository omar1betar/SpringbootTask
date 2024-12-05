package com.neoleaptask.NeoleapTask.repository;

import com.neoleaptask.NeoleapTask.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

}
