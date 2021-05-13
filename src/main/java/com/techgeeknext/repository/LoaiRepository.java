package com.techgeeknext.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.techgeeknext.entity.Loai;

@Repository
public interface LoaiRepository extends JpaRepository<Loai, Long>{

}
