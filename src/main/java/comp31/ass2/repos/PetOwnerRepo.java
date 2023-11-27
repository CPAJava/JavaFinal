package comp31.ass2.repos;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import comp31.ass2.model.entity.PetOwner;

public interface PetOwnerRepo extends CrudRepository<PetOwner, Integer> {
    public List<PetOwner> findAll();

    public PetOwner findByUserId(String userId);
}
