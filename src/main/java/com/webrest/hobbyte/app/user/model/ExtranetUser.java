package com.webrest.hobbyte.app.user.model;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonView;
import com.webrest.hobbyte.app.posts.model.PostEntry;
import com.webrest.hobbyte.app.user.model.enums.ExtranetUserAgreement;
import com.webrest.hobbyte.app.user.model.enums.ExtranetUserRoles;
import com.webrest.hobbyte.app.user.model.enums.ExtranetUserStatus;
import com.webrest.hobbyte.app.user.model.enums.Gender;
import com.webrest.hobbyte.app.user.model.enums.NewsletterStatus;
import com.webrest.hobbyte.app.user.model.enums.RegistrationType;
import com.webrest.hobbyte.core.model.DatabaseObjectImpl;
import com.webrest.hobbyte.core.model.json.View;
import com.webrest.hobbyte.core.utils.StringUtils;

@Entity
@Table(name = "hb_extranet_users")
public class ExtranetUser extends DatabaseObjectImpl {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "hb_extranet_user_id", nullable = false, unique = true, updatable = false)
	@JsonView(View.Basic.class)
	private int id;

	@Column(name = "created_at")
	private Date createdAt = new Date();

	@Column(nullable = false, unique = true)
	@JsonView(View.Basic.class)
	private String login;

	@Column(nullable = false, unique = true)
	private String email;

	@Column(nullable = false)
	private String password;

	@Column
	@JsonView(View.Basic.class)
	private String name;

	@Column
	@JsonView(View.Basic.class)
	private String lastname;

	@Column
	private int status = ExtranetUserStatus.ACTIVE.getId();

	@Column
	private int agreement = ExtranetUserAgreement.ACCEPT.getId();

	@Column
	private Date born;

	@Column(name = "phone_number", nullable = true)
	private String phoneNumber;

	@Column(nullable = true)
	@JsonView(View.Basic.class)
	private Integer gender;

	@Column
	private int newsletter;

	@Column(name = "role_id")
	private int roleId = ExtranetUserRoles.USER.getId();

	@Transient
	private ExtranetUserPolicy userPolicy;

	@Column(name = "remember_me_code", nullable = true, unique = true)
	private String rememberMeCode;

	@Column(name = "image_url")
	private String imageUrl;

	@Column(name = "registration_type")
	private int registrationType = RegistrationType.DEFAULT.getId();

	@Column(name = "social_auth", nullable = true)
	private String socialAuthToken;

	@OneToMany(mappedBy = "author", fetch = FetchType.LAZY)
	private Collection<PostEntry> postEntries;

	@Override
	public int getId() {
		return id;
	}

	@Override
	public void setId(int id) {
		this.id = id;
	}

	public String getLogin() {
		return login;
	}

	public Collection<PostEntry> getPostEntries() {
		return postEntries;
	}

	@JsonView(View.Basic.class)
	public String getImageUrl() {
		if (StringUtils.isEmpty(imageUrl))
			return "/images/gandalf.jpg";
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
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

	public ExtranetUserRoles getRole() {
		return ExtranetUserRoles.getById(roleId);
	}

	public void setRole(ExtranetUserRoles role) {
		this.roleId = role.getId();
	}

	public String getRememberMeCode() {
		return rememberMeCode;
	}

	public void setRememberMeCode(String rememberMeCode) {
		this.rememberMeCode = rememberMeCode;
	}

	public String getFullName() {
		return name + " " + lastname;
	}

	public RegistrationType getRegistrationType() {
		return RegistrationType.getById(registrationType);
	}

	public void setRegistrationType(RegistrationType registrationType) {
		this.registrationType = registrationType.getId();
	}

	public String getSocialAuthToken() {
		return socialAuthToken;
	}

	public void setSocialAuthToken(String socialAuthToken) {
		this.socialAuthToken = socialAuthToken;
	}

	public ExtranetUserPolicy createOrGetUserPolicy() {
		if (userPolicy != null)
			return userPolicy;
		this.userPolicy = new ExtranetUserPolicy(this);
		return userPolicy;
	}

}
