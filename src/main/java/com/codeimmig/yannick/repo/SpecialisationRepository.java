package com.codeimmig.yannick.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.codeimmig.yannick.model.Specialization;

public interface SpecialisationRepository extends JpaRepository<Specialization,Long> {
	@Query("SELECT COUNT(specCode) FROM Specialization  WHERE specCode=:specCode")
	Integer getSpecCodeCount(String specCode);
	
	@Query("SELECT COUNT(specCode) FROM Specialization  WHERE specCode=:specCode AND id!=:id")
	Integer getSpecCodeCountForEdit(String specCode,Long id);
	//for dynamic dropdown
	@Query("SELECT id,specName FROM Specialization ")
	List<Object[]> getSpecIdAndName();
}

