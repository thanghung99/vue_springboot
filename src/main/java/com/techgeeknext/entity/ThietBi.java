package com.techgeeknext.entity;

import java.util.Date;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Data
@Table(name = "thietbi")
public class ThietBi extends BaseEntity{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String tenThietBi;
	private String hangSX;
	@ManyToOne
	@JoinColumn(name = "maLoai")
	private Loai loai;
	
}
