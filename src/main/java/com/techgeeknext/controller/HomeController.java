package com.techgeeknext.controller;
import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.techgeeknext.repository.TaiKhoanRepository;
@RestController
@CrossOrigin(origins = "http://localhost:8081", maxAge = 3600)
public class HomeController {
	@Autowired private TaiKhoanRepository taikhoanRepository;
    @RequestMapping(value = "/api/greeting", method = RequestMethod.GET)
    public String getEmployees(Principal principal) {
        return "Welcome  " +taikhoanRepository.findByUsername(principal.getName()).getTenNguoiDung() ;
    }
  
}