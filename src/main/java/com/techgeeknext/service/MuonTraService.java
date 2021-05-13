package com.techgeeknext.service;

import java.util.List;

import com.techgeeknext.entity.MuonTra;
import com.techgeeknext.entity.ThietBi;

public interface MuonTraService {
	List<ThietBi> searchDevice(String valueSearch);
	List<MuonTra> getMuonTraByStatus(int status);
	List<MuonTra> getMuonTraByUser(long id);
	List<MuonTra> getMuonTraByUser(long id,int tra);
}
