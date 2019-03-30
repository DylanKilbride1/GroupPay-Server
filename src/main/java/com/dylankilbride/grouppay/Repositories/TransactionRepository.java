package com.dylankilbride.grouppay.Repositories;

import com.dylankilbride.grouppay.Models.GroupAccount;
import com.dylankilbride.grouppay.Models.Transaction;
import com.dylankilbride.grouppay.Models.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TransactionRepository extends CrudRepository<Transaction, Integer> {

	List<Transaction> findTransactionByGroupAccount(GroupAccount groupAccount);
	List<Transaction> findTransactionByUser(User user);

//	@Query(value = "SELECT * FROM transaction WHERE group_id = 1?", nativeQuery = true)
//	List<Transaction> findTransactionByGAID(long groupAccountID);
}
