package com.controllers;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.SystemPropertyUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
		
		//for checking update user in case of duplicate entry
		user=userDAO.findOne(userDetails.getId());
		if (user==null){
				//for inserting new user 
			try{
				user=new User(userDetails.getEmail(),userDetails.getName(),userDetails.getContact());
				userDAO.save(user);
				}catch(Exception e){
					throw new Exception("Exception in saving user details...",e);
				}
		}else{
				throw new Exception(" User with id "+userDetails.getId()+" already exists...");
			}
		
			return user;
	}
		
	//Method for creating books 
	@RequestMapping(value="/book",method=RequestMethod.POST)
	public @ResponseBody Books createBooks(@RequestBody Books bookDetails) throws Exception{
		Books book=null;
		
		//for checking duplicate entry of books
		book=booksDAO.findOne(bookDetails.getIsbn());
		if(book==null){
			//create a new book entry 
			try{
				book=new Books(bookDetails.getBookName(),bookDetails.getBookDescription(),bookDetails.getPrice());
				booksDAO.save(book);
				}catch(Exception e){
					throw new Exception("Exception in saving book details...",e);
				}
		}else{
			throw new Exception(" Book with isbn "+bookDetails.getIsbn()+" already exists.");  
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
		
		if(purchases.getOrderId()==null){

			//check whether book exists in the database 
			book=booksDAO.findOne(purchases.getIsbn());
			user=userDAO.findOne(purchases.getUserId());
			if(book==null || user==null){
				throw new Exception("Entered book isbn or user id not found !!!");			
			}
			
			Optional<Purchases> purchaseOptional = purchaseDAO.findByIsbnAndUserId(book.getIsbn(),user.getId());
			if(!purchaseOptional.isPresent()){
					purchase = new Purchases(null,creation_date,purchases.getUserId(),purchases.getIsbn());
					purchaseDAO.save(purchase);
					purchase.setBook(book);
					purchase.setUser(user);
				}else{			
					throw new Exception("Book already purchased !!!");				
				}
			System.out.println(" After purchase operation completed !!!");
		}else{				//first if checking 
			throw new Exception(" Order_id already used !!! ");
		}
		return purchase;
	} 
		
	//for fetching user details of a single user 
	@RequestMapping(value="/user/{id}",method=RequestMethod.GET)
	public @ResponseBody User getOneUserDetails(@PathVariable Long id)throws Exception{
		User user=null;
			user=userDAO.findOne(id);
			if(user!=null)
				return user;
			else {
				throw new Exception(" User with id: "+id+" does not exists !");
			}
	}
	
	//for fetching book details of a single book 
		@RequestMapping(value="/book/{isbn}",method=RequestMethod.GET)
		public @ResponseBody Books getOneBookDetails(@PathVariable Long isbn)throws Exception{
			Books book=null;
			
				book=booksDAO.findOne(isbn);
				if(book!=null)
					return book;
				else {
					throw new Exception(" Book  with isbn: "+isbn+" does not exists !");
				}
		}
		
	//for fetching purchase details of a single user 
			@RequestMapping(value="/purchase/{userId}",method=RequestMethod.GET)
			public @ResponseBody List<Purchases> getUserPurchaseDetails(@PathVariable Long userId)throws Exception{
				List<Purchases> purchase=null;
				User user=null;
				Books book=null;
					user=userDAO.findOne(userId);
					if(user==null){
						throw new Exception(" User with id : "+userId+" does not exists !!!"); 
					}
					purchase=purchaseDAO.findByUserId(userId);
					if(purchase!=null){
						 for(Purchases  purch : purchase) {
							  book=booksDAO.findOne(purch.getIsbn());
							  purch.setBook(book);
						  }
						return purchase;
					}
					else {
						throw new Exception(" Book  with isbn: "+userId+" does not exists !");
					}
			}
			
		//for deleting the user details 
			@RequestMapping(value="/user/{id}",method=RequestMethod.DELETE)
			public @ResponseBody String deleteUser(@PathVariable Long id)throws Exception{
				User user=null;
				user=userDAO.findOne(id);
				if(user!=null)
				{	try{userDAO.delete(id);
							return "User deleted !!";
						}catch(Exception e){
							throw new Exception("could not delete the user. ",e);
						}
				}else
					return " User does not exits !!!!"; 
			}
			
			//for deleting the book details 
			@RequestMapping(value="/book/{isbn}",method=RequestMethod.DELETE)
			public @ResponseBody String deleteBookDetails(@PathVariable Long isbn)throws Exception{
				Books book=null;
				book=booksDAO.findOne(isbn);
				if(book!=null)
				{	try{booksDAO.delete(isbn);
							return "Book details deleted !!";
						}catch(Exception e){
							throw new Exception("Could not delete the book details. ",e);
						}
				}else
					return " Book does not exits !!!!"; 
			}
			
			//for deleting the purchase details 
			@RequestMapping(value="/purchase/{orderId}",method=RequestMethod.DELETE)
			public @ResponseBody String deletePurchaseDetails(@PathVariable Long orderId)throws Exception{
				Purchases purchase=null;
				purchase=purchaseDAO.findOne(orderId);
				if(purchase!=null)
				{	try{booksDAO.delete(orderId);
							return "Purchase  details deleted !!";
						}catch(Exception e){
							throw new Exception("Could not delete the purchase details. ",e);
						}
				}else
					return " Purchase details does not exits !!!!"; 
			}
			
}
