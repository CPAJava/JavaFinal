package comp31.ass2.repos;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import comp31.ass2.model.entity.PetPreferences;

public interface PetPreferencesRepo extends CrudRepository<PetPreferences, Integer> {

}
