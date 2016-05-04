package com.controllers;


import java.text.BreakIterator;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.dao.BooksRepository;
import com.dao.PurchasesRepository;
import com.dao.UserRepository;
import com.model.Books;
import com.model.Purchases;
import com.model.User;


@RestController
public class PurchaseController {

	@Autowired
	UserRepository userDAO;
	
	@Autowired
	BooksRepository booksDAO;
	
	@Autowired
	PurchasesRepository purchaseDAO;
		
	//Method for creating users 
	@RequestMapping(value="/user",method=RequestMethod.POST)
	public @ResponseBody User createUser(@RequestBody User userDetails) throws Exception{
		User user=null;
		try{
			user=new User(userDetails.getEmail(),userDetails.getName(),userDetails.getContact());
			userDAO.save(user);
			}catch(Exception e){
				throw new Exception("Exception in saving user details...",e);
			}	
		  return user;
	}
		
	//Method for creating b 
	@RequestMapping(value="/book",method=RequestMethod.POST)
	public @ResponseBody Books createBooks(@RequestBody Books bookDetails) throws Exception{
		Books book=null;
		try{
			book=new Books(bookDetails.getBookName(),bookDetails.getBookDescription(),bookDetails.getPrice());
			booksDAO.save(book);
			}catch(Exception e){
				throw new Exception("Exception in saving book details...",e);
			}	
		  return book;
	}

	//For purchasing books 
	@RequestMapping(value="/purchase",method=RequestMethod.POST)
	public @ResponseBody Purchases purchase(@RequestBody Purchases purchases)throws Exception{
		Books book=null;
		User user=null;
		Purchases purchase=null;
		Date creation_date=new Date();
		
		try{
			book=booksDAO.findOne(purchases.getIsbn());
			user=userDAO.findOne(purchases.getUser_id());
	
			if(book==null || user==null){
				throw new Exception("Entered book isbn or user id not found !!!");			
				 
			}
			if(purchaseDAO.equals(book) && purchaseDAO.equals(user)){
				throw new Exception("Book already purchased !!!");				
			
			}
			purchase = new Purchases(creation_date,purchases.getUser_id(),purchases.getIsbn());
			purchaseDAO.save(purchase);
			
		}catch(Exception e){
			throw new Exception("Exception occured in saving book details....",e);
		}
		
		return purchase;
	} 
		
}
