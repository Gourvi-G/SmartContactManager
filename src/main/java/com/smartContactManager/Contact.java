package com.smartContactManager;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="CONTACT")
public class Contact {
    
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int C_Id;
	private String name;
	private String secondName;
	private String work;
	private String email;
	private String phone;
	private String C_imageUrl;
	@Column(length=50000)
	private String description;
	
	@ManyToOne
	@JsonIgnore
	private User user;
	
	//getters and setters
	public int getC_Id() {
		return C_Id;
	}
	public void setC_Id(int c_Id) {
		C_Id = c_Id;
	}
	public String getName() {
		return name;
	}
	public void setName(String c_name) {
		this.name = c_name;
	}
	public String getSecondName() {
		return secondName;
	}
	public void setSecondName(String secondName) {
		this.secondName = secondName;
	}
	public String getWork() {
		return work;
	}
	public void setWork(String work) {
		this.work = work;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getC_imageUrl() {
		return C_imageUrl;
	}
	public void setC_imageUrl(String c_imageUrl) {
		this.C_imageUrl = c_imageUrl;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		return this.C_Id==((Contact)obj).getC_Id();
	}


	
	
	
//	@Override
//	public String toString() {
//		return "Contact [C_Id=" + C_Id + ", name=" + name + ", secondName=" + secondName + ", work=" + work + ", email="
//				+ email + ", phone=" + phone + ", C_imageUrl=" + C_imageUrl + ", description=" + description + ", user="
//				+ user + "]";
//	}
	
	
	
	
	
}
