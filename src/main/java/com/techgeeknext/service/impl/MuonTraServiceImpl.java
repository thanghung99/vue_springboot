package com.techgeeknext.service.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Service;

import com.techgeeknext.entity.MuonTra;
import com.techgeeknext.entity.ThietBi;
import com.techgeeknext.service.MuonTraService;

@Service
public class MuonTraServiceImpl implements MuonTraService{
	@PersistenceContext protected EntityManager entityManager;
	
	@Override
	public List<ThietBi> searchDevice(String valueSearch) {
		String jpql = "SELECT * FROM quanlythietbi.thietbi where ten_thiet_bi like '%"+valueSearch+"%'";
		Query query = entityManager.createNativeQuery(jpql, ThietBi.class);
		return query.getResultList();
	}

	@Override
	public List<MuonTra> getMuonTraByStatus(int status) {
		String jpql = "SELECT * FROM quanlythietbi.muon_tra where status='"+status+"'";
		Query query = entityManager.createNativeQuery(jpql, MuonTra.class);
		return query.getResultList();
	}

	@Override
	public List<MuonTra> getMuonTraByUser(long idUser) {
		String jpql = "SELECT * FROM quanlythietbi.muon_tra where ma_tai_khoan='"+idUser+"'";
		Query query = entityManager.createNativeQuery(jpql, MuonTra.class);
		return query.getResultList();
	}

	@Override
	public List<MuonTra> getMuonTraByUser(long id, int tra) {
		String jpql = "SELECT * FROM quanlythietbi.muon_tra, quanlythietbi.thietbi where muon_tra.ma_thiet_bi = thietbi.id and thietbi.status=1 and ma_tai_khoan= '"+id+"';";
		Query query = entityManager.createNativeQuery(jpql, MuonTra.class);
		return query.getResultList();
	}
}
