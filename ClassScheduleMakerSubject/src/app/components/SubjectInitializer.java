package app.components;


import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import app.entities.Subject;
import app.repositories.SubjectRepository;

@Component
public class SubjectInitializer {
	
	@Autowired
	private SubjectRepository subjrepo;
	
	@PostConstruct
	public void init() {
		if(subjrepo.count()==0) {
			
			Subject subj[] = new Subject[9];
			for(int x = 0; x <= 8 ; x++) {
				subj[x] = new Subject() ; //
				
			}
		
			subj[0].setSubjectCode("THEO 11");
			subj[0].setCourseTitle("FAITH, SPIRITUALITY, AND THE CHURCH");
//			subj[0].setDayOfTheWeekSchedule("M-TH");
//			subj[0].setTimeStart("09:00");
//			subj[0].setTimeEnd("11:00");
			subj[0].setDepartment("Department of THEOLOGY");
//			subj[0].setSlots(30);

			subj[1].setSubjectCode("THEO 12");
			subj[1].setCourseTitle("THEOLOGICAL ANTHROPOLOGY");
//			subj[1].setDayOfTheWeekSchedule("T-F");
//			subj[1].setTimeStart("10:00");
//			subj[1].setTimeEnd("12:00");
			subj[1].setDepartment("THEOLOGY");
//			subj[1].setSlots(30);

			subj[2].setSubjectCode("PHYED 111.30");
			subj[2].setCourseTitle("FUNDAMENTAL MOVEMENT FOR HEALTH AND FITNESS");
//			subj[2].setDayOfTheWeekSchedule("W-F");
//			subj[2].setTimeStart("10:00");
//			subj[2].setTimeEnd("12:00");
			subj[2].setDepartment("PHYSICAL EDUCATION");
//			subj[2].setSlots(30);

			subj[3].setSubjectCode("PHYED 113");
			subj[3].setCourseTitle("PHYSICAL FITNESS FOR WOMEN");
//			subj[3].setDayOfTheWeekSchedule("T-TH");
//			subj[3].setTimeStart("14:00");
//			subj[3].setTimeEnd("16:00");
			subj[3].setDepartment("PHYSICAL EDUCATION");
//			subj[3].setSlots(25);
			
			subj[4].setSubjectCode("DEV 183.13");
			subj[4].setCourseTitle("SOCIAL MARKETING");
//			subj[4].setDayOfTheWeekSchedule("M-W");
//			subj[4].setTimeStart("14:00");
//			subj[4].setTimeEnd("16:00");
			subj[4].setDepartment("DEVELOPMENT STUDIES");
//			subj[4].setSlots(20);

			subj[5].setSubjectCode("DEV 183.25");
			subj[5].setCourseTitle("SOCIAL ENTREPRENEURSHIP");
//			subj[5].setDayOfTheWeekSchedule("T-TH");
//			subj[5].setTimeStart("09:30");
//			subj[5].setTimeEnd("11:30");
			subj[5].setDepartment("DEVELOPMENT STUDIES");
//			subj[5].setSlots(20);
			
			subj[6].setSubjectCode("PSYC 80.03i");
			subj[6].setCourseTitle("INTRODUCTION TO POLITICAL PSYCHOLOGY");
//			subj[6].setDayOfTheWeekSchedule("W-F");
//			subj[6].setTimeStart("13:00");
//			subj[6].setTimeEnd("15:00");
			subj[6].setDepartment("PSYCHOLOGY");
//			subj[6].setSlots(25);

			subj[7].setSubjectCode("ENVI 161i");
			subj[7].setCourseTitle("INTEGRAL ECOLOGY");
//			subj[7].setDayOfTheWeekSchedule("M-T");
//			subj[7].setTimeStart("12:00");
//			subj[7].setTimeEnd("14:00");
			subj[7].setDepartment("ENVIRONMENTAL SCIENCE");
//			subj[7].setSlots(20);

			subj[8].setSubjectCode("STS 21i");
			subj[8].setCourseTitle("SCIENCE IN SCI-FI SHOWS");
//			subj[8].setDayOfTheWeekSchedule("T-TH");
//			subj[8].setTimeStart("10:00");
//			subj[8].setTimeEnd("12:00");
			subj[8].setDepartment("SCIENCE, TECHNOLOGY, AND SOCIETY");
//			subj[8].setSlots(30);
			

			for(int x = 0; x <= 8; x++) {
				subjrepo.save(subj[x]);
			}
		
		}
		}

}
