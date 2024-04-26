package org.antihetman.testassignment.controller;/*
  @author   antihetman
  @project   test-assignment
  @class  UserController
  @version  1.0.0 
  @since 4/26/24 - 12.27
*/

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import org.antihetman.testassignment.model.User;
import org.antihetman.testassignment.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/Users/")
public class UserController {

    private final UserService service;
    @Autowired
    public UserController(UserService service) {
        this.service = service;
    }
    @GetMapping
    public List<User> getAllUsers() {
        return service.getAllUsers();
    }
    @GetMapping("Born/From/{from}/To/{to}")
    public List<User> getAllUsersByBirthDateBetweenTwoDates(@Parameter(
            name =  "from",
            description  = "the first date in our range",
            example = "1994-01-01",
            required = true)
            @PathVariable LocalDate from,
                                                            @Parameter( name =  "to",
                                                                    description  = "the last date in our range",
                                                                    example = "1999-11-02",
                                                                    required = true)
                                                            @PathVariable LocalDate to) {
        return service.getAllUsersByBirthDateBetweenTwoDates(from,to);
    }
    @GetMapping("{id}")
    public User getUserById(@PathVariable String id) {
        return service.getUserById(id);
    }
    @PostMapping
    public User createUser(@RequestBody User user) {
        return service.createUser(user);
    }
    @PutMapping("{id}")
    public User updateUser(@PathVariable String id ,@RequestBody User user) {
        return service.updateUser(id, user);
    }
    @DeleteMapping("{id}")
    public void deleteUser(@PathVariable String id) {
       service.deleteUser(id);
    }

}
