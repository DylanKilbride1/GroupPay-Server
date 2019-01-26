package com.dylankilbride.grouppay.repositories;

import com.dylankilbride.grouppay.entities.Users;
import org.springframework.data.repository.CrudRepository;

public interface UsersRepository extends CrudRepository<Users, Integer> { //JPA or CRUD repo?

	boolean existsByEmailAddress(String emailAddress);
	boolean existsByPassword(String password);
	Users findUsersByEmailAddress(String emailAddress);
	Users findUsersById(long id);
}