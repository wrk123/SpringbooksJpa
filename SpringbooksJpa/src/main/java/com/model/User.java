package com.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;


@Entity
@Table(name = "userDetails")
public class User implements Serializable{

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
	  
	  @NotNull
	  private boolean isActive;
	  
	  @Temporal(TemporalType.TIMESTAMP)
	  private Date creationTime;
		
	  @Temporal(TemporalType.TIMESTAMP)
	  private Date lastModifiedTime;
		
	  public User() {	}
	  
	  public User(long id){
		 this.id=id;
	  }
	  
	 
		public User(String email, String name, String contact, boolean isActive, Date creationTime, Date lastModifiedTime) {
			super();
			this.email = email;
			this.name = name;
			this.contact = contact;
			this.isActive = isActive;
			this.creationTime = creationTime;
			this.lastModifiedTime = lastModifiedTime;
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
	
		public boolean getIsActive() {
			return isActive;
		}
	
		public void setIsActive(boolean isActive) {
			this.isActive = isActive;
		}
	
		public Date getCreationTime() {
			return creationTime;
		}
	
		public void setCreationTime(Date creationTime) {
			this.creationTime = creationTime;
		}
	
		public Date getLastModifiedTime() {
			return lastModifiedTime;
		}
	
		public void setLastModifiedTime(Date lastModifiedTime) {
			this.lastModifiedTime = lastModifiedTime;
		}
	
		@Override
		public String toString() {
			return "User [id=" + id + ", email=" + email + ", name=" + name + ", contact=" + contact + ", isActive="
					+ isActive + ", creationTime=" + creationTime + ", lastModifiedTime=" + lastModifiedTime + "]";
		}
	  
}
