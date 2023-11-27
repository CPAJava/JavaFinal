package comp31.ass2.model.entity;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
// import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
// @Table(name="Employee")
public class Employee {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  Integer id;//for database, primary key, diff from userID
  String userId;
  String firstName;
  String lastName;
  String password;
  String position;
  @OneToMany(mappedBy = "employee")  //one to many relationship to the pets
  List<Pets> pets;

  public Employee(String userId, String firstName, String lastName, String password, String position) {
    this.userId = userId;
    this.firstName = firstName;
    this.lastName = lastName;
    this.password = password;
    this.position = position;
  }    
}
