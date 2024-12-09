package app.components;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import app.entities.Instructor;
import app.repositories.InstructorRepository;

@Component
public class InstructorComponent {
	
	@Autowired
   	private InstructorRepository instructRepo;
	
	@Transactional
	public List<Instructor> findInstructorAll(){
		return this.instructRepo.findAll();
	}
	
	@Transactional
	public Instructor findInstructor(Long pk) {
		return this.instructRepo.findByInstructorPk(pk);
	}

	@Transactional
    public Instructor addInstructor(String name) throws Exception{
   
   		Instructor newInstructor = new Instructor();
        newInstructor.setInstructorName(name);
        return instructRepo.save(newInstructor);
    }
	
    public Instructor updateInstructor(Long pk, String name) throws Exception {
    	
    	Instructor existingInstructor = instructRepo.findByInstructorPk(pk);
        
    	//Basically, it only updates values with non-empty parameters <3
    	
	        if (existingInstructor == null) {
	        	throw new IllegalArgumentException("Instructor with pk: " + pk + " not found. Check if pk parameter is NULL or INVALID.");	            
	        }
	        
	        if(name != null && !name.isEmpty()) {
	           existingInstructor.setInstructorName(name);

	        }
	        return instructRepo.save(existingInstructor);
    }
			

    @Transactional
    public String deleteInstructor(Long pk) {
        Instructor existingInstructor = instructRepo.findByInstructorPk(pk);
        
        if (existingInstructor != null) {
        	instructRepo.deleteByInstructorPk(pk);
            return "Instructor with pk: " + pk + " has been deleted successfully.";
        } else {
            return "No Instructor has been deleted.";

        }
        
    }
	


}
