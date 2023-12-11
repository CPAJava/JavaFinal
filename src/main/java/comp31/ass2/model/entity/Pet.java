/*
* Author: Xuancheng Li
* Date: 2023-12-06
*
* Class/File: Pet.java
*
* This Java class represents the entity "Pet" in the application. It is annotated with JPA annotations
* to define its persistence mapping. The class includes fields such as petId, petName, species, color, size as well as 
* adoption status. This setting stores information about a pet, and will be related to employee and owners.
*
* The class establishes relationships with other entities using JPA annotations. It has a many-to-one relationship
* with the "PetOwner" entity and "Employee" entity. 
*
* The class will be created with two contructors. One of which will omit the petOwner field to reflect the initial status
* of pets, that is "available to be adopted."
*/
package comp31.ass2.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Pet {
    @Id 
    @GeneratedValue(strategy = GenerationType.AUTO) 
    Integer id; 
    String petId;
    String petName;
    String petSpecies;
    String petColor;
    String petSize;
    String adoptStatus;
    @ManyToOne
    @JoinColumn(name="fkey_owner")
    PetOwner petOwner;
    @ManyToOne
    @JoinColumn(name="fkey_employee")
    Employee employee;   


    public Pet(String petId, String petName, String petSpecies, String petColor, String petSize, PetOwner petOwner, Employee employee, String adoptStatus) {
        
        this.petId = petId;
        this.petName = petName;
        this.petSpecies = petSpecies;
        this.petColor = petColor;
        this.petSize = petSize;
        this.petOwner = petOwner;
        this.employee = employee;
        this.adoptStatus = adoptStatus;
    }    

    public Pet(String petId,String petName, String petSpecies, String petColor, String petSize, Employee employee, String adoptStatus) {
        this.petId = petId;
        this.petName = petName;
        this.petSpecies = petSpecies;
        this.petColor = petColor;
        this.petSize = petSize;
        this.employee = employee;
        this.adoptStatus = adoptStatus;
    }
}
