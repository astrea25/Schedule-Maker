package app.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

@Entity
@Table(
    uniqueConstraints = @UniqueConstraint(
        columnNames = {"subjectCode", "courseTitle", "department"}
    )
)
public class Subject {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column
    private Long subjectPk;

	@Column
	@NotNull
    private String subjectCode;

	@Column
	@NotNull
    private String courseTitle;

	@Column
	@NotNull
    private String department;

	public Long getSubjectPk() {
		return subjectPk;
	}

	public void setSubjectPk(Long subjectPk) {
		this.subjectPk = subjectPk;
	}

	public String getSubjectCode() {
		return subjectCode;
	}

	public void setSubjectCode(String subjectCode) {
		this.subjectCode = subjectCode;
	}

	public String getCourseTitle() {
		return courseTitle;
	}

	public void setCourseTitle(String courseTitle) {
		this.courseTitle = courseTitle;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	@Override
	public String toString() {
		return "Subject [subjectPk=" + subjectPk + ", subjectCode=" + subjectCode + ", courseTitle=" + courseTitle
				+ ", department=" + department + "]";
	}
}
