package com.bezkoder.springjwt.models;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "profile")
public class Profile {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "prof_id")
	Integer profId;

	@Column(name="address")
	String address;

	@Column(name="city")
	String city;
	
	@Column(name="state")
	String state;

	@Column(name="country")
	String country;

	@Column(name="pin_code")
	String pinCode;

	@Column(name="about_me")
	String aboutMe;
	
	@Column(name="contact_no")
	String contactNo;
	
	@Column(name="profile_picture")
	String profilePicture;
	
	public String getContactNo() {
		return contactNo;
	}

	public void setContactNo(String contactNo) {
		this.contactNo = contactNo;
	}

	public String getProfilePicture() {
		return profilePicture;
	}

	public void setProfilePicture(String profilePicture) {
		this.profilePicture = profilePicture;
	}


	@JsonBackReference 
	@OneToOne(mappedBy = "profile",cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	User user;

	public Profile() {
		
	}
	
	
	public Profile(Integer profId, String address, String city, String state, String country, String pinCode,
			String aboutMe, String contactNo, String profilePicture) {
		super();
		this.profId = profId;
		this.address = address;
		this.city = city;
		this.state = state;
		this.country = country;
		this.pinCode = pinCode;
		this.aboutMe = aboutMe;
		this.contactNo = contactNo;
		this.profilePicture = profilePicture;
	}

	public Integer getProfId() {
		return profId;
	}

	public void setProfId(Integer profId) {
		this.profId = profId;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getPinCode() {
		return pinCode;
	}

	public void setPinCode(String pinCode) {
		this.pinCode = pinCode;
	}

	public String getAboutMe() {
		return aboutMe;
	}

	public void setAboutMe(String aboutMe) {
		this.aboutMe = aboutMe;
	}
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return "Profile [profId=" + profId + ", address=" + address + ", city=" + city + ", state=" + state
				+ ", country=" + country + ", pinCode=" + pinCode + ", aboutMe=" + aboutMe + ", contactNo=" + contactNo
				+ ", profilePicture=" + profilePicture + "]";
	}


	
}
