package com.dylankilbride.grouppay.repositories;

import com.dylankilbride.grouppay.models.GroupAccount;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.math.BigInteger;
import java.util.List;

public interface GroupAccountRepository extends CrudRepository<GroupAccount, Integer> {

 GroupAccount findByGroupAccountId(long accountId);

 @Query(value = "SELECT group_account_id FROM group_account_users WHERE user_id = ?1", nativeQuery = true)
 List<BigInteger> getUserAssociatedAccounts(long userID);
}