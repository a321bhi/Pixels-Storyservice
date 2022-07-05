package com.pixels.feedservice.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.GenericGenerator;

@Entity
public class MediaComment {
	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	private String commentId;

	private String commentContent;

	private Date createdAt = new Date();
	@ManyToOne
	@JoinColumn(name = "userId")
	private PixelSenseUser commentByUser;

	@ManyToOne
	@JoinColumn(name = "mediaId")
	private MediaOracle commentOnMediaId;

	@ManyToMany(fetch=FetchType.EAGER)
	@JoinTable(name = "commentLikedBy", joinColumns = @JoinColumn(name = "commendId"), inverseJoinColumns = @JoinColumn(name = "userId"))
	private Set<PixelSenseUser> commentLikedBy = new HashSet<>();

	public MediaComment() {
		super();
	}

	public MediaComment(String commentContent, PixelSenseUser commentByUser, MediaOracle commentOnMediaId) {
		super();
		this.commentContent = commentContent;
		this.commentByUser = commentByUser;
		this.commentOnMediaId = commentOnMediaId;
	}

	@Override
	public int hashCode() {
		return Objects.hash(commentId, createdAt);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MediaComment other = (MediaComment) obj;
		return Objects.equals(commentId, other.commentId) && Objects.equals(createdAt, other.createdAt);
	}

	@Override
	public String toString() {
		return "MediaComment [commentId=" + commentId + ", commentContent=" + commentContent + ", createdAt="
				+ createdAt + ", commentByUser=" + commentByUser + ", commentOnMediaId=" + commentOnMediaId + "]";
	}

	public String getCommentId() {
		return commentId;
	}

	public void setCommentId(String commentId) {
		this.commentId = commentId;
	}

	public String getCommentContent() {
		return commentContent;
	}

	public void setCommentContent(String commentContent) {
		this.commentContent = commentContent;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public PixelSenseUser getCommentByUser() {
		return this.commentByUser;
	}
//	
//	public PixelSenseUser getCommentByUserOnlyUsernames() {
//		return new PixelSenseUser(commentByUser.getUserName());
//	}

	public void setCommentByUser(PixelSenseUser commentByUser) {
		this.commentByUser = commentByUser;
	}

	public MediaOracle getCommentOnMediaId() {
		return commentOnMediaId;
	}

	public void setCommentOnMediaId(MediaOracle commentOnMediaId) {
		this.commentOnMediaId = commentOnMediaId;
	}

	public Set<PixelSenseUser> getCommentLikedBy() {
		return commentLikedBy;
	}

	public void setCommentLikedBy(Set<PixelSenseUser> commentLikedBy) {
		this.commentLikedBy = commentLikedBy;
	}

}
