package com.quest.etna.repositories;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.quest.etna.model.entities.User;


@Repository
public interface UserRepository extends CrudRepository<User, Integer>
{

	@Query("SELECT u FROM User u WHERE u.id = :userId")
	public List<User> findByUserId(Integer userId);
	
	
	@Query("SELECT u FROM User u WHERE u.username = :username")
	public User findByUsername(String username);
	
	
	public Set<User> findByIdIn(Set<Integer> ids);
}
