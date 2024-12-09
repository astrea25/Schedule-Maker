package app.entities;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
public class Section {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column
    private Long sectionPk;

	@Column
	@NotNull
    private String sectionName;
	
	@Column
	@NotNull
    private String dayOfTheWeekSchedule;

	@Column
	@NotNull
    private String timeStart;

	@Column
	@NotNull
    private String timeEnd;
	
	@ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "subjectPk")
    private Subject subject;
	
	@ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "roomPk")
    private Room room;
	
	@ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER)
	@JoinTable(
	    name = "section_instructor",
	    joinColumns = @JoinColumn(name = "sectionPk"),
	    inverseJoinColumns = @JoinColumn(name = "instructorPk")
	)
	private Set<Instructor> instructors = new HashSet<>();

	public Long getSectionPk() {
		return sectionPk;
	}

	public void setSectionPk(Long sectionPk) {
		this.sectionPk = sectionPk;
	}

	public String getSectionName() {
		return sectionName;
	}

	public void setSectionName(String sectionName) {
		this.sectionName = sectionName;
	}

	public String getDayOfTheWeekSchedule() {
		return dayOfTheWeekSchedule;
	}

	public void setDayOfTheWeekSchedule(String dayOfTheWeekSchedule) {
		this.dayOfTheWeekSchedule = dayOfTheWeekSchedule;
	}

	public String getTimeStart() {
		return timeStart;
	}

	public void setTimeStart(String timeStart) {
		this.timeStart = timeStart;
	}

	public String getTimeEnd() {
		return timeEnd;
	}

	public void setTimeEnd(String timeEnd) {
		this.timeEnd = timeEnd;
	}

	public Subject getSubject() {
		return subject;
	}

	public void setSubject(Subject subject) {
		this.subject = subject;
	}

	public Room getRoom() {
		return room;
	}

	public void setRoom(Room room) {
		this.room = room;
	}

	public Set<Instructor> getInstructors() {
		return instructors;
	}

	public void setInstructors(Set<Instructor> instructors) {
		this.instructors = instructors;
	}

	@Override
	public String toString() {
		return "Section [sectionPk=" + sectionPk + ", sectionName=" + sectionName + ", dayOfTheWeekSchedule="
				+ dayOfTheWeekSchedule + ", timeStart=" + timeStart + ", timeEnd=" + timeEnd + ", subject=" + subject
				+ ", room=" + room + ", instructors=" + instructors + "]";
	}
}
