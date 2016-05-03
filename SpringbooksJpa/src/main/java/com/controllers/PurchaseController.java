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
	
	
	@RequestMapping("/purchaseBooks")
	@ResponseBody
	public String purchaseBooks(Long user_id, Long isbn){
		Set<Books> book =null;
		Books bk=null;
		User user=null;
		User users=null;
		try{
			book = new HashSet<Books>();
			bk=booksDAO.findOne(isbn);
			System.out.println(":: Books object ::["+bk.getIsbn()+"]");
			book.add(bk);
			users=userDAO.findOne(user_id);
			//user = new User(users.getEmail(),users.getName(),user.getContact(),book);
			System.out.println(":: Inside if , user :"+users.getEmail()+","+users.getName()+","+user.getContact());
			userDAO.save(user);
			System.out.println("book= "+book+" and user= "+user+"");
			
			
			
		}catch(Exception e){
			return "Exception occured while purchasing book ["+e+"]";
		}
		
		return "Book purchased";
	} 
	
	
	/*@RequestMapping("/books")
	@ResponseBody
	public String getAllBooks(){
		List<Books> books=new ArrayList<Books>();
		Books book=null;
		StringBuilder str = new StringBuilder();
		try{
				books=(List<Books>) booksDAO.findAll();
				Iterator iterator = books.iterator();
				while(iterator.hasNext()){
				  //String element = (String) iterator.next();
					book=(Books) iterator.next();
					str.append(book.getIsbn());
					str.append(",");
					str.append(book.getBookName());
					str.append(",");
					str.append(book.getPrice());
					str.append("\n");
				}
				
			}catch(Exception e){
				return "Problem occured while fetching all books :"+e; 
			}
		return "Books present in thedatabase  are "+str;
	}*/
		
}
