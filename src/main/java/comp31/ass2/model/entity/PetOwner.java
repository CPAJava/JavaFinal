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
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "petOwner")
    List<Pet> pets;

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
}
