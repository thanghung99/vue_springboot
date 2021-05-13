package com.techgeeknext.service.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Service;

import com.techgeeknext.entity.ThietBi;
import com.techgeeknext.service.ThietBiService;

@Service
public class ThietBiServiceImpl implements ThietBiService{
	@PersistenceContext protected EntityManager entityManager;

	@Override
	public List<ThietBi> findThietBiByLoai(long id) {
		String jpql = "SELECT * FROM quanlythietbi.thietbi where status =0 and ma_loai = '"+id+"'";
		Query query = entityManager.createNativeQuery(jpql, ThietBi.class);
		return query.getResultList();
	}

	@Override
	public List<ThietBi> findThietBiMuon() {
		String jpql = "SELECT * FROM quanlythietbi.thietbi where status =0";
		Query query = entityManager.createNativeQuery(jpql, ThietBi.class);
		return query.getResultList();
	}

}
