package com.model;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;



@Entity
@Table(name="purchases")
public class Purchases {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long orderId;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date creationTime;
	
	
	@Column(name="user_id")
	private Long userId;
	
	@Column(name="isbn")
	private Long isbn;
	
	@ManyToOne(cascade=CascadeType.ALL,fetch = FetchType.LAZY)
	@JoinColumn(name="user_id",insertable=false, updatable=false)
	private User user;
	
	
	@ManyToOne(cascade=CascadeType.ALL,fetch = FetchType.LAZY)
	@JoinColumn(name="isbn",insertable=false, updatable=false)
	private Books book;
	
	public Purchases() { }
	
	public Purchases(Long orderId, Date creation_time, Long user_id, Long isbn) {
		super();
		this.orderId = orderId;
		this.creationTime = creation_time;
		this.userId = user_id;
		this.isbn = isbn;
	}

	public Long getOrderId() {
		return orderId;
	}



	public void setOrder_id(Long orderId) {
		this.orderId = orderId;
	}



	public Date getCreation_time() {
		return creationTime;
	}



	public void setCreation_time(Date creation_time) {
		this.creationTime = creation_time;
	}



	public Long getUserId() {
		return userId;
	}



	public void setUser_id(Long userId) {
		this.userId = userId;
	}



	public Long getIsbn() {
		return isbn;
	}



	public void setIsbn(Long isbn) {
		this.isbn = isbn;
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

	@Override
	public String toString() {
		return "Purchases [orderId=" + orderId + ", creationTime=" + creationTime + ", userId=" + userId + ", isbn="
				+ isbn + ", user=" + user + ", book=" + book + "]";
	}

	
	
	
}
