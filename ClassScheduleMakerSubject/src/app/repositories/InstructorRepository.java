package app.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import app.entities.Instructor;

@Repository
public interface InstructorRepository extends JpaRepository<Instructor, Long> {
	
	public List<Instructor> findAll();
	public Instructor findByInstructorPk(Long InstructorPk);
	public Instructor findByInstructorName(String name);
	public void deleteByInstructorPk(Long InstructorPk);

}

