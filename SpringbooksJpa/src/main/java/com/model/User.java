package com.model;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;


@Entity
@Table(name = "user")
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
	  private int contact;
	  
	  public User() {	}
	  
	  @OneToMany
	  @JoinTable( name="pruchases",joinColumns=@JoinColumn(name="user_id"),
	  inverseJoinColumns=@JoinColumn(name="isbn")) //its optional using for name configuration of the join table
	    
		private Collection<Books> vehicle = new ArrayList<Books>();

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
	
		public int getContact() {
			return contact;
		}
	
		public void setContact(int contact) {
			this.contact = contact;
		}

		public Collection<Books> getVehicle() {
			return vehicle;
		}

		public void setVehicle(Collection<Books> vehicle) {
			this.vehicle = vehicle;
		}

			
}
