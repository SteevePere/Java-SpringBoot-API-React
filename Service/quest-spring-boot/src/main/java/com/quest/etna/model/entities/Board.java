package com.quest.etna.model.entities;

import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonProperty;


@Entity
public class Board
{
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
	@Column(nullable = false)
	private String title;
	
	@OneToMany(
		mappedBy = "board",
		cascade = CascadeType.REMOVE
	)
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	protected Set<Milestone> milestones;
	
	@OneToMany(
		mappedBy = "board",
		cascade = CascadeType.REMOVE
	)
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	protected Set<Issue> issues;
	
	@ManyToMany()
    @JoinTable(
        name = "board_users", 
        joinColumns = { @JoinColumn(name = "board_id") }, 
        inverseJoinColumns = { @JoinColumn(name = "user_id") }
    )
    private Set<User> users;
	
	@Column()
	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	private Date creationDate;

	@Column()
	@UpdateTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	private Date updatedDate;
	
	
	public Board()
	{
	
	}
	
	
	public Board(String title)
	{
		this.title = title;
	}
	
	
	public Board(String title, Set<Issue> issues, Set<User> users)
	{
		this.title = title;
		this.issues = issues;
		this.users = users;
	}
	
	
	public Board(String title, Set<Milestone> milestones, Set<Issue> issues, Set<User> users) {
		super();
		this.title = title;
		this.milestones = milestones;
		this.issues = issues;
		this.users = users;
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
	
	
	public Set<Milestone> getMilestones()
	{
		return milestones;
	}


	public void setMilestones(Set<Milestone> milestones)
	{
		this.milestones = milestones;
	}


	public Set<Issue> getIssues()
	{
		return issues;
	}


	public void setIssues(Set<Issue> issues)
	{
		this.issues = issues;
	}


	public Set<User> getUsers()
	{
		return users;
	}


	public void setUsers(Set<User> boardUsers)
	{
		this.users = boardUsers;
	}
	
	
	public Integer getNumberOfUsers()
	{
		if (users != null) 
			return users.size();
		
		return 0;
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
		Board other = (Board) obj;
		if (creationDate == null) {
			if (other.creationDate != null)
				return false;
		} else if (!creationDate.equals(other.creationDate))
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
		return "Board [id=" + id + ", title=" + title + ", creationDate=" + creationDate + ", updatedDate="
				+ updatedDate + "]";
	}
}
