package comp31.ass2.repos;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

import comp31.ass2.model.entity.Employee;
import comp31.ass2.model.entity.Pets;

public interface PetsRepo extends CrudRepository<Pets, Integer>{
    //ListCrudRepository -> will give the same default method below
    public List<Pets> findAll();

    //public Pets findById(); -> this is the default given by CrudRepository

    public List<Pets> findByPetName(String petName);

    public List<Pets> findByPetSize(String petSize);

    public List<Pets> findByPetColor(String petColor);

    public List<Pets> findByPetSpecies(String petSpecies);

    public List<Pets> findByAdoptStatus(String adoptStatus);

    public List<Pets> findByEmployee(Employee employee);


}
