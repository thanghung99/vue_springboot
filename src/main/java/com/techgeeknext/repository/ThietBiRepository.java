package com.techgeeknext.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.techgeeknext.entity.ThietBi;

@Repository
public interface ThietBiRepository extends JpaRepository<ThietBi, Long>{

}
