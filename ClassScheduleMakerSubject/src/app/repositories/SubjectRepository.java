package app.repositories;

import org.springframework.stereotype.Repository;

import app.entities.Subject;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface SubjectRepository extends JpaRepository<Subject, Long> {

	public List<Subject> findAll();
	public Subject findBySubjectPk(Long subjectPk);
	public void deleteBySubjectPk(Long subjectPk);
	public Subject findBySubjectCode(String subjectCode);
	
}
