package com.techgeeknext.controller;

import com.techgeeknext.config.JwtTokenUtil;
import com.techgeeknext.entity.TaiKhoan;
import com.techgeeknext.model.JwtRequest;
import com.techgeeknext.model.JwtResponse;
import com.techgeeknext.repository.TaiKhoanRepository;
import com.techgeeknext.service.JwtUserDetailsService;

import java.security.Principal;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:8081", maxAge = 3600)
public class JwtAuthenticationController {

	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private TaiKhoanRepository taikhoanRepository;
	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private JwtUserDetailsService userDetailsService;

	@RequestMapping(value = "/api/login", method = RequestMethod.POST)
	public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {

		authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());

		final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());

		final String token = jwtTokenUtil.generateToken(userDetails);

		return ResponseEntity.ok(new JwtResponse(token));
	}

	@GetMapping("/api/role")
	public ResponseEntity<?> checkRole(Principal principal) {
			
		Optional<TaiKhoan> taikhoanOpt = Optional.ofNullable(taikhoanRepository.findByUsername(principal.getName()));
		if(taikhoanOpt.isPresent()) {
			if(taikhoanOpt.get().getRole().equals("admin"))
				return new ResponseEntity<>("admin",HttpStatus.OK);
			else return new ResponseEntity<>("user",HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public ResponseEntity<?> saveUser(@RequestBody TaiKhoan taikhoan) {
		return ResponseEntity.ok(userDetailsService.save(taikhoan));
	}

	private void authenticate(String username, String password) throws Exception {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		} catch (DisabledException e) {
			throw new Exception("USER_DISABLED", e);
		} catch (BadCredentialsException e) {
			throw new Exception("INVALID_CREDENTIALS", e);
		}
	}
}
