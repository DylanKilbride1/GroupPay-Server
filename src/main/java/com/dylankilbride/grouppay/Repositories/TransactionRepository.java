package com.dylankilbride.grouppay.Repositories;

import com.dylankilbride.grouppay.Models.Transaction;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TransactionRepository extends CrudRepository<Transaction, Integer> {

	List<Transaction> findTransactionByGroupId(long groupId);
	List<Transaction> findTransactionByUserId(long userId);

//	@Query(value = "SELECT * FROM transaction WHERE group_id = 1?", nativeQuery = true)
//	List<Transaction> findTransactionByGAID(long groupAccountID);
}
