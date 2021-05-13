
package com.techgeeknext.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.techgeeknext.entity.MuonTra;
import com.techgeeknext.entity.TaiKhoanThietBi;

@Repository
public interface MuonTraRepository extends JpaRepository<MuonTra, TaiKhoanThietBi> {

}
