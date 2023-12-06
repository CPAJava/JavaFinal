package comp31.ass2.model.entity;

import java.util.List;
import java.util.Objects;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/*
 * Author: Manlin Mao
 * Date: 2023-12-06
 *
 * Class/File: PetOwner.java
 *
 * Additional context:
 * This Java class represents the entity "PetOwner" in the application. It is annotated with JPA annotations
 * to define its persistence mapping. The class includes fields such as userId, firstName, lastName, password,
 * email, status, and preference, which store information about a pet owner. The default status is set to "submitted"
 * when a new user registers, and the preference is initialized as false.
 *
 * The class establishes relationships with other entities using JPA annotations. It has a one-to-many relationship
 * with the "Pet" entity, where a pet owner can have multiple pets. Additionally, it has a one-to-one relationship
 * with the "PetPreferences" entity, linked by the "fkey_pet_preferences" foreign key.
 *
 * The class includes standard methods such as equals and hashCode for object comparison. Additionally, it provides
 * a method "setStatus" to update the status of the pet owner.
 */

@Entity
@Data
@NoArgsConstructor
public class PetOwner {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Integer id;
    String userId;
    String firstName;
    String lastName;
    String password;
    String email;
    String status = "submitted";// set default when a new user register
    Boolean preference = false;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "petOwner", cascade = CascadeType.ALL)
    @ToString.Exclude
    List<Pet> pets;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "fkey_pet_preferences")
    PetPreferences petPreferences;

    public PetOwner(String userId, String firstName, String lastName, String password, String status, String email,
            Boolean preference) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.status = status;
        this.preference = preference;
        this.email = email;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        PetOwner other = (PetOwner) obj;
        return Objects.equals(userId, other.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId);
    }

    public void setStatus(String newStatus) {
        this.status = newStatus;
    }
}
