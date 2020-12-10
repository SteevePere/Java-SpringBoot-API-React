package com.quest.etna.model.entities;

import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.quest.etna.model.enums.UserRole;


@Entity
public class User
{
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
	@Column(nullable = false, unique = true)
	private String username;
	
	@Column(nullable = false)
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private String password;
	
	@Enumerated(EnumType.STRING)
    @Column()
	private UserRole role = UserRole.ROLE_USER;
	
	@Column(nullable = false)
	private Boolean isActive = false;
	
	@OneToMany(
		mappedBy = "reporter",
		cascade = CascadeType.REMOVE
	)
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	protected Set<Issue> reportedIssues;
	
	@OneToMany(
		mappedBy = "assignee",
		cascade = CascadeType.REMOVE
	)
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	protected Set<Issue> assignedIssues;
	
	@ManyToMany(mappedBy = "users")
    Set<Board> boards;

	@Column()
	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	private Date creationDate;

	@Column()
	@UpdateTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	private Date updatedDate;

	
	public User()
	{
	
	}
	
	
	public User(String username, String password)
	{
		this.username = username;
		this.password = password;
	}
	
	
	public User(String username, UserRole role)
	{
		this.username = username;
		this.role = role;
	}
	
	
	public User(String username, String password, UserRole role)
	{
		this.username = username;
		this.password = password;
		this.role = role;
	}
	
	
	public User(
			String username,
			String password,
			UserRole role,
			Boolean isActive,
			Set<Issue> reportedIssues,
			Set<Issue> assignedIssues,
			Set<Board> boards
	)
	{
		this.username = username;
		this.password = password;
		this.role = role;
		this.isActive = isActive;
		this.reportedIssues = reportedIssues;
		this.assignedIssues = assignedIssues;
		this.boards = boards;
	}


	public Integer getId()
	{
		return this.id;
	}
	
	
	public String getUsername()
	{
		return this.username;
	}
	
	
	@JsonIgnore
	public String getPassword()
	{
		return this.password;
	}
	
	
	public UserRole getRole()
	{
		return this.role;
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
	
	
	public void setUsername(String username)
	{
		this.username = username;
	}
	
	
	public void setPassword(String password)
	{
		this.password = password;
	}
	
	
	public void setRole(UserRole role)
	{
		this.role = role;
	}
	
	
	public Boolean getIsActive()
	{
		return isActive;
	}


	public void setIsActive(Boolean isActive)
	{
		this.isActive = isActive;
	}
	
	
	public Set<Issue> getReportedIssues()
	{
		return reportedIssues;
	}


	public void setReportedIssues(Set<Issue> reportedIssues)
	{
		this.reportedIssues = reportedIssues;
	}


	public Set<Issue> getAssignedIssues()
	{
		return assignedIssues;
	}


	public void setAssignedIssues(Set<Issue> assignedIssues)
	{
		this.assignedIssues = assignedIssues;
	}

	
	@JsonIgnore
	public Set<Board> getBoards()
	{
		return boards;
	}


	public void setBoards(Set<Board> boards)
	{
		this.boards = boards;
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
	
	
	public Boolean getIsAdmin()
	{
		Boolean isAdmin = false;
		
		if (getRole() == UserRole.ROLE_ADMIN)
		{
			isAdmin = true;
		}
		
		return isAdmin;
	}


	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
	
		result = prime * result + ((creationDate == null) ? 0 : creationDate.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		result = prime * result + ((role == null) ? 0 : role.hashCode());
		result = prime * result + ((updatedDate == null) ? 0 : updatedDate.hashCode());
		result = prime * result + ((username == null) ? 0 : username.hashCode());
		
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
		User other = (User) obj;
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
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (role != other.role)
			return false;
		if (updatedDate == null) {
			if (other.updatedDate != null)
				return false;
		} else if (!updatedDate.equals(other.updatedDate))
			return false;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}


	@Override
	public String toString()
	{
		return "User [id=" + id + ", username=" + username + ", password=" + password + ", role=" + role
				+ ", creationDate=" + creationDate + ", updatedDate=" + updatedDate + "]";
	}
}
