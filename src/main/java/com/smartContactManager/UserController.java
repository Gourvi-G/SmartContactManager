package com.smartContactManager;
import java.io.File;


import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpSession;

import java.nio.file.Path;
import org.springframework.data.domain.Pageable;



@Controller
@RequestMapping("/user")
public class UserController {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private ContactRepository contactRepository;
	//Method for adding common data to response
	@ModelAttribute
	public void addCommonData(Model m, Principal p) {
		String userName=p.getName();
		System.out.println(userName);
		User user=userRepository.getUserByUserName(userName);
		System.out.println("User"+user);
		m.addAttribute("user",user) ;
	}
	

	//DashBoard Home
	@RequestMapping(value = "/index", method = {RequestMethod.POST ,RequestMethod.GET})
	public String dashboard(){
//	    this.userRepository.getUserByUserName(userName);)
		return "user_dashboard";
	}
		
	//open add form handler
	@GetMapping("/add_contact")
	public String openAddContactForm(Model m) {
		m.addAttribute("title","Add Contact");
		m.addAttribute("contact",new Contact());
		return "add_contact";
	}
   @PostMapping("/process_contact")
	public String processContact(@ModelAttribute Contact contact, @RequestParam MultipartFile file,
			Principal p, HttpSession session ) {
		try {
		String name=p.getName();
		User user=this.userRepository.getUserByUserName(name);
		
		//processing and uploading file
		if(file.isEmpty()) {
			System.out.println("Please Upload image");
			contact.setC_imageUrl("contact.png");
		}
		else {
			contact.setC_imageUrl(file.getOriginalFilename());
			File saveFile=new ClassPathResource("static/image").getFile();
			Path path=Paths.get(saveFile.getAbsolutePath()+File.separator+file.getOriginalFilename());
			Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
			System.out.println("Image is uploaded");
			session.setAttribute("msg", new Message("Your contact is added!! ","success"));
		}
		
		
		user.getContacts().add(contact);
		contact.setUser(user);
		this.userRepository.save(user);
		System.out.println("Data" + contact);
		}catch(Exception e) {
			System.out.println("Error"+e.getMessage());
			e.printStackTrace();
			session.setAttribute("msg", new Message("Something went wrong!! Try Again ","danger"));
		}
		return "add_contact";
	}
   
   //showContactHandler
   @GetMapping("/show_contacts/{page}")
   public String showContacts(@PathVariable("page") Integer page ,Model m, Principal p) {
	   String username=p.getName();	
	   User user=this.userRepository.getUserByUserName(username);
//	   List<Contact> contacts=user.getContacts();
	 //current page
		//conatct per page-5
	   Pageable pageable=PageRequest.of(page, 5);
	   Page<Contact> contacts=this.contactRepository.findContactsByUser(user.getUserId(), pageable);
	   m.addAttribute("contacts",contacts);
	   m.addAttribute("currentpage",page);
	   m.addAttribute("totalpages",contacts.getTotalPages());
	  return "show_contacts";
   }
   @RequestMapping("/{C_Id}/contact")
   public String showConatctDetail(@PathVariable("C_Id") Integer cId , Model m,Principal p) {
	   m.addAttribute("title","Show Contact");
	   String username=p.getName();
	   User user=this.userRepository.getUserByUserName(username);
	   System.out.println("CId"+cId);
	   Optional<Contact> c=this.contactRepository.findById(cId);
	   Contact contact=c.get();
	  if(user.getUserId()==contact.getUser().getUserId()) {
		  m.addAttribute("contact",contact);
	  }
	   return "Contact_detail";
   }
   
 //open delete form handler
//   @GetMapping("/delete/{C_Id}")
//   @Transactional
//   public String delete_Contact(@PathVariable("C_Id") Integer cId, Model m,Principal p,HttpSession session) {
//	   String username=p.getName();
//	   User user=this.userRepository.getUserByUserName(username);
//	   Optional<Contact> c=this.contactRepository.findById(cId);
//	   Contact contact=c.get();
//	   
//	   if(user.getUserId()==contact.getUser().getUserId()) {
//		      contact.setUser(null);
//			  this.contactRepository.delete(contact);
//			
//		  }
//	   
//	   session.setAttribute("msg", new Message("Your contact deleted successfully......." , "success"));
//	   
//	   return "redirect:/user/show_contacts/0";
//   }
   
   
   
   @GetMapping("/delete/{C_Id}")
   @Transactional
   public String delete_Contact(@PathVariable("C_Id") Integer cId, Model m,Principal p,HttpSession session) {
	   String username=p.getName();
	   User user=this.userRepository.getUserByUserName(username);
	   Optional<Contact> c=this.contactRepository.findById(cId);
	   Contact contact=c.get();
	   
	   user.getContacts().remove(contact);
   	   this.userRepository.save(user);
	   
	  
	   
	   session.setAttribute("msg", new Message("Your contact deleted successfully......." , "success"));
	   
	   return "redirect:/user/show_contacts/0";
   }   
   
   
   //open update form handler
   @PostMapping("/updateContact/{C_Id}")
    public String updateForm(@PathVariable("C_Id") Integer cid,Model m,Principal p) {
    	m.addAttribute("title","Update Contact");
    	Contact contact=this.contactRepository.findById(cid).get();
    	User user=this.userRepository.getUserByUserName(p.getName());
    	m.addAttribute("contact",contact);
    	return "update_form";
    }
   
   //handler for update the form
   @RequestMapping(value="/process_update" ,method=RequestMethod.POST)
   public String process_update(@ModelAttribute Contact contact, @RequestParam MultipartFile file , Model m ,HttpSession session,Principal p) {
	   try {
		   
		   //old Contact detail
		   Contact c2=this.contactRepository.findById(contact.getC_Id()).get();
		   
		//image  
		   if(!file.isEmpty()) {
			   //rewrite
			   
			   //delete old photo
			   File deleteFile=new ClassPathResource("static/image").getFile();
			   File file1=new File(deleteFile,c2.getC_imageUrl());
			   file1.delete();
			   
			   
			   
			 //update new photo
			   File saveFile=new ClassPathResource("static/image").getFile();
			   Path path=Paths.get(saveFile.getAbsolutePath()+File.separator+file.getOriginalFilename());
			   Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
			   
			   contact.setC_imageUrl(file.getOriginalFilename());
			   
			   
			   
		   }
		   else {
			   contact.setC_imageUrl(c2.getC_imageUrl());
		   }
		   User user=this.userRepository.getUserByUserName(p.getName());
		   contact.setUser(user);
		   this.contactRepository.save(contact);
		   session.setAttribute("msg", new Message("Your contact is updated","success"));
	   }catch(Exception e) {
		   e.printStackTrace();
	   }
	   System.out.println(contact.getName());
	   return "redirect:/user/"+contact.getC_Id()+"/contact";
   }
   
   //Your profile handler
   @GetMapping("/your_profile")
   public String YourProfile(Model m) {
	   m.addAttribute("title","Profile Page");
	   return "profile";
   }
   @GetMapping("/home2")
   public String home2() {
	   return "home2";
   }
}

