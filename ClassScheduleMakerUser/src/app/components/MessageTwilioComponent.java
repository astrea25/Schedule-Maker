package app.components;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;

import app.entities.Subject;
import app.entities.User;
import app.repositories.SubjectRepository;

@Component
@EnableScheduling
public class MessageTwilioComponent {
	   
	   @Autowired
	   private UserComponent uc;
	   
	   @Autowired
	   TwilioComponent tc;
	   
	   @Autowired
	   SubjectRepository subjectRepository;
	   
	   Subject s;
	
	  public TwilioReply sendMessageToUser(Long studentID, Long sectionPk) throws Exception {
	      User u = this.uc.findUser(studentID);
	      if (u != null) {
	         
	      } 
	      return tc.sendMessage(u.getPhoneNumber(), "Hello " + u.getName() + ", A class has been added to your schedule: " + this.s.getCourseTitle());
	      
	      
	   }

}
