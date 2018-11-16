package com.dylankilbride.grouppay.repositories;

import com.dylankilbride.grouppay.entities.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UsersRepository extends JpaRepository<Users, Integer> {

	boolean existsByEmailAddress(String emailAddress);

	String findByEmailAddress(String emailAddress);
}
