package com.dylankilbride.grouppay.Repositories;

import com.dylankilbride.grouppay.Models.GroupAccount;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.List;

public interface GroupAccountRepository extends CrudRepository<GroupAccount, Integer> {

 GroupAccount findByGroupAccountId(long accountId);

 @Query(value = "SELECT group_account_id FROM group_account_users WHERE user_id = ?1", nativeQuery = true)
 List<BigInteger> getUserAssociatedAccounts(long userID);

 @Transactional
 @Modifying
 @Query(value = "INSERT INTO group_account_users (group_account_id, user_id) VALUES (?1, ?2)", nativeQuery = true)
 int addUsersToGroupAccount(long accountId, long userId);
}