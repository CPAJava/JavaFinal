package comp31.ass2;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import comp31.ass2.model.entity.Employee;
import comp31.ass2.model.entity.Pet;
import comp31.ass2.model.entity.PetOwner;

import comp31.ass2.repos.EmployeeRepo;
import comp31.ass2.repos.PetOwnerRepo;
import comp31.ass2.repos.PetsRepo;

@Component
public class InitData implements CommandLineRunner {

    EmployeeRepo employeeRepo;
    PetsRepo petsRepo;
    PetOwnerRepo petOwnerRepo;

    // scource action-> generate contructor
    public InitData(EmployeeRepo employeeRepo, PetsRepo petsRepo, PetOwnerRepo petOwnerRepo) {
        this.employeeRepo = employeeRepo;
        this.petsRepo = petsRepo;
        this.petOwnerRepo = petOwnerRepo;
    }

    @Override
    public void run(String... args) throws Exception {
        Employee emp1 = new Employee("anderson", "Anderson", "Abbie", "anderson", "office");
        Employee emp2 = new Employee("sam", "Sam", "Smith", "sam", "manager");
        Employee emp3 = new Employee("olivia", "Olivia", "Olson", "olivia", "office");

        employeeRepo.save(emp1);
        employeeRepo.save(emp2);
        employeeRepo.save(emp3);

        PetOwner owner1 = new PetOwner("ashly", "Ashly", "Black", "123", "approved", "ash@fake.com", false);
        PetOwner owner2 = new PetOwner("bill", "Bill", "Jack", "abc", "approved", "bill@fake.com", true);
        PetOwner owner3 = new PetOwner("lily", "Lily", "Smith", "234", "declined", "lil@fake.com", false);

        petOwnerRepo.save(owner1);
        petOwnerRepo.save(owner2);
        petOwnerRepo.save(owner3);

        petOwnerRepo.save(owner1);
        petOwnerRepo.save(owner2);
        petOwnerRepo.save(owner3);

        petsRepo.save(new Pet("ash", "dog", "black", "big", owner1, emp1, "available"));
        petsRepo.save(new Pet("bill", "cat", "orange", "medium", owner2, emp2, "available"));
        petsRepo.save(new Pet("lil", "bird", "white", "small", owner3, emp3, "available"));
        petsRepo.save(new Pet("lil2", "bird", "white", "small", owner1, emp1, "available"));
        petsRepo.save(new Pet("lil3", "bird", "white", "small", owner2, emp1, "available"));
        petsRepo.save(new Pet("lil4", "bird", "white", "small", owner3, emp2, "available"));

        petsRepo.save(new Pet("Pan Cake", "Cat", "Black", "Small", owner1, emp1, "pending"));
        petsRepo.save(new Pet("Grizzly ", "Dog", "Brown", "Large", owner2, emp2, "waiting"));
        petsRepo.save(new Pet("Mushroom", "Cat", "Yellow", "Medium ", owner3, emp3, "waiting"));
        petsRepo.save(new Pet("Fur", "Dog", "White", "Large ", owner1, emp1, "adopted"));
        petsRepo.save(new Pet("Pepper", "Cat", "Black", "Small", owner1, emp1, "pending"));
        petsRepo.save(new Pet("Ollie ", "Dog", "Brown", "Large", owner2, emp2, "pending"));
        petsRepo.save(new Pet("Mango", "Cat", "Yellow", "Medium ", owner3, emp3, "waiting"));
        petsRepo.save(new Pet("Ciao", "Dog", "White", "Large ", owner1, emp1, "pending"));

    }

}