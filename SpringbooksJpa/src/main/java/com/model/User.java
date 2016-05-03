package com.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;


@Entity
@Table(name = "userDetails")
public class User {

	  @Id
	  @GeneratedValue(strategy = GenerationType.AUTO)
	  @Column(name="user_id")
	  private long id;
	  
	  @NotNull
	  private String email;
	  
	  @NotNull
	  private String name;

	  @NotNull
	  private String contact;
	  
	  public User() {	}
	  
	  public User(long id){
		 this.id=id;
	  }
	 
	  public User(String email, String name, String contact) {
			this.email = email;
			this.name = name;
			this.contact = contact;
	  }
	  	  

		public long getId() {
			return id;
		}
	
		public void setId(long id) {
			this.id = id;
		}
	
		public String getEmail() {
			return email;
		}
	
		public void setEmail(String email) {
			this.email = email;
		}
	
		public String getName() {
			return name;
		}
	
		public void setName(String name) {
			this.name = name;
		}
	
		public String getContact() {
			return contact;
		}
	
		public void setContact(String contact) {
			this.contact = contact;
		}		
		
}
