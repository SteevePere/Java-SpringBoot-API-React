package com.quest.etna.model.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.UpdateTimestamp;

import com.quest.etna.model.enums.IssuePriority;
import com.quest.etna.model.enums.IssueStatus;
import com.quest.etna.model.enums.IssueType;


@Entity
@DynamicUpdate
public class Issue
{
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
	@ManyToOne()
	@JoinColumn(name = "board_id", nullable = false)
    private Board board;
	
	@ManyToOne()
	@JoinColumn(name = "milestone_id", nullable = true)
    private Milestone milestone;
	
	@ManyToOne()
	@JoinColumn(name = "reporter_id", nullable = false)
    private User reporter;
	
	@ManyToOne()
	@JoinColumn(name = "assignee_id", nullable = true)
    private User assignee;
	
	@Column(nullable = false)
	private String title;
	
	@Column(nullable = true, columnDefinition="TEXT")
	private String description;
	
	@Enumerated(EnumType.STRING)
    @Column()
	private IssueType type = IssueType.STORY;
	
	@Enumerated(EnumType.STRING)
    @Column()
	private IssueStatus status = IssueStatus.OPEN;
	
	@Enumerated(EnumType.STRING)
    @Column()
	private IssuePriority priority = IssuePriority.MEDIUM;
	
	@Column()
	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	private Date creationDate;

	@Column()
	@UpdateTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	private Date updatedDate;
	
	
	public Issue()
	{
	
	}


	public Issue(
		String title,
		String description,
		IssueType type,
		IssueStatus status,
		IssuePriority priority,
		Board board,
		Milestone milestone,
		User reporter,
		User assignee
	)
	{
		this.title = title;
		this.description = description;
		this.type = type;
		this.status = status;
		this.priority = priority;
		this.board = board;
		this.milestone = milestone;
		this.reporter = reporter;
		this.assignee = assignee;
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
	

	public Board getBoard()
	{
		return board;
	}


	public void setBoard(Board board)
	{
		this.board = board;
	}


	public Milestone getMilestone()
	{
		return milestone;
	}


	public void setMilestone(Milestone milestone)
	{
		this.milestone = milestone;
	}


	public User getReporter()
	{
		return reporter;
	}


	public void setReporter(User reporter)
	{
		this.reporter = reporter;
	}


	public User getAssignee()
	{
		return assignee;
	}


	public void setAssignee(User assignee)
	{
		this.assignee = assignee;
	}


	public IssueType getType()
	{
		return type;
	}


	public void setType(IssueType type)
	{
		this.type = type;
	}


	public IssueStatus getStatus()
	{
		return status;
	}


	public void setStatus(IssueStatus status)
	{
		this.status = status;
	}


	public IssuePriority getPriority()
	{
		return priority;
	}


	public void setPriority(IssuePriority priority)
	{
		this.priority = priority;
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
		result = prime * result + ((assignee == null) ? 0 : assignee.hashCode());
		result = prime * result + ((board == null) ? 0 : board.hashCode());
		result = prime * result + ((milestone == null) ? 0 : milestone.hashCode());
		result = prime * result + ((reporter == null) ? 0 : reporter.hashCode());
		result = prime * result + ((creationDate == null) ? 0 : creationDate.hashCode());
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((priority == null) ? 0 : priority.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
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
		Issue other = (Issue) obj;
		if (assignee == null) {
			if (other.assignee != null)
				return false;
		} else if (!assignee.equals(other.assignee))
			return false;
		if (board == null) {
			if (other.board != null)
				return false;
		} else if (!board.equals(other.board))
			return false;
		if (milestone == null) {
			if (other.milestone != null)
				return false;
		} else if (!milestone.equals(other.milestone))
			return false;
		if (reporter == null) {
			if (other.reporter != null)
				return false;
		} else if (!reporter.equals(other.reporter))
			return false;
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
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (priority != other.priority)
			return false;
		if (status != other.status)
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		if (type != other.type)
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
		return "Issue [id=" + id + ", Board=" + board + ", Milestone=" + milestone + ", Reporter=" + reporter
				+ ", Assignee=" + assignee + ", title=" + title + ", description=" + description + ", type=" + type
				+ ", status=" + status + ", priority=" + priority + ", creationDate=" + creationDate + ", updatedDate="
				+ updatedDate + "]";
	}
}
