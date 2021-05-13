package com.techgeeknext.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.Data;

@Data
@Entity
@Table(name = "taikhoan")
public class TaiKhoan extends BaseEntity{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String tenNguoiDung;
	private String diaChi;
	private String sdt;
	private Date ngaySinh;
	private String queQuan;
	private String chucVu;
	private String role;
	private String username;
	@Column(length = 100)
	private String password;
	@Transient
	private String passwordConfirm;
	private String anh;

}
