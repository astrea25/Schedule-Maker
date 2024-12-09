package app.components;

import java.util.Iterator;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import app.entities.Section;
import app.entities.User;
import app.repositories.SectionRepository;
import app.repositories.UserRepository;
import app.services.LoginService;

@Component
public class UserComponent {
	
	@Autowired
	private UserRepository repo;
	
	@Autowired
	private SectionRepository section_repo;
	
	@Autowired
	TwilioComponent tc;
	
  @Autowired
	private LoginService loginService;
	
	public List<User> getAllUsers() {
		 return repo.findAll();
	}
	
	public User findUser(Long studentID) {
		return repo.findByUserPk(studentID);
	}
	
	public User createUser(String username, String password, String name, String course, String phoneNumber) {
		return loginService.register(username, password, name, course, phoneNumber);
	}
	
	public User updateUser(Long studentID, String name, String course, String phoneNumber) {
		User u1 = repo.findByUserPk(studentID);
		if (u1 == null) {
	        throw new RuntimeException("User not found");
	    }
		u1.setName(name);
		u1.setCourse(course);
		u1.setPhoneNumber(phoneNumber);
		return repo.save(u1);
	}
	
	public String deleteUser(Long studentID) {
		if(repo.findByUserPk(studentID) == null) {
			return "User not found";
		}
		repo.deleteById(studentID);
		return "User deleted successfully";
	}
	
	public User addSection(Long studentID, Long sectionPk) throws Exception {
		User u1 = repo.findByUserPk(studentID);
		Section s1 = section_repo.findBySectionPk(sectionPk);
		  if (u1 == null) {
		        throw new IllegalArgumentException("User not found for ID: " + studentID);
		    }
		  if (s1 == null) {
		        throw new IllegalArgumentException("Section not found for ID: " + sectionPk);
		    }
		  boolean sectionChecker = u1.getSections().stream()
				    .anyMatch(section -> section.getSectionPk().equals(s1.getSectionPk()));

				if (sectionChecker) {
				    throw new IllegalArgumentException("Section already enrolled: " + sectionPk);
				}
		
		
		u1.getSections().add(s1);
		User savedUser = repo.save(u1);
		
		try {
	        String message = "Hello " + u1.getName() + 
	                         ", a new class has been added to your schedule: " + 
	                         s1.getSubject().getCourseTitle() + ", Section " + s1.getSectionName();
	        tc.sendMessage(u1.getPhoneNumber(), message);
	    } catch (Exception e) {
	        // Log or handle the error
	        System.err.println("Failed to send Twilio message: " + e.getMessage());
	    }

		
		return savedUser;
	}
	
	@Transactional
	public User deleteSection(Long studentID, Long sectionPk) throws Exception {
	    User u1 = repo.findByUserPk(studentID);
	    Section s1 = section_repo.findBySectionPk(sectionPk);

	    if (u1 == null) {
	        throw new IllegalArgumentException("User not found for ID: " + studentID);
	    }
	    if (s1 == null) {
	        throw new IllegalArgumentException("Section not found for ID: " + sectionPk);
	    }

	    boolean isEnrolled = u1.getSections().stream()
	        .anyMatch(section -> section.getSectionPk().equals(s1.getSectionPk()));

	    if (!isEnrolled) {
	        throw new IllegalArgumentException("User is not enrolled in section with ID: " + sectionPk);
	    }

	    u1.getSections().remove(s1);	    
	    User savedUser= repo.save(u1);

	    try {
	        String message = "Hello, " + u1.getName() + ". Your scheduled class " + s1.getSubject().getCourseTitle() + ", Section "+ s1.getSectionName() + " has been removed from your schedule.";
	        tc.sendMessage(u1.getPhoneNumber(), message);
	    } catch (Exception e) {
	        // Log or handle the error
	        System.err.println("Failed to send Twilio message: " + e.getMessage());
	    }
	    

	    return savedUser;
	}
	
	

	public User updateSection(Long studentID, Long oldSectionPk, Long newSectionPk) {
		User u1 = repo.findByUserPk(studentID);
		Section s1 = section_repo.findBySectionPk(newSectionPk);
		Iterator<Section> iterator = u1.getSections().iterator();
	    while (iterator.hasNext()) {
	        Section currentSection = iterator.next();
	        if (currentSection.getSectionPk().equals(oldSectionPk)) {
	            iterator.remove();
	            u1.getSections().add(s1);
	            break;
	        }
	    }

		return repo.save(u1);
	}
	
	public String login(String username, String password) {
        return loginService.login(username, password);
    }
	
	public void logout(String sessionId) {
        loginService.logout(sessionId);
    }

    public User getUserFromSession(String sessionId) {
        User user = loginService.getUserFromSession(sessionId);
        if (user == null) {
            throw new RuntimeException("Session not found or expired");
        }
        return user;
    }
}
