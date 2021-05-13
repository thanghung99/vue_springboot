package com.techgeeknext.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.techgeeknext.entity.Loai;
import com.techgeeknext.repository.LoaiRepository;

@RestController
@RequestMapping("/api/")
@CrossOrigin(origins = "http://localhost:8081", maxAge = 3600)
public class LoaiController {
	private LoaiRepository loaiRepository ;
	
	@Autowired
	public LoaiController(LoaiRepository loaiRepository) {
		this.loaiRepository = loaiRepository;
		
	}
	
	@GetMapping("loai")
	public ResponseEntity<List<Loai>> getLoais(){
		return new ResponseEntity<List<Loai>>(loaiRepository.findAll(),HttpStatus.OK);
	}
}
