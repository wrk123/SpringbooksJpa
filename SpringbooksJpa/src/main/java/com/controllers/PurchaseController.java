package com.controllers;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dao.BooksDAO;
import com.dao.PurchasesDAO;
import com.dao.UserDAO;
import com.model.Books;
import com.model.Purchases;
import com.model.User;





@Controller
public class PurchaseController {

	@Autowired
	UserDAO userDAO;
	
	@Autowired
	BooksDAO booksDAO;
	
	@Autowired
	PurchasesDAO purchaseDAO;
	
	
	//Method for creating users 
	@RequestMapping("/createUser")
	@ResponseBody
	public String createUser(String email, String name, String contact){
		User user=null;
		try{
			user=new User(email,name,contact);
			userDAO.save(user);
			}catch(Exception e){
				return "Problem occured while saving user :"+e; 
			}	
		  return "User succesfully created! (id = " + user.getId() + ")";
	}
	
	
	//Method for creating b 
	@RequestMapping("/createBook")
	@ResponseBody
	public String createBooks(String bookName, String bookDesc, String price){
		Books book=null;
		try{
			book=new Books(bookName,bookDesc,price);
			booksDAO.save(book);
			}catch(Exception e){
				return "Problem occured while saving book details :"+e; 
			}	
		  return "Book succesfully created! (isbn = " + book.getIsbn() + " name = "+book.getBookName()+")";
	}

	//For purchasing books 
	@RequestMapping("/purchase")
	@ResponseBody
	public String purchase(Long user_id, Long isbn){
		Books book=null;
		User user=null;
		Purchases purchase=null;
		Date creation_date=new Date();
		try{
			book=booksDAO.findOne(isbn);
			user=userDAO.findOne(user_id);
			System.out.println("book= "+book+" and user= "+user+"");
			if(book.getIsbn()==0 || user.getId()==0){
				return "Book with isbn="+isbn+" or user with user id ="+user_id+" not found.";
			}
			purchase = new Purchases(creation_date,user,book);
			purchaseDAO.save(purchase);
			
		}catch(Exception e){
			return "Exception occured while purchasing book ["+e+"]";
		}
		
		return "Book purchased";
	} 
	
	
		
}
