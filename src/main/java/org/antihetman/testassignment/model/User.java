package org.antihetman.testassignment.model;/*
  @author   antihetman
  @project   test-assignment
  @class  User
  @version  1.0.0 
  @since 4/26/24 - 11.25
*/

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document
@Schema(
        name = "User",
        description = "Object fields of which represents game-specs"
)
public class User {

    @Id
    @Schema(
            name = "Identification",
            description = "Identification is used for searching documents in MongoDB",
            example = "6548ade5b20f381351730c8c",
            accessMode = Schema.AccessMode.READ_ONLY
    )
    String id;

    @Schema(
            name = "Email",
            description = "Email",
            example = "user@example.com",
            accessMode = Schema.AccessMode.READ_WRITE
    )
    String email;

    @Schema(
            name = "First Name",
            description = "First name of user",
            example = "John",
            accessMode = Schema.AccessMode.READ_ONLY
    )
    String firstName;

    @Schema(
            name = "Last Name",
            description = "Last name of user",
            example = "Doe",
            accessMode = Schema.AccessMode.READ_WRITE
    )
    String lastName;

    @Schema(
            name = "Birth Date",
            description = "Birth date of user",
            example = "2000-01-01",
            accessMode = Schema.AccessMode.READ_WRITE
    )
    LocalDate birthDate;

    @Schema(
            name = "Home Address",
            description = "Home address of user",
            example = "123 Street, City",
            accessMode = Schema.AccessMode.READ_WRITE
    )
    String address;

    @Schema(
            name = "Phone Number",
            description = "Phone number of user",
            example = "+1234567890",
            accessMode = Schema.AccessMode.READ_WRITE
    )
    String phoneNumber;

    public User(String email, String firstName, String lastName, LocalDate birthDate, String address, String phoneNumber) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }
    public User(String email, String firstName, String lastName, LocalDate birthDate) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
    }
}
