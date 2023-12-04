package com.smartContactManager;

import java.security.Principal;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SearchController {
	@Autowired
    private UserRepository userRepository;
	@Autowired
    private ContactRepository contactRepository;
	
	//serach handler
	@GetMapping("/search/{query}")
	public ResponseEntity<?> SearchController(@PathVariable("query") String q,Principal p) {
		User user=this.userRepository.getUserByUserName(p.getName());
		List<Contact> contact=this.contactRepository.findByNameContainingAndUser(q, user);
		return ResponseEntity.ok(contact);
	}
}
