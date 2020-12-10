package com.quest.etna.model.entities;

import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonProperty;


@Entity
public class Milestone
{
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
	@Column(nullable = false)
	private String title;
	
	@Column(nullable = true, columnDefinition="TEXT")
	private String description;
	
	@Column()
	@Temporal(TemporalType.TIMESTAMP)
	private Date dueDate;
	
	@ManyToOne()
	@JoinColumn(name = "board_id", nullable = false)
    private Board board;
	
	@OneToMany(
		mappedBy = "milestone"
	)
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	protected Set<Issue> issues;
	
	@Column()
	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	private Date creationDate;

	@Column()
	@UpdateTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	private Date updatedDate;
	
	
	public Milestone()
	{
	
	}
	
	
	public Milestone(String title)
	{
		this.title = title;
	}
	
	
	public Milestone(String title, String description)
	{
		this.title = title;
		this.description = description;
	}
	

	public Milestone(String title, String description, Date dueDate, Set<Issue> issues)
	{
		this.title = title;
		this.description = description;
		this.dueDate = dueDate;
		this.issues = issues;
	}
	
	
	public Milestone(String title, String description, Date dueDate)
	{
		this.title = title;
		this.description = description;
		this.dueDate = dueDate;
	}
	
	
	public Milestone(String title, String description, Board board, Date dueDate)
	{
		this.title = title;
		this.description = description;
		this.board = board;
		this.dueDate = dueDate;
	}
	
	
	public Milestone(String title, String description, Date dueDate, Board board, Set<Issue> issues)
	{
		this.title = title;
		this.description = description;
		this.dueDate = dueDate;
		this.board = board;
		this.issues = issues;
	}


	public Integer getId()
	{
		return this.id;
	}
	
	
	public String getTitle()
	{
		return this.title;
	}
	
	
	public Date getCreationDate()
	{
		return this.creationDate;
	}
	
	
	public Date getUpdatedDate()
	{
		return this.updatedDate;
	}
	
	
	public void setId(Integer id)
	{
		this.id = id;
	}
	
	
	public void setTitle(String title)
	{
		this.title = title;
	}
	
	
	public String getDescription()
	{
		return description;
	}


	public void setDescription(String description)
	{
		this.description = description;
	}


	public Date getDueDate() 
	{
		return dueDate;
	}


	public void setDueDate(Date dueDate)
	{
		this.dueDate = dueDate;
	}
	
	
	public Board getBoard()
	{
		return board;
	}


	public void setBoard(Board board)
	{
		this.board = board;
	}


	public Set<Issue> getIssues()
	{
		return issues;
	}


	public void setIssues(Set<Issue> issues)
	{
		this.issues = issues;
	}
	
	
	public Integer getNumberOfIssues()
	{
		if (issues != null) 
			return issues.size();
		
		return 0;
	}


	@PrePersist
	public void prePersist()
	{
		creationDate = new Date(System.currentTimeMillis());
	}
	
	
	@PreUpdate
	public void preUpdate()
	{
		updatedDate = new Date(System.currentTimeMillis());
	}


	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((creationDate == null) ? 0 : creationDate.hashCode());
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((dueDate == null) ? 0 : dueDate.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		result = prime * result + ((updatedDate == null) ? 0 : updatedDate.hashCode());
		
		return result;
	}


	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Milestone other = (Milestone) obj;
		if (creationDate == null) {
			if (other.creationDate != null)
				return false;
		} else if (!creationDate.equals(other.creationDate))
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (dueDate == null) {
			if (other.dueDate != null)
				return false;
		} else if (!dueDate.equals(other.dueDate))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		if (updatedDate == null) {
			if (other.updatedDate != null)
				return false;
		} else if (!updatedDate.equals(other.updatedDate))
			return false;
		return true;
	}


	@Override
	public String toString()
	{
		return "Milestone [id=" + id + ", title=" + title + ", description=" + description + ", dueDate=" + dueDate
				+ ", creationDate=" + creationDate + ", updatedDate=" + updatedDate + "]";
	}
}
