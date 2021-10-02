package com.codeimmig.yannick.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "doctor_tab")
public class Doctor {
	@Id
	@Column(name="doc_id_col")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="doc_fn_col")
	private String firstName;
	
	@Column(name="doc_ln_col")
	private String lastName;
	
	@Column(name="doc_email_col")
	private String email;

	@Column(name="doc_adress_col")
	private String address;
	
	@Column(name="doc_mob_col")
	private String  mobile;
	
	@Column(name="doc_note_col")
	private String note;
	
	@Column(name="doc_gender_col")
	private String gender;
	
	@Column(name="doc_img_col")
	private String photoLoc;
	
	//module integration
	@ManyToOne
	@JoinColumn(name="spec_id_fk_col", unique = true)
	private Specialization specialization; //HAS-A

}