package com.techgeeknext.controller;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.techgeeknext.entity.TaiKhoan;
import com.techgeeknext.model.FileModel;
import com.techgeeknext.repository.TaiKhoanRepository;
import com.techgeeknext.service.UserService;

@RestController
@RequestMapping("/api/")
@CrossOrigin(origins = "http://localhost:8081", maxAge = 3600)
public class TaiKhoanController {
	private TaiKhoanRepository taiKhoanRepository;
	private UserService userService;
	private PasswordEncoder bcryptEncoder;

	@Autowired
	public TaiKhoanController(TaiKhoanRepository taiKhoanRepository, UserService userService,
			PasswordEncoder bcryptEncoder) {
		this.taiKhoanRepository = taiKhoanRepository;
		this.userService = userService;
		this.bcryptEncoder = bcryptEncoder;
	}

	@GetMapping("taikhoan")
	public ResponseEntity<List<TaiKhoan>> getTaiKhoan(Principal principal) {
		if (taiKhoanRepository.findByUsername(principal.getName()).getRole().equals("user")) {
			return null;
		}
		return new ResponseEntity<List<TaiKhoan>>(taiKhoanRepository.findAll(), HttpStatus.OK);
	}

	@GetMapping("profile")
	public ResponseEntity<TaiKhoan> getProfile(Principal principal) {
		TaiKhoan tk = taiKhoanRepository.findByUsername(principal.getName());
		Optional<TaiKhoan> taikhoanOp = taiKhoanRepository.findById(tk.getId());
		TaiKhoan taikhoan = taikhoanOp.get();
		taikhoan.setPassword(null);
		if (!taikhoanOp.isPresent())
			return new ResponseEntity<TaiKhoan>(HttpStatus.NOT_FOUND);
		return new ResponseEntity<TaiKhoan>(taikhoanOp.get(), HttpStatus.OK);
	}

	@GetMapping("taikhoan/{id}")
	public ResponseEntity<TaiKhoan> getTaiKhoanById(@PathVariable("id") String id, Principal principal) {
		if (taiKhoanRepository.findByUsername(principal.getName()).getRole().equals("user")) {
			return null;
		}
		Optional<TaiKhoan> taikhoanOp = taiKhoanRepository.findById(Long.parseLong(id));
		TaiKhoan taikhoan = taikhoanOp.get();
		taikhoan.setPassword(null);
		if (!taikhoanOp.isPresent())
			return new ResponseEntity<TaiKhoan>(HttpStatus.NOT_FOUND);
		return new ResponseEntity<TaiKhoan>(taikhoanOp.get(), HttpStatus.OK);
	}

