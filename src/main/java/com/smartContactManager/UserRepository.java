package com.smartContactManager;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User,Integer>{
	@Query("select u from User u where u.username = :username")
      public User getUserByUserName(@Param("username") String username);
}
