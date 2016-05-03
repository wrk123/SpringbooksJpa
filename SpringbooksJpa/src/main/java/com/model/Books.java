package com.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="books")
public class Books {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="isbn")
	private long isbn;
	
	@NotNull
	private String bookName;
	
	@NotNull
	private String bookDescription;
	
	@NotNull
	private String price;
	
	public Books() {	}
  
	public Books(long isbn){
		this.isbn=isbn;
	}

	public Books( String bookName, String bookDescription, String price) {
			this.bookName=bookName;
			this.bookDescription=bookDescription;
			this.price=price;
	}

	public long getIsbn() {
		return isbn;
	}

	public void setIsbn(int isbn) {
		this.isbn = isbn;
	}

	public String getBookName() {
		return bookName;
	}

	public void setBookName(String bookName) {
		this.bookName = bookName;
	}

	public String getBookDescription() {
		return bookDescription;
	}

	public void setBookDescription(String bookDescription) {
		this.bookDescription = bookDescription;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}
		
}
