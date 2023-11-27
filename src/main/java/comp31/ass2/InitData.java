package comp31.ass2;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import comp31.ass2.model.entity.Employee;
import comp31.ass2.model.entity.PetOwner;
import comp31.ass2.model.entity.Pets;
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
        Employee emp1=new Employee("anderson", "Anderson", "Abbie","anderson","office");
        Employee emp2=new Employee("sam", "Sam", "Smith", "sam","manager");
        Employee emp3=new Employee("olivia", "Olivia", "Olson","olivia","office");
      
        employeeRepo.save(emp1);
        employeeRepo.save(emp2);
        employeeRepo.save(emp3);
        
        PetOwner owner1 = new PetOwner("ashly", "Ashly", "Black", "123", "submitted");
        PetOwner owner2 = new PetOwner("bill", "Bill", "Jack", "abc", "approved");
        PetOwner owner3 = new PetOwner("lily", "Lily", "Smith", "234", "declined");
 
        petOwnerRepo.save(owner1);
        petOwnerRepo.save(owner2);
        petOwnerRepo.save(owner3);
        
        petsRepo.save(new Pets("Pan Cake", "Cat","Black", "Small",owner1,emp1,"pending"));
        petsRepo.save(new Pets("Grizzly ", "Dog","Brown", "Large", owner2,emp2, "waiting"));
        petsRepo.save(new Pets("Mushroom", "Cat","Yellow", "Medium ", owner3,emp3,"waiting"));
        petsRepo.save(new Pets("Fur", "Dog","White", "Large ", owner1, emp1,"adopted"));
        petsRepo.save(new Pets("Pepper", "Cat","Black", "Small",owner1,emp1,"pending"));
        petsRepo.save(new Pets("Ollie ", "Dog","Brown", "Large", owner2,emp2, "pending"));
        petsRepo.save(new Pets("Mango", "Cat","Yellow", "Medium ", owner3,emp3,"waiting"));
        petsRepo.save(new Pets("Ciao", "Dog","White", "Large ", owner1, emp1,"pending"));
        

        
    }
    
    
}