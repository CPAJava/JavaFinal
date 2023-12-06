/*
* Author: Yanan Liu
* Date: 2023-12-06
*
* Class/File: Employee.java
*
* Additional context:
* This Java class represents the entity "Employee" in the application. It is annotated with JPA annotations
* to define its persistence mapping. The class includes fields such as id, userId, firstName, lastName, password,
* and position, which store information about an employee. The id field is annotated as the primary key for
* database storage.
*
* The class establishes a one-to-many relationship with the "Pet" entity using JPA annotations. An employee can
* be associated with multiple pets, and this relationship is mapped through the "pets" field.
*
*/
package comp31.ass2.model.entity;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@NoArgsConstructor
public class Employee {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  Integer id;// for database, primary key, diff from userID
  String userId;
  String firstName;
  String lastName;
  String password;
  String position;
  @OneToMany(mappedBy = "employee") // one to many relationship to the pets
  @ToString.Exclude
  List<Pet> pets;

//constructor of Employee
  public Employee(String userId, String firstName, String lastName, String password, String position) {
    this.userId = userId;
    this.firstName = firstName;
    this.lastName = lastName;
    this.password = password;
    this.position = position;
  }
}
