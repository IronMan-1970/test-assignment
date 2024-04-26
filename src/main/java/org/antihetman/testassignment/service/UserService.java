package org.antihetman.testassignment.service;/*
  @author   antihetman
  @project   test-assignment
  @class  userService
  @version  1.0.0 
  @since 4/26/24 - 11.54
*/

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import org.antihetman.testassignment.model.User;
import org.antihetman.testassignment.repository.UserRepository;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.convert.MongoConverter;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import java.time.LocalDate;
import java.util.List;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@Service
public class UserService {


    private final MongoConverter mongoConverter;
    private final UserRepository userRepository;
    private final MongoTemplate mongoTemplate;
    @Autowired
    public UserService(UserRepository userRepository, MongoTemplate mongoTemplate, MongoConverter mongoConverter) {
        this.userRepository = userRepository;
        this.mongoTemplate = mongoTemplate;
        this.mongoConverter = mongoConverter;
    }
    @Value("${user.age.limit}")
    private static int userAgeLimit;

    public static boolean isValidEmailAddress(String email) {
        boolean result = true;
        try {
            InternetAddress emailAddr = new InternetAddress(email);
            emailAddr.validate();
        } catch (AddressException ex) {
            result = false;
        }
        return result;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(String id) {
        User user =  userRepository.findById(id).orElse(null);
        if (user == null)
            throw new RuntimeException("User not found");
        return user;
    }

    public User createUser(User user) {

        checkIfRequiredFieldsIsNotNull(user);
        return  userRepository.save(user);
    }

    private static void checkIfRequiredFieldsIsNotNull(User user) {
        if (user.getFirstName() == null || user.getLastName() == null)
            throw new IllegalArgumentException("First name and last name must not be null");
        if (user.getEmail() == null || user.getEmail().isEmpty())
            throw new IllegalArgumentException("Email must not be empty");
        if (user.getBirthDate() == null)
            throw new IllegalArgumentException("Birth date must not be null");
        checkUserBirthDate(user);
        boolean isEmailAddressValid = isValidEmailAddress(user.getEmail());
        if (!isEmailAddressValid)
            throw new IllegalArgumentException("Invalid email address");
    }

    private static void checkUserBirthDate(User user) {

        if (user.getBirthDate().equals(LocalDate.now())) {
            throw new IllegalArgumentException("birthDate cannot be today");
        }
        if ((user.getBirthDate().getYear()-LocalDate.now().getYear() < userAgeLimit)
                && (user.getBirthDate().getMonth().getValue() - LocalDate.now().getMonth().getValue() < 0)
                && (user.getBirthDate().getDayOfMonth() - LocalDate.now().getDayOfMonth() < 0)) {
            throw new IllegalArgumentException("Her/his age cannot be less than 18 y.o.");
        }

    }

    public void deleteUser(String id) {
        userRepository.deleteById(id);
    }

    public List<User> getAllUsersByBirthDateBetweenTwoDates(LocalDate from, LocalDate to) {
        return userRepository.findAllByBirthDateBetween(from, to);
    }

    public User updateUser(String id, User user) {
        User existingUser = userRepository.findById(id).orElse(null);
        if (existingUser == null)
            throw new RuntimeException("User not found");
        if (user.getBirthDate() != null)
            checkUserBirthDate(user);
        if (user.getEmail() != null) {
            boolean isEmailAddressValid = isValidEmailAddress(user.getEmail());
            if (!isEmailAddressValid)
                throw new IllegalArgumentException("Invalid email address");
        }
        user.setId(id);
        DBObject update = getDbObject(user);
        mongoTemplate.updateFirst(query(where("id").is(id)), Update.fromDocument(new Document("$set", update)).push("events", user), User.class);
        mongoTemplate.findOne(query(where("id").is(id)), User.class);
        User updatedUser = userRepository.findById(id).orElse(null);
        return updatedUser;
    }
    private DBObject getDbObject(Object o) {
        BasicDBObject basicDBObject = new BasicDBObject();
        mongoConverter.write(o, basicDBObject);
        return basicDBObject;
    }
}
