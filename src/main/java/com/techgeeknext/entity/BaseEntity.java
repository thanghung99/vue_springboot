package com.techgeeknext.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import lombok.Data;

@MappedSuperclass
@Data
public class BaseEntity implements Serializable{
	private int status;
	@Column(name = "createdBy")
	private String createdBy;
	@Column(name = "createdDate")
	private Date createdDate;
	@Column(name = "modifiedBy")
	private String modifiedBy;
	@Column(name = "modifiedDate")
	private Date modifiedDate;
}
