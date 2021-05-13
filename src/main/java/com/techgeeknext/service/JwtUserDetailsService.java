package com.techgeeknext.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.techgeeknext.entity.TaiKhoan;
import com.techgeeknext.repository.TaiKhoanRepository;

import java.util.ArrayList;

@Service
public class JwtUserDetailsService implements UserDetailsService {
	@Autowired TaiKhoanRepository taiKhoanRepository;

	@Autowired
	private PasswordEncoder bcryptEncoder;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		TaiKhoan taikhoan = taiKhoanRepository.findByUsername(username);
		if (taikhoan == null) {
			throw new UsernameNotFoundException("User not found with username: " + username);
		}
		return new org.springframework.security.core.userdetails.User(taikhoan.getUsername(), taikhoan.getPassword(),
				new ArrayList<>());
	}

	public TaiKhoan save(TaiKhoan tk) {
		tk.setPassword(bcryptEncoder.encode(tk.getPassword()));
		return taiKhoanRepository.save(tk);
	}
}