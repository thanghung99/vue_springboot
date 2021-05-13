package com.techgeeknext.service;

import java.util.List;

import com.techgeeknext.entity.ThietBi;

public interface ThietBiService {
	List<ThietBi> findThietBiByLoai(long id);
	List<ThietBi> findThietBiMuon();
}
