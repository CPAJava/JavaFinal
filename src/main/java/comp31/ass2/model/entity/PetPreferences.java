package comp31.ass2.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class PetPreferences {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Integer id;
    String preferredSpecies;
    String preferredColor;
    String preferredSize;

    public PetPreferences(String preferredSpecies, String preferredColor, String preferredSize) {
        this.preferredSpecies = preferredSpecies;
        this.preferredColor = preferredColor;
        this.preferredSize = preferredSize;
    }

}
