package app.repositories;

import org.springframework.stereotype.Repository;

import app.entities.Section;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface SectionRepository extends JpaRepository<Section, Long> {

	public List<Section> findAll();
	public Section findBySectionPk(Long sectionPk);
	public void deleteBySectionPk(Long sectionPk);
	public Section findBySectionName(String sectionName);
	
}