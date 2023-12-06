/*
* Author: Yanan Liu
* Date: 2023-12-06
*
* Class/File: EmployeeRepo.java
*
* Additional context:
* This Java interface defines the repository for the "Employee" entity in the application. It extends the
* Spring Data JPA CrudRepository, providing CRUD operations for the Employee entity.
*
* The interface includes methods to retrieve employees based on various criteria. The entity "Employee" is
* annotated with JPA annotations, establishing its persistence mapping.
*
* The Employee entity includes fields such as userId, firstName, lastName, password, and other details
* representing an employee's information.
*/

package comp31.ass2.repos;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import comp31.ass2.model.entity.Employee;

public interface EmployeeRepo extends CrudRepository<Employee, Integer> {
   List<Employee> findAll();

   Employee findByLastNameIgnoreCase(String lastName);

   List<Employee> findByFirstNameAndLastName(String firstName, String lastName);

   List<Employee> findByFirstNameIgnoreCaseAndLastNameIgnoreCase(String firstName, String lastName);

   public Employee findByUserId(String userId);
}
