package com.quest.etna.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.quest.etna.model.entities.Board;


@Repository
public interface BoardRepository extends CrudRepository<Board, Integer>
{

	@Query("SELECT b FROM Board b WHERE b.id = :boardId")
	public List<Board> findByBoardId(Integer boardId);
	
	
	@Query("SELECT b FROM Board b INNER JOIN b.users u WHERE u.id = :userId")
	public List<Board> findByUserId(Integer userId);
}