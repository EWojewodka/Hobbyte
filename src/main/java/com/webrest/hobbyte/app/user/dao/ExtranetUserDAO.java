package com.webrest.hobbyte.app.user.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.webrest.hobbyte.app.user.model.ExtranetUser;

@Repository
public interface ExtranetUserDAO extends JpaRepository<ExtranetUser, Integer>{

	ExtranetUser findByLogin(String login);

	ExtranetUser findByEmail(String email);
	
	@Query("SELECT user FROM ExtranetUser user WHERE login=?1 OR email=?1")
	ExtranetUser findByLoginOrEmail(String loginOrEmail);
	
}
