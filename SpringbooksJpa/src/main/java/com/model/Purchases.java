package com.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="purchases")
public class Purchases {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long order_id;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date creation_time;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="user_id")
	private User user;
	
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="isbn")
	private Books book;
	
	public Purchases() {	}

	public Long getOrder_id() {
		return order_id;
	}

	public void setOrder_id(Long order_id) {
		this.order_id = order_id;
	}

	public Date getCreation_time() {
		return creation_time;
	}

	public void setCreation_time(Date creation_time) {
		this.creation_time = creation_time;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Books getBook() {
		return book;
	}

	public void setBook(Books book) {
		this.book = book;
	}

	public Purchases(long order_id){
		this.order_id=order_id;
	}
	
	public Purchases(Date creation_time, User user, Books book) {
		this.creation_time = creation_time;
		this.user = user;
		this.book = book;
	}
	
}
