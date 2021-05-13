package com.techgeeknext.entity;

import java.io.Serializable;

import javax.persistence.Embeddable;

import lombok.Data;

@Embeddable
@Data
public class TaiKhoanThietBi implements Serializable {
	private long maThietBi;	
	private long maTaiKhoan;	
}