	@PostMapping(value = "taikhoan")
	public ResponseEntity adduser(@RequestParam(value = "image", required = false) MultipartFile file,
			@RequestParam("taikhoan") String taikhoan, Principal principal)
			throws JsonMappingException, JsonProcessingException {
		if (taiKhoanRepository.findByUsername(principal.getName()).getRole().equals("user")) {
			return null;
		}
		ObjectMapper om = new ObjectMapper();
		TaiKhoan tk = om.readValue(taikhoan, TaiKhoan.class);
		System.out.println(tk);
		if (file != null && !userService.load(file.getOriginalFilename())) {
			userService.save(file);
			tk.setAnh(file.getOriginalFilename());
		}
		Optional<TaiKhoan> findTaiKhoan = Optional.ofNullable(taiKhoanRepository.findByUsername(tk.getUsername()));
		if (findTaiKhoan.isPresent()) {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		tk.setPassword(bcryptEncoder.encode(tk.getPassword()));
		taiKhoanRepository.save(tk);
		return new ResponseEntity<>(HttpStatus.CREATED);

	}

	@PutMapping("taikhoan/{id}")
	public ResponseEntity updateUser(@PathVariable("id") long id,
			@RequestParam(value = "image", required = false) MultipartFile file,
			@RequestParam("taikhoan") String taikhoans, Principal principal)
			throws JsonMappingException, JsonProcessingException {
		if (taiKhoanRepository.findByUsername(principal.getName()).getRole().equals("user")) {
			return null;
		}
		Optional<TaiKhoan> taiKhoanOpt = taiKhoanRepository.findById(id);
		ObjectMapper om = new ObjectMapper();
		TaiKhoan taikhoanss = om.readValue(taikhoans, TaiKhoan.class);
		TaiKhoan tk = taiKhoanOpt.get();
		if (!taiKhoanOpt.isPresent())
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		else {
			if (!taikhoanss.getUsername().equals(tk.getUsername())) {
				Optional<TaiKhoan> findTaiKhoan = Optional
						.ofNullable(taiKhoanRepository.findByUsername(taikhoanss.getUsername()));
				if (findTaiKhoan.isPresent()) {
					return new ResponseEntity(HttpStatus.NO_CONTENT);
				}
			}
			if (taikhoanss.getUsername() == null)
				taikhoanss.setUsername(tk.getUsername());
			if (taikhoanss.getPassword() == null)
				taikhoanss.setPassword(tk.getPassword());
			if (file != null && !file.getOriginalFilename().equals(tk.getAnh())) {
				if (!userService.load(file.getOriginalFilename())) {
					userService.save(file);
				}
				taikhoanss.setAnh(file.getOriginalFilename());
			}
			BeanUtils.copyProperties(taikhoanss, tk);
			if (taikhoanss.getPassword() != null)
				tk.setPassword(bcryptEncoder.encode(tk.getPassword()));
			return new ResponseEntity(taiKhoanRepository.save(tk), HttpStatus.OK);
		}
	}

	@PutMapping("profile")
	public ResponseEntity updateProfile(@RequestParam(value = "image", required = false) MultipartFile file,
			@RequestParam("taikhoan") String taikhoans, Principal principal)
			throws JsonMappingException, JsonProcessingException {
		TaiKhoan tkkhoan = taiKhoanRepository.findByUsername(principal.getName());
		if (taiKhoanRepository.findByUsername(principal.getName()).getRole().equals("user")) {
			return null;
		}
		Optional<TaiKhoan> taiKhoanOpt = taiKhoanRepository.findById(tkkhoan.getId());
		ObjectMapper om = new ObjectMapper();
		TaiKhoan taikhoanss = om.readValue(taikhoans, TaiKhoan.class);
		TaiKhoan tk = taiKhoanOpt.get();
		if (!taiKhoanOpt.isPresent())
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		else {
			if (!taikhoanss.getUsername().equals(tk.getUsername())) {
				Optional<TaiKhoan> findTaiKhoan = Optional
						.ofNullable(taiKhoanRepository.findByUsername(taikhoanss.getUsername()));
				if (findTaiKhoan.isPresent()) {
					return new ResponseEntity(HttpStatus.NO_CONTENT);
				}
			}
			if (taikhoanss.getUsername() == null)
				taikhoanss.setUsername(tk.getUsername());
			if (taikhoanss.getPassword() == null)
				taikhoanss.setPassword(tk.getPassword());
			if (file != null && !file.getOriginalFilename().equals(tk.getAnh())) {
				if (!userService.load(file.getOriginalFilename())) {
					userService.save(file);
				}
				taikhoanss.setAnh(file.getOriginalFilename());
			}
			BeanUtils.copyProperties(taikhoanss, tk);
			if (taikhoanss.getPassword() != null)
				tk.setPassword(bcryptEncoder.encode(tk.getPassword()));
			return new ResponseEntity(taiKhoanRepository.save(tk), HttpStatus.OK);
		}
	}

	@DeleteMapping("taikhoan/{id}")
	public ResponseEntity deleteUser(@PathVariable("id") long id, Principal principal) {
		if (taiKhoanRepository.findByUsername(principal.getName()).getRole().equals("user")) {
			return null;
		}
		Optional<TaiKhoan> taiKhoanOpt = taiKhoanRepository.findById(id);
		if (!taiKhoanOpt.isPresent())
			return new ResponseEntity(HttpStatus.NOT_FOUND);
		else {
			taiKhoanRepository.deleteById(id);
			return new ResponseEntity(HttpStatus.OK);
		}

	}
}
