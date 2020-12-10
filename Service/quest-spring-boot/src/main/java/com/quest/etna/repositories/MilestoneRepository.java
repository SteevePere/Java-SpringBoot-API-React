package com.quest.etna.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.quest.etna.model.entities.Milestone;


@Repository
public interface MilestoneRepository extends CrudRepository<Milestone, Integer>
{

	@Query("SELECT m FROM Milestone m WHERE m.id = :milestoneId")
	public List<Milestone> findByMilestoneId(Integer milestoneId);
	
	
	@Query("SELECT m FROM Milestone m INNER JOIN m.board b INNER JOIN b.users u WHERE u.id = :userId")
	public List<Milestone> findByUserId(Integer userId);
	
	
	@Query("SELECT m FROM Milestone m INNER JOIN m.board b WHERE b.id = :boardId")
	public List<Milestone> findByBoardId(Integer boardId);
	
	
	@Query(
		"SELECT m FROM Milestone m"
		+ " INNER JOIN m.board b"
		+ " INNER JOIN b.users u"
		+ " WHERE b.id = :boardId"
		+ " AND u.id = :userId"
	)
	public List<Milestone> findByBoardAndUserId(Integer boardId, Integer userId);
}
