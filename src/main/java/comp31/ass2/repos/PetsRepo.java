package comp31.ass2.repos;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

import comp31.ass2.model.entity.Employee;
import comp31.ass2.model.entity.Pet;

public interface PetsRepo extends CrudRepository<Pet, Integer> {
    // ListCrudRepository -> will give the same default method below
    public List<Pet> findAll();

    // public Pet findById(); -> this is the default given by CrudRepository

    public List<Pet> findByPetName(String petName);

    public List<Pet> findByPetize(String Petize);

    public List<Pet> findByPetColor(String petColor);

    public List<Pet> findByPetpecies(String Petpecies);

    public List<Pet> findByAdoptStatus(String adoptStatus);

    public List<Pet> findByEmployee(Employee employee);

    List<Pet> findPetsByPetSpeciesAndPetColorAndPetSize(String petSpecies, String petColor, String petSize);

}
