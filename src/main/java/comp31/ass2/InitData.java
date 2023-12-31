package comp31.ass2;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import comp31.ass2.model.entity.Employee;
import comp31.ass2.model.entity.Pet;
import comp31.ass2.model.entity.PetOwner;
import comp31.ass2.model.entity.PetPreferences;
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

        PetOwner owner1 = new PetOwner("ashly", "Ashly", "black", "123", "approved", "ash@fake.com", false);
        PetOwner owner2 = new PetOwner("bill", "Bill", "Jack", "abc", "submitted", "bill@fake.com", false);
        PetOwner owner3 = new PetOwner("lily", "Lily", "Smith", "234", "approved", "lil@fake.com", true,new PetPreferences("dog","white","large"));
        PetOwner owner4 = new PetOwner("cathy", "cathy", "white", "123", "approved", "cathy@fake.com",true, new PetPreferences("dog","white","large"));
        PetOwner owner5 = new PetOwner("dale", "Dale", "Bill", "abc", "approved", "dale@fake.com", true,new PetPreferences("cat","black","small"));
        PetOwner owner6 = new PetOwner("phil", "Phil", "T", "234", "approved", "phil@fake.com", true,new PetPreferences("dog","black","large"));

        petOwnerRepo.save(owner1);
        petOwnerRepo.save(owner2);
        petOwnerRepo.save(owner3);

        petOwnerRepo.save(owner4);
        petOwnerRepo.save(owner5);
        petOwnerRepo.save(owner6);

        Pet pet1=new Pet("panC","Pan Cake", "cat","black", "small",emp1,"available");
        Pet pet2=new Pet("Gri","Grizzly", "dog","black", "large",emp2, "available");
        Pet pet3=new Pet("Mus","Mushroom", "cat","yellow", "medium",emp3,"available"); 
        Pet pet7=new Pet("Man","Mango", "cat","yellow", "medium",emp3,"available");

       
        Pet pet4=new Pet("Fur", "Fur", "dog","white", "large", owner4, emp1,"adopted");
        Pet pet5=new Pet("Pep","Pepper", "cat","black", "small",owner5,emp1,"pending");
        Pet pet6=new Pet("Oll ","Ollie", "dog","black", "large", owner6,emp2, "pending");
        Pet pet8=new Pet("Cia","Ciao", "dog","white", "large", owner3, emp1,"pending");
        

        petsRepo.save(new Pet("ash","ash", "dog", "black", "big", emp1, "available"));
        petsRepo.save(new Pet("bill","bill", "cat", "orange", "medium", emp2, "available"));
        petsRepo.save(new Pet("lil","lil", "bird", "white", "small", emp3, "available"));
        petsRepo.save(new Pet("lil2","lil2", "bird", "white", "small", emp1, "available"));
        petsRepo.save(new Pet("lil3","lil3", "bird", "white", "small", emp1, "available"));
        petsRepo.save(new Pet("lil4","lil4", "bird", "white", "small", emp2, "available"));
       
        petsRepo.save(pet1);
        petsRepo.save(pet2);
        petsRepo.save(pet3);
        petsRepo.save(pet4);
        petsRepo.save(pet5);
        petsRepo.save(pet6);
        petsRepo.save(pet7);
        petsRepo.save(pet8);

    }

}