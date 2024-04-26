package org.antihetman.testassignment.repository;/*
  @author   antihetman
  @project   test-assignment
  @class  d
  @version  1.0.0 
  @since 4/26/24 - 13.45
*/

import org.antihetman.testassignment.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface UserRepository extends MongoRepository<User, String> {
    List<User> findAllByBirthDateBetween(LocalDate from, LocalDate to);
}