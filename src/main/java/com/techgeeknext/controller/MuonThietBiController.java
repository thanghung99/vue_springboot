package com.techgeeknext.controller;

import java.security.Principal;
import java.time.LocalDate;
import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.techgeeknext.entity.MuonTra;
import com.techgeeknext.entity.TaiKhoan;
import com.techgeeknext.entity.TaiKhoanThietBi;
import com.techgeeknext.entity.ThietBi;
import com.techgeeknext.repository.MuonTraRepository;
import com.techgeeknext.repository.TaiKhoanRepository;
import com.techgeeknext.repository.ThietBiRepository;
import com.techgeeknext.service.MuonTraService;

@RestController
@RequestMapping("/api/thietbi/")
@CrossOrigin(origins = "http://localhost:8081", maxAge = 3600)
public class MuonThietBiController {
	private MuonTraRepository muontrarepository;
	private MuonTraService muontraservice;
	private TaiKhoanRepository taiKhoanRepository;
	private ThietBiRepository thietBiRepository;
	
	@Autowired
	public MuonThietBiController(MuonTraRepository muontrarepository, MuonTraService muontraservice,
			ThietBiRepository thietBiRepository,TaiKhoanRepository taiKhoanRepository) {
		this.muontrarepository = muontrarepository;
		this.muontraservice = muontraservice;
		this.taiKhoanRepository = taiKhoanRepository;
		this.thietBiRepository = thietBiRepository;
	}

	@PostMapping("muon-thiet-bi")
	public ResponseEntity muonthietbi(@RequestBody MuonTra muontra, Principal principal) {
		System.out.println(muontra);
		Date d = new Date();
		if (muontra.getNgayTra() == null || d.compareTo(muontra.getNgayTra()) > 0)
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		TaiKhoan tk = taiKhoanRepository.findByUsername(principal.getName());
		muontra.setTaiKhoan(tk);
		muontra.setNgayMuon(d);
		TaiKhoanThietBi tk_tb = new TaiKhoanThietBi();
		tk_tb.setMaTaiKhoan(tk.getId());
		tk_tb.setMaThietBi(muontra.getThietBi().getId());
		muontra.setTaiKhoanThietBi(tk_tb);
		muontra.setTaiKhoanThietBi(tk_tb);
		muontra.setStatus(0);
		muontrarepository.save(muontra);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@GetMapping("yeu-cau-xu-ly")
	public ResponseEntity listYeuCau() {
		return new ResponseEntity<>(muontraservice.getMuonTraByStatus(0), HttpStatus.OK);
	}

	@GetMapping("danh-sach-yeu-cau")
	public ResponseEntity listYeuCauByUser(Principal principal) {
		TaiKhoan tk = taiKhoanRepository.findByUsername(principal.getName());
		return new ResponseEntity<>(muontraservice.getMuonTraByUser(tk.getId()), HttpStatus.OK);
	}
	
	@GetMapping("search")
	public ResponseEntity search(@RequestParam("valueSearch") String valueSearch) {
		return new ResponseEntity<>(muontraservice.searchDevice(valueSearch), HttpStatus.OK);
	}

	@PostMapping("yeu-cau-xu-ly/dongy")
	public ResponseEntity dongY(@RequestBody MuonTra muonTra) {
		muonTra.setStatus(1);
		muontrarepository.save(muonTra);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@GetMapping("danh-sach-thiet-bi-user")
	public ResponseEntity danhSachThietBiUser(Principal principal) {
		TaiKhoan tk = taiKhoanRepository.findByUsername(principal.getName());
		return new ResponseEntity<>(muontraservice.getMuonTraByUser(tk.getId(), 0), HttpStatus.OK);
	}
	
	@PostMapping("yeu-cau-xu-ly/huybo")
	public ResponseEntity huyBo(@RequestBody MuonTra muonTra) {
		muontrarepository.delete(muonTra);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	@PostMapping("assign")
	public ResponseEntity assign(@RequestBody MuonTra muontra,Principal principal) {
//		TaiKhoan tk = taiKhoanRepository.findByUsername(principal.getName());
		if(muontra.getThietBi() == null)
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		muontra.setNgayMuon(new Date());
		muontra.setStatus(1);
		long id = muontra.getThietBi().getId();
		TaiKhoanThietBi tk_tb = new TaiKhoanThietBi();
		tk_tb.setMaTaiKhoan(muontra.getTaiKhoan().getId());
		tk_tb.setMaThietBi(muontra.getThietBi().getId());
		muontra.setTaiKhoanThietBi(tk_tb);
		muontrarepository.save(muontra);
		ThietBi tb = thietBiRepository.getOne(id);
		tb.setStatus(1);
		thietBiRepository.save(tb);
		return new ResponseEntity<> (HttpStatus.CREATED);
	}
}
