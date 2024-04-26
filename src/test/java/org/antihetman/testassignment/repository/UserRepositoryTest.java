package org.antihetman.testassignment.repository;

import org.antihetman.testassignment.model.User;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.junit.jupiter.api.BeforeEach;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

/*
  @author   antihetman
  @project   test-assignment
  @class  UserRepositoryTest
  @version  1.0.0 
  @since 4/26/24 - 14.55
*/

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DataMongoTest

class UserRepositoryTest {


        UserRepository underTest;
        @Autowired
        public UserRepositoryTest(UserRepository underTest) {
            this.underTest = underTest;
        }

        @BeforeEach
        void setUp() {
            List<User> items = List.of(
                    new User("test.user1@gmail.com", "John", "Doe", LocalDate.parse("2000-05-20"),"123 Street.","044077099"),
                    new User("test.user2@gmail.com", "Alice", "Smith", LocalDate.parse("1998-12-15"),"456 Avenue.","044077099"),
                    new User("test.user3@gmail.com", "Bob", "Johnson", LocalDate.parse("1995-07-10"),"789 Road.","044077099")


            );
            underTest.saveAll(items);
        }

        @AfterEach
        void tearDown() {
            List<User> items = underTest.findAll()
                    .stream().filter(item -> item.getEmail().contains("test"))
                    .toList();
            underTest.deleteAll(items);
        }

        @Test
        void itShouldCheckTheCollectionIsNotEmpty() {
            assertFalse(underTest.findAll().isEmpty());
            List<User> items = underTest.findAll()
                    .stream().filter(it -> it.getEmail().contains("test"))
                    .toList();
            assertEquals(items.size(), 3);
        }
        @Test
        void itShouldSaveItem(){
            //given
            User user = new User("test.user5@gmail.com", "Michael", "Wilson", LocalDate.parse("1997-09-03"),"321 Boulevard.","044077099");

            //when
            underTest.save(user);
            //then
            User testUser = underTest.findAll().stream()
                    .filter(it -> it.getEmail().contains("test"))
                    .findAny()
                    .orElse(null);
            assertNotNull(testUser);
            assertNotNull(testUser.getId());
            assertFalse(testUser.getId().isEmpty());

        }
        @Test
        void itShouldDeleteItem(){
            //given
            User user = new  User("test.deleted.user4@gmail.com", "Emily", "Brown", LocalDate.parse("1993-04-28"),"987 Lane.","044077099");

            underTest.save(user);
            //when
            underTest.delete(user);
            //then
            User testUser = underTest.findAll().stream()
                    .filter(it -> it.getEmail().contains("deleted"))
                    .findAny().orElse(null);
            assertNull(testUser);
        }
        @Test
        void itShouldFindUsersWhichHaveBornBetween1994And1999(){
            List<User> users = underTest.findAllByBirthDateBetween(LocalDate.parse("1994-01-01"),LocalDate.parse("1999-01-01"));
            Optional<User> user = users.stream().filter(el -> el.getBirthDate().equals(LocalDate.parse("2000-05-20"))).findAny();
            System.out.println(user);
            assertEquals("Optional.empty", user.toString());
            assertNotNull(users);
        }

}