package com.webrest.hobbyte.app.user.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.webrest.hobbyte.app.user.model.enums.ExtranetUserAgreement;
import com.webrest.hobbyte.app.user.model.enums.ExtranetUserStatus;
import com.webrest.hobbyte.app.user.model.enums.Gender;
import com.webrest.hobbyte.app.user.model.enums.NewsletterStatus;

@Entity
@Table(name = "hb_extranet_users")
public class ExtranetUser {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "hb_extranet_user_id", nullable = false, unique = true, updatable = false)
	private int id;

	@Column(name = "created_at")
	private Date createdAt = new Date();

	@Column(nullable = false, unique = true)
	private String login;

	@Column(nullable = false, unique = true)
	private String email;

	@Column(nullable = false)
	private String password;

	@Column
	private String name;

	@Column
	private String lastname;
	
	@Column
	private int status;
	
	@Column
	private int agreement = ExtranetUserAgreement.NOT_ACCEPT.getId();

	@Column
	private Date born;
	
	@Column(name = "phone_number", nullable = true)
	private String phoneNumber;
	
	@Column(nullable = true)
	private Integer gender;

	@Column
	private int newsletter;
	
	@Transient
	private ExtranetUserPolicy userPolicy = new ExtranetUserPolicy(this);
	
	public ExtranetUser() {

	}

	public int getId() {
		return id;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public boolean isAgreement() {
		return agreement == ExtranetUserAgreement.ACCEPT.getId();
	}

	public ExtranetUserAgreement getAgreement() {
		return ExtranetUserAgreement.getById(agreement);
	}

	public void setAgreement(ExtranetUserAgreement agreement) {
		this.agreement = agreement.getId();
	}

	public Date getBorn() {
		return born;
	}
	
	public void setBorn(Date born) {
		this.born = born;
	}

	public ExtranetUserStatus getStatus() {
		return ExtranetUserStatus.findById(status);
	}

	public void setStatus(ExtranetUserStatus status) {
		this.status = status.getId();
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public Gender getGender() {
		return Gender.getById(gender == null ? -1 : gender);
	}

	public void setGender(Gender gender) {
		this.gender = gender.getId();
	}

	public NewsletterStatus getNewsletter() {
		return NewsletterStatus.getById(newsletter);
	}

	public void setNewsletter(NewsletterStatus newsletter) {
		this.newsletter = newsletter.getId();
	}

	public ExtranetUserPolicy getUserPolicy() {
		return userPolicy;
	}
	

}
