package comp31.ass2.repos;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import comp31.ass2.model.entity.Employee;

public interface EmployeeRepo extends CrudRepository<Employee, Integer> { 
   List<Employee> findAll(); 
   Employee findByLastNameIgnoreCase(String lastName);
   List<Employee> findByFirstNameAndLastName(String firstName, String lastName);
   List<Employee> findByFirstNameIgnoreCaseAndLastNameIgnoreCase(String firstName, String lastName);
} 
