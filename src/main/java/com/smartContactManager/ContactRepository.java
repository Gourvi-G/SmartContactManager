package com.smartContactManager;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ContactRepository extends JpaRepository<Contact,Integer>{
      //pagination...
	
	//current page
	//conatct per page-5
//	@Query("from Contact as c where c.user.UserId =:userId")
//	public Page<Contact> findContactsByUser(@Param("userId")int userId , Pageable pageable);

	  @Query("from Contact as c where c.user.UserId = :userId")
	    public Page<Contact> findContactsByUser(@Param("userId") int userId, Pageable pageable); // Add Pageable parameter here
	    //search
        public List<Contact> findByNameContainingAndUser(String name, User user);
		//public List<Contact> findByNameContainingAndUser(String q, User user);
}
