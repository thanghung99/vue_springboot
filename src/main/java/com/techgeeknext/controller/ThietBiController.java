package com.techgeeknext.controller;

import java.security.Principal;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.techgeeknext.entity.ThietBi;
import com.techgeeknext.repository.TaiKhoanRepository;
import com.techgeeknext.repository.ThietBiRepository;
import com.techgeeknext.service.ThietBiService;

@RestController
@RequestMapping("/api/")
@CrossOrigin(origins = "http://localhost:8081", maxAge = 3600)
public class ThietBiController {
	private ThietBiRepository thietBiRepository;
	private TaiKhoanRepository taikhoanRepository;
	private ThietBiService thietBiService;
	@Autowired
	public ThietBiController(ThietBiRepository thietBiRepository, TaiKhoanRepository taikhoanRepository,ThietBiService thietBiService) {
		this.thietBiRepository = thietBiRepository;
		this.taikhoanRepository = taikhoanRepository;
		this.thietBiService = thietBiService;
	}

	@GetMapping("thietbi")
	public ResponseEntity<List<ThietBi>> getAllThietBi(Principal principal) {
		if (taikhoanRepository.findByUsername(principal.getName()).getRole().equals("user")) {
			return null;
		}
		List<ThietBi> listThietBi = thietBiRepository.findAll();
		if (StringUtils.isEmpty(listThietBi))
			return new ResponseEntity<List<ThietBi>>(HttpStatus.NO_CONTENT);
		else
			return new ResponseEntity<List<ThietBi>>(thietBiRepository.findAll(), HttpStatus.OK);

	}

	@GetMapping("thietbi/muon")
	public ResponseEntity<List<ThietBi>> getAllThietBiMuon(Principal principal) {
		List<ThietBi> listThietBi = thietBiService.findThietBiMuon();
		if (StringUtils.isEmpty(listThietBi))
			return new ResponseEntity<List<ThietBi>>(HttpStatus.NO_CONTENT);
		else
			return new ResponseEntity<List<ThietBi>>(listThietBi, HttpStatus.OK);

	}
	
	@GetMapping("thietbi/{id}")
	public ResponseEntity<ThietBi> getThietBiByID(@PathVariable String id,Principal principal) {
		Optional<ThietBi> optionalThietBi = thietBiRepository.findById(Long.parseLong(id));
		if (!optionalThietBi.isPresent())
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		else
			return new ResponseEntity<ThietBi>(optionalThietBi.get(), HttpStatus.OK);

	}

	@GetMapping("thietbi/loai/{id}")
	public ResponseEntity<List<ThietBi>> getThietBiByLoai(@PathVariable String id,Principal principal) {
		if (taikhoanRepository.findByUsername(principal.getName()).getRole().equals("user")) {
			return null;
		}
			return new ResponseEntity<List<ThietBi>>(thietBiService.findThietBiByLoai(Long.parseLong(id)), HttpStatus.OK);

	}
	
	@PostMapping("thietbi")
	public ResponseEntity<ThietBi> addThietBi(@RequestBody ThietBi thietbi, Principal principal) {
		if (taikhoanRepository.findByUsername(principal.getName()).getRole().equals("user")) {
			return null;
		}
		thietbi.setCreatedBy(principal.getName());
		thietbi.setCreatedDate(new Date());
		return new ResponseEntity<ThietBi>(thietBiRepository.save(thietbi), HttpStatus.CREATED);
	}

	@PutMapping("thietbi/{id}")
	public ResponseEntity<ThietBi> updateThietBi(@PathVariable String id, @RequestBody ThietBi thietbi,
			Principal principal) {
		if (taikhoanRepository.findByUsername(principal.getName()).getRole().equals("user")) {
			return null;
		}
		Optional<ThietBi> optionalThietBi = thietBiRepository.findById(Long.parseLong(id));
		if (!optionalThietBi.isPresent())
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		ThietBi tb = optionalThietBi.get();
		BeanUtils.copyProperties(thietbi, optionalThietBi.get());
		tb.setModifiedBy(principal.getName());
		tb.setModifiedDate(new Date());
		return new ResponseEntity<ThietBi>(thietBiRepository.save(tb), HttpStatus.OK);
	}

	@DeleteMapping("thietbi/{id}")
	public ResponseEntity<ThietBi> deleteThietBi(@PathVariable String id,Principal principal) {
		if (taikhoanRepository.findByUsername(principal.getName()).getRole().equals("user")) {
			return null;
		}
		thietBiRepository.deleteById(Long.parseLong(id));
		return new ResponseEntity<ThietBi>(HttpStatus.OK);
	}
}
