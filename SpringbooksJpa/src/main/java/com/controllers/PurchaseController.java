package com.controllers;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
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
	public @ResponseBody ResponseEntity<User> createUser(@RequestBody User userDetails) throws Exception{
		User user=null;
	
		//for checking update user 
		user=userDAO.findOne(userDetails.getId());
		if (user==null){
			//insert new user 
			try{
				user=new User(userDetails.getEmail(),userDetails.getName(),userDetails.getContact(),userDetails.getIsActive(), new Date(),new Date());
				userDAO.save(user);
				}catch(Exception e){
					throw new Exception("Exception in saving user details...",e);
				}
		}else{  
				userDetails.setCreationTime(user.getCreationTime());
				userDetails.setLastModifiedTime(new Date());
				userDAO.save(userDetails);
			}
		
			return  new ResponseEntity<User>(user, HttpStatus.OK);
	}
		
	//Method for creating books 
	@RequestMapping(value="/book",method=RequestMethod.POST)
	public @ResponseBody ResponseEntity<Books> createBooks(@RequestBody Books bookDetails) throws Exception{
		Books book=null;
		
		//for checking duplicate entry of books
		book=booksDAO.findOne(bookDetails.getIsbn());
		if(book==null){
			//create a new book entry 
			try{
				book=new Books(bookDetails.getBookName(),bookDetails.getBookDescription(),bookDetails.getPrice(),bookDetails.getIsActive(),new Date(),new Date());
				booksDAO.save(book);
				}catch(Exception e){
					throw new Exception("Exception in saving book details...",e);
				}
		}else{
			  bookDetails.setCreationTime(book.getCreationTime());
			  bookDetails.setLastModifiedTime(new Date());
			  booksDAO.save(bookDetails);
		}
			return new ResponseEntity<Books>(book, HttpStatus.OK);
	}

	//For purchasing books 
	@RequestMapping(value="/purchase",method=RequestMethod.POST)
	public @ResponseBody ResponseEntity<Purchases> purchase(@RequestBody Purchases purchases)throws Exception{
		Books book=null;
		User user=null;
		Purchases purchase=null;
		Date creationDate=new Date();
		
		if(purchases.getOrderId()==null){

			//check whether book exists in the database 
			book=booksDAO.findOne(purchases.getIsbn());
			user=userDAO.findOne(purchases.getUserId());
			if(book==null || user==null){
				return new ResponseEntity<Purchases>(HttpStatus.NOT_FOUND);		
			}
			if(!user.getIsActive()){
				throw new Exception(" User with id : "+user.getId()+" not allowed to purchase books !!!");
			}
			if(!book.getIsActive()){
				throw new Exception(" Book with name : "+book.getBookName()+" is not allowed to purchase !!!");
			}
			
			Optional<Purchases> purchaseOptional = purchaseDAO.findByIsbnAndUserId(book.getIsbn(),user.getId());
			if(!purchaseOptional.isPresent()){
					purchase = new Purchases(null,creationDate,creationDate,purchases.getUserId(),purchases.getIsbn(),true,user,book);
					purchaseDAO.save(purchase);					
				}else{			
					return new ResponseEntity<Purchases>(HttpStatus.CONFLICT);		//book already purchases 				
				}	
		}else{				//first if checking 
			return new ResponseEntity<Purchases>(HttpStatus.CONFLICT);				//order id already created			
		}
		return new ResponseEntity<Purchases>(purchase, HttpStatus.OK);
	} 
		
	//for fetching user details of a single user 
	@RequestMapping(value="/user/{id}",method=RequestMethod.GET)
	public @ResponseBody ResponseEntity<User> getOneUserDetails(@PathVariable Long id)throws Exception{
		User user=null;
			user=userDAO.findOne(id);
			if(user!=null)
				return new ResponseEntity<User>(user, HttpStatus.OK);
			else {
				return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
			}
	}
	
	//for fetching book details of a single book 
	@RequestMapping(value="/book/{isbn}",method=RequestMethod.GET)
	public @ResponseBody ResponseEntity<Books> getOneBookDetails(@PathVariable Long isbn)throws Exception{
		Books book=null;
		
			book=booksDAO.findOne(isbn);
			if(book!=null)
				return new ResponseEntity<Books>(book, HttpStatus.OK);
			else {
				return new ResponseEntity<Books>(HttpStatus.NOT_FOUND);
			}
	}
	
	//for fetching purchase details of a single user 
		@RequestMapping(value="/purchase/user/{userId}",method=RequestMethod.GET)
		public @ResponseBody ResponseEntity<List<Purchases>> getUserPurchaseDetails(@PathVariable Long userId)throws Exception{
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
				 return new ResponseEntity<List<Purchases>>(purchase, HttpStatus.OK);
			}
			else {
				return new ResponseEntity<List<Purchases>>(HttpStatus.NOT_FOUND);
			}
		}
		
	     //for fetching all the users details 
		 @RequestMapping(value="/user/all",method=RequestMethod.GET)
		 public @ResponseBody ResponseEntity<List<User>> getAllUsers(){
			 List<User> user=(List<User>) userDAO.findAll();
			 return new ResponseEntity<List<User>>(user,HttpStatus.OK);
		 }
		 
		 //for fetching all the book details
		 @RequestMapping(value="/book/all",method=RequestMethod.GET)
		 public @ResponseBody ResponseEntity<List<Books>> getAllBooks(){
			 List<Books> books=(List<Books>) booksDAO.findAll();
			 return new ResponseEntity<List<Books>>(books,HttpStatus.OK);
		 }
		 
		 //for fetching all the purchase details 	
		 @RequestMapping(value="/purchase/all",method=RequestMethod.GET)
		 public @ResponseBody ResponseEntity<List<Purchases>> getAllPurchases(){
			 List<Purchases> purchase=(List<Purchases>) purchaseDAO.findAll();
			 Books books=null;
			 User user=null;
			 for(Purchases  purch : purchase) {
				  books=booksDAO.findOne(purch.getIsbn());
				  purch.setBook(books);
				  user=userDAO.findOne(purch.getUserId());
				  purch.setUser(user);
			  }
			 return new ResponseEntity<List<Purchases>>(purchase, HttpStatus.OK);
		 }
		 
		 
		 //for deleting the user details 
			@RequestMapping(value="/user/{id}",method=RequestMethod.DELETE)
			public @ResponseBody ResponseEntity<User> deleteUser(@PathVariable Long id)throws Exception{
				User user=null;
				List<Purchases> purchase=null;
				user=userDAO.findOne(id);
				if(user!=null)
				{	try{	
							user.setLastModifiedTime(new Date());
							purchase=purchaseDAO.findByUserId(user.getId());
							if(user.getIsActive())	
								{	for(Purchases purch : purchase){			    //getting all the payments of that user and making it offline 
										purch.setLastModifiedTime(new Date());
										purch.setIsActive(false);
									}
									user.setIsActive(false);
								}
							else
								{	for(Purchases purch : purchase){
										purch.setLastModifiedTime(new Date());
										purch.setIsActive(true);
									}
									user.setIsActive(true);
								}
							
							userDAO.save(user);
							purchaseDAO.save(purchase);								//user status and his payment details also modified
							return new ResponseEntity<User>(user, HttpStatus.OK);
						}catch(Exception e){
							throw new Exception("Could not change status of the user. ",e);
						}
				}else
					return new ResponseEntity<User>(HttpStatus.NOT_FOUND); 
			}
			
			//for deleting the book details 
			@RequestMapping(value="/book/{isbn}",method=RequestMethod.DELETE)
			public @ResponseBody ResponseEntity<Books> deleteBookDetails(@PathVariable Long isbn)throws Exception{
				Books book=null;
				book=booksDAO.findOne(isbn);
				if(book!=null)
				{	try{	
							book.setLastModifiedTime(new Date());
							if(book.getIsActive())
								book.setIsActive(false);
							else
								book.setIsActive(true);
							booksDAO.save(book);
							return new ResponseEntity<Books>(book, HttpStatus.OK);
						}catch(Exception e){
							throw new Exception("Could not change the status of book. ",e);
						}
				}else
					return new ResponseEntity<Books>(HttpStatus.NOT_FOUND); 
			}
			
			//for deleting the purchase details 
			@RequestMapping(value="/purchase/{orderId}",method=RequestMethod.DELETE)
			public @ResponseBody ResponseEntity<Purchases> deletePurchaseDetails(@PathVariable Long orderId)throws Exception{
				Purchases purchase=null;
				purchase=purchaseDAO.findOne(orderId);
				if(purchase!=null)
				{	try{	
							purchase.setLastModifiedTime(new Date());
							if(purchase.getIsActive())
								purchase.setIsActive(false);
							else
								purchase.setIsActive(true);
							purchaseDAO.save(purchase);
							return new ResponseEntity<Purchases>(purchase, HttpStatus.OK);
						}catch(Exception e){
							throw new Exception("Could not change status of purchase details. ",e);
						}
				}else
					return new ResponseEntity<Purchases>(HttpStatus.NOT_FOUND);  
			}
			
			//get user based on active or inactive
			@RequestMapping(value="/user/isActive/{isActive}",method=RequestMethod.GET)
			public @ResponseBody ResponseEntity<List<User>> getAllIsActiveUsers(@PathVariable boolean isActive)throws Exception{
				List<User> user=null;
				user=userDAO.findByIsActive(isActive);
					return new  ResponseEntity<List<User>>(user,HttpStatus.OK);
			}
			
			//get books based on active or inactive
			@RequestMapping(value="/books/isActive/{isActive}",method=RequestMethod.GET)
			public @ResponseBody ResponseEntity<List<Books>> getAllIsActiveBooks(@PathVariable boolean isActive)throws Exception{
				List<Books> book=null;
				book=booksDAO.findByIsActive(isActive);				
					return new  ResponseEntity<List<Books>>(book,HttpStatus.OK);
			}

			//get books based on active or inactive
			@RequestMapping(value="/purchase/isActive/{isActive}",method=RequestMethod.GET)
			public @ResponseBody ResponseEntity<List<Purchases>> getAllIsActivePurchases(@PathVariable boolean isActive)throws Exception{
				List<Purchases> purchase=null;
				purchase=purchaseDAO.findByIsActive(isActive);
						return new  ResponseEntity<List<Purchases>>(purchase,HttpStatus.OK);					
			}
}