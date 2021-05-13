package com.techgeeknext.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Entity
@Data
@Table(name = "muon_tra")
public class MuonTra implements Serializable {
	@EmbeddedId
	private TaiKhoanThietBi taiKhoanThietBi;
	
	@ManyToOne
	@MapsId("maThietBi")
    @JoinColumn(name = "maThietBi")
    private ThietBi thietBi;
	
	@ManyToOne
	@MapsId("maTaiKhoan")
    @JoinColumn(name = "maTaiKhoan")
    private TaiKhoan taiKhoan;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date ngayMuon;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date ngayTra;
	private String lyDo;
	private int status;
	private int tra;
}
