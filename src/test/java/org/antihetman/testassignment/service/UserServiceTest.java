package org.antihetman.testassignment.service;

import org.antihetman.testassignment.model.User;
import org.antihetman.testassignment.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.*;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.convert.MongoConverter;

import java.time.LocalDate;

/*
  @author   antihetman
  @project   test-assignment
  @class  userServiceTest
  @version  1.0.0 
  @since 4/26/24 - 12.21
*/
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DataMongoTest
class UserServiceTest {

    @Mock
    private UserRepository mockRepository;
    @Mock
    private MongoTemplate mockmongoTemplate;
    @Mock
    private MongoConverter mockMongoConverter;
    private UserService underTest;
    User user = new User();
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        this.underTest = new UserService(this.mockRepository, this.mockmongoTemplate, this.mockMongoConverter);
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setEmail("john.doe@gmail.com");
        user.setAddress("123 Main St");
        user.setPhoneNumber("0123456789");
        user.setBirthDate(LocalDate.parse("1999-12-29"));
    }


    @Test
    void CheckIfUserOlderThan18YearsOldWhenWeCreateNewObject(){
        user.setBirthDate(LocalDate.parse("2011-02-03"));
        Exception exception = Assertions.assertThrows(IllegalArgumentException.class, ()-> {
            underTest.createUser(user);
        });
        Assertions.assertSame(IllegalArgumentException.class ,exception.getClass());
    }
    @Test
    void CheckIfUserOlderThan18YearsOldWhenWeUpdateObject(){
        user.setBirthDate(LocalDate.parse("2011-02-03"));
        Exception exception = Assertions.assertThrows(IllegalArgumentException.class, ()-> {
            underTest.updateUser(user.getId(), user);
        });
        Assertions.assertSame(IllegalArgumentException.class ,exception.getClass());
    }

    @Test
    void CheckIfUserBirthDateIsNotEqualCurrentDateWhenWeCreateNewObject(){
        user.setBirthDate(LocalDate.now());
        Exception exception = Assertions.assertThrows(IllegalArgumentException.class, ()-> {
            underTest.createUser(user);
        });
        Assertions.assertSame(IllegalArgumentException.class ,exception.getClass());
    }
    @Test
    void CheckIfUserBirthDateIsNotEqualCurrentDateWhenWeUpdateObject(){
        user.setBirthDate(LocalDate.now());
        Exception exception = Assertions.assertThrows(IllegalArgumentException.class, ()-> {
            underTest.updateUser(user.getId(), user);
        });
        Assertions.assertSame(IllegalArgumentException.class ,exception.getClass());
    }

    @Test
    void CheckIfUserEmailFollowsEmailPatternWhenWeUpdateObject(){
        user.setEmail("@sdfd/gmail.com");
        user.setBirthDate(LocalDate.now());
        Exception exception = Assertions.assertThrows(IllegalArgumentException.class, ()-> {
            underTest.updateUser(user.getId(), user);
        });
        Assertions.assertSame(IllegalArgumentException.class ,exception.getClass());
    }

    @Test
    void CheckIfUserEmailFollowsEmailPatternWhenWeCreateNewObject(){
        user.setEmail("@sdfd/gmail.com");
        user.setBirthDate(LocalDate.now());
        Exception exception = Assertions.assertThrows(IllegalArgumentException.class, ()-> {
            underTest.createUser(user);
        });
        Assertions.assertSame(IllegalArgumentException.class ,exception.getClass());
    }

    @Test
    void CheckIfFirstNameIsNotNullWhenWeCreateNewObject(){
        User nullFirstNameUser = new User();
        nullFirstNameUser.setLastName("Doe");
        nullFirstNameUser.setEmail("john.doe@gmail.com");
        nullFirstNameUser.setBirthDate(LocalDate.parse("1999-12-29"));
        Exception exception = Assertions.assertThrows(IllegalArgumentException.class, ()-> {
            underTest.createUser(nullFirstNameUser);
        });
        Assertions.assertSame(IllegalArgumentException.class ,exception.getClass());
    }
    @Test
    void CheckIfLastNameIsNotNullWhenWeCreateNewObject(){
        User nullLastNameUser = new User();
        nullLastNameUser.setFirstName("John");
        nullLastNameUser.setEmail("john.doe@gmail.com");
        nullLastNameUser.setBirthDate(LocalDate.parse("1999-12-29"));
        Exception exception = Assertions.assertThrows(IllegalArgumentException.class, ()-> {
            underTest.createUser(nullLastNameUser);
        });
        Assertions.assertSame(IllegalArgumentException.class ,exception.getClass());
    }
    @Test
    void CheckIfEmailIsNotNullWhenWeCreateNewObject(){
        User nullEmailUser = new User();
        nullEmailUser.setFirstName("John");
        nullEmailUser.setLastName("Doe");
        nullEmailUser.setBirthDate(LocalDate.parse("1999-12-29"));
        Exception exception = Assertions.assertThrows(IllegalArgumentException.class, ()-> {
            underTest.createUser(nullEmailUser);
        });
        Assertions.assertSame(IllegalArgumentException.class ,exception.getClass());
    }
    @Test
    void CheckIfBirthDateIsNotNullWhenWeCreateNewObject(){
        User nullBirthDateUser = new User();
        nullBirthDateUser.setFirstName("John");
        nullBirthDateUser.setLastName("Doe");
        nullBirthDateUser.setEmail("john.doe@gmail.com");
        Exception exception = Assertions.assertThrows(IllegalArgumentException.class, ()-> {
            underTest.createUser(nullBirthDateUser);
        });
        Assertions.assertSame(IllegalArgumentException.class ,exception.getClass());
    }

}