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
	private int isbn;
	
	@NotNull
	private String bookName;
	
	@NotNull
	private String bookDescription;
	
	@NotNull
	private int price;
	
	public Books() {	}
  
	public Books(int isbn){
		this.isbn=isbn;
	}
	
	/*public Books(int isbn, String bookName, String bookDescription, int price) {
		this.isbn = isbn;
		this.bookName = bookName;
		this.bookDescription = bookDescription;
		this.price = price;
	}*/

	public Books( String bookName, String bookDescription, int price) {
			this.bookName=bookName;
			this.bookDescription=bookDescription;
			this.price=price;
	}

	public int getIsbn() {
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

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}
		
}
