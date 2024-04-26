package org.antihetman.testassignment;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import org.antihetman.testassignment.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.annotation.Id;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.fields;

/*
  @author   antihetman
  @project   test-assignment
  @class  UserTest
  @version  1.0.0 
  @since 4/26/24 - 11.28
*/
@SpringBootTest
class UserTest {

    private JavaClasses importedClasses;
    @BeforeEach
    public void setUp() {
        importedClasses = new ClassFileImporter()
                .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_JARS)
                .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_ARCHIVES)
                .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
                .importPackages("org.antihetman.testassignment");
    }
    @Test
    void userMustHaveFieldCalledEmail(){
        fields()
                .that().areDeclaredInClassesThat().resideInAPackage("..model..")
                .and().haveName("email")
                .should().beDeclaredIn(User.class)
                .check(importedClasses);
    }
    @Test
    void userMustHaveFieldCalledFirstName(){
        fields()
                .that().areDeclaredInClassesThat().resideInAPackage("..model..")
                .and().haveName("firstName")
                .should().beDeclaredIn(User.class)
                .check(importedClasses);
    }
    @Test
    void userMustHaveFieldCalledLastName(){
        fields()
                .that().areDeclaredInClassesThat().resideInAPackage("..model..")
                .and().haveName("lastName")
                .should().beDeclaredIn(User.class)
                .check(importedClasses);
    }
    @Test
    void userMustHaveFieldCalledBirthDate(){
        fields()
                .that().areDeclaredInClassesThat().resideInAPackage("..model..")
                .and().haveName("birthDate")
                .should().beDeclaredIn(User.class)
                .check(importedClasses);
    }
    @Test
    void userBirthDateMustBeLocalDateType(){
        fields()
                .that().areDeclaredInClassesThat().resideInAPackage("..model..")
                .and()
                .haveName("birthDate")
                .should()
                .haveRawType(LocalDate.class)
                .check(importedClasses);
    }
}