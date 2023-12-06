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
    @JoinColumn(name = "fkey_owner")
    PetOwner petOwner;
    @ManyToOne
    @JoinColumn(name = "fkey_employee")
    Employee employee;
  
    
    public Pet(String petId, String petName, String petSpecies, String petColor, String petSize, PetOwner petOwner,
            Employee employee, String adoptStatus) {

        this.petId = petId;
        this.petName = petName;
        this.petSpecies = petSpecies;
        this.petColor = petColor;
        this.petSize = petSize;
        this.petOwner = petOwner;
        this.employee = employee;
        this.adoptStatus = adoptStatus;
    }

    public Pet(String petName, String petSpecies, String petColor, String petSize, Employee employee,
            String adoptStatus) {

        this.petName = petName;
        this.petSpecies = petSpecies;
        this.petColor = petColor;
        this.petSize = petSize;
        this.employee = employee;
        this.adoptStatus = adoptStatus;
    }

}
