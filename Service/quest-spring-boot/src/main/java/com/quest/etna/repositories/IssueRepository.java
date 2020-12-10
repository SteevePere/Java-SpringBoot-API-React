package com.quest.etna.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.quest.etna.model.entities.Issue;


@Repository
public interface IssueRepository extends CrudRepository<Issue, Integer>
{

	@Query("SELECT i FROM Issue i WHERE i.id = :issueId")
	public List<Issue> findByIssueId(Integer issueId);
	
	
	@Query("SELECT i FROM Issue i INNER JOIN i.board b INNER JOIN b.users u WHERE u.id = :userId")
	public List<Issue> findByUserId(Integer userId);
	
	
	@Query("SELECT i FROM Issue i INNER JOIN i.board b WHERE b.id = :boardId")
	public List<Issue> findByBoardId(Integer boardId);
	
	
	@Query(
		"SELECT i FROM Issue i"
		+ " INNER JOIN i.board b"
		+ " INNER JOIN b.users u"
		+ " WHERE b.id = :boardId"
		+ " AND u.id = :userId"
	)
	public List<Issue> findByBoardAndUserId(Integer boardId, Integer userId);
}
