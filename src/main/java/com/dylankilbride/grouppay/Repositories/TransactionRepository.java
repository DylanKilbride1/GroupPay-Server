package com.dylankilbride.grouppay.Repositories;

import com.dylankilbride.grouppay.Models.Transaction;
import org.springframework.data.repository.CrudRepository;

public interface TransactionRepository extends CrudRepository<Transaction, Integer> {
}
