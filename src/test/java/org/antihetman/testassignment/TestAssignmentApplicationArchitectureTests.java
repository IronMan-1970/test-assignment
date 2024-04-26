package org.antihetman.testassignment;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.annotation.Id;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.*;
import static com.tngtech.archunit.library.Architectures.layeredArchitecture;

@SpringBootTest
class TestAssignmentApplicationArchitectureTests {

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
	void shouldFollowLayeredArchitecture() {
		layeredArchitecture()
				.layer("Controller").definedBy("..controller..")
				.layer("Service").definedBy("..service..")
				.layer("Repository").definedBy("..repository..")
				.layer("Model").definedBy("..model..")
				.whereLayer("Controller").mayNotBeAccessedByAnyLayer()
				.whereLayer("Service").mayOnlyBeAccessedByLayers("Controller", "Service")
				.whereLayer("Repository").mayOnlyBeAccessedByLayers("Service")
				.check(importedClasses);

	}
	@Test
	void serviceShouldNotDependOnControllerLevel(){
		noClasses()
				.that().resideInAPackage("..service..")
				.should()
				.dependOnClassesThat().resideInAPackage("..controller..")
				.because(" out of rules")
				.check(importedClasses);
	}
	@Test
	void controllerClassesShouldBeNamedXController(){
		classes()
				.that().resideInAPackage("..controller..")
				.should()
				.haveSimpleNameEndingWith("Controller")
				.check(importedClasses);
	}
	@Test
	void repositoryClassesShouldBeNamedXRepository(){
		classes()
				.that().resideInAPackage("..repository..")
				.should()
				.haveSimpleNameEndingWith("Repository")
				.check(importedClasses);
	}
	@Test
	void serviceClassesShouldBeNamedXService(){
		classes()
				.that().resideInAPackage("..service..")
				.should()
				.haveSimpleNameEndingWith("Service")
				.check(importedClasses);
	}
	@Test
	void controllerClassesShouldBeAnnotatedByRestController(){
		classes()
				.that().resideInAPackage("..controller..")
				.should()
				.beAnnotatedWith(RestController.class)
				.andShould()
				.beAnnotatedWith(RequestMapping.class)
				.check(importedClasses);
	}
	@Test
	void entitiesShouldHaveIdAnnotation(){
		fields()
				.that().areDeclaredInClassesThat().resideInAPackage("..model..").and().haveName("id")
				.should()
				.beAnnotatedWith(Id.class)
				.check(importedClasses);
	}
	@Test
	void serviceClassesShouldBeAnnotatedByService()
	{
		classes()
				.that().resideInAPackage("..service..")
				.should()
				.beAnnotatedWith(Service.class)
				.check(importedClasses);
	}
	@Test
	void repositoryClassesShouldBeAnnotatedByRepository()
	{
		classes()
				.that().resideInAPackage("..repository..")
				.should()
				.beAnnotatedWith(Repository.class)
				.check(importedClasses);
	}
	@Test
	void shouldNotUseFieldsAutowired(){
		noFields()
				.should()
				.beAnnotatedWith(Autowired.class)
				.check(importedClasses);
	}

}
