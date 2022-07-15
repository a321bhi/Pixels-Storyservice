package com.pixels.feedservice.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "media")
public class MediaMongo {

	@Id
	private String mediaId;
	private Date mediaDate;
	private String mediaEncodedData;
	private List<String> mediaTags = new ArrayList<>();
	private String mediaCaption;

	public MediaMongo() {
		super();
	}

	public MediaMongo(String mediaId, Date mediaDate, List<String> mediaTags, String mediaCaption) {
		this.mediaId = mediaId;
		this.mediaDate = mediaDate;
		this.mediaTags = mediaTags;
		this.mediaCaption = mediaCaption;
	}

	public MediaMongo(String mediaId, Date mediaDate, String mediaEncodedData, List<String> mediaTags,
			String mediaCaption) {
		super();
		this.mediaId = mediaId;
		this.mediaDate = mediaDate;
		this.mediaEncodedData = mediaEncodedData;
		this.mediaTags = mediaTags;
		this.mediaCaption = mediaCaption;
	}

	@Override
	public String toString() {
		return "Media [mediaId=" + mediaId + ", mediaDate=" + mediaDate + " mediaTags=" + mediaTags + ", mediaCaption="
				+ mediaCaption + "]";
	}

	public List<String> getMediaTags() {
		return mediaTags;
	}

	public void setMediaTags(List<String> mediaTags) {
		this.mediaTags = mediaTags;
	}

	public String getMediaCaption() {
		return mediaCaption;
	}

	public void setMediaCaption(String mediaCaption) {
		this.mediaCaption = mediaCaption;
	}

	@Override
	public int hashCode() {
		return Objects.hash(mediaId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MediaMongo other = (MediaMongo) obj;
		return Objects.equals(mediaId, other.mediaId);
	}

	public String getMediaId() {
		return mediaId;
	}

	public void setMediaId(String mediaId) {
		this.mediaId = mediaId;
	}

	public Date getMediaDate() {
		return mediaDate;
	}

	public void setMediaDate(Date mediaDate) {
		this.mediaDate = mediaDate;
	}

	public String getMediaEncodedData() {
		return mediaEncodedData;
	}

	public void setMediaEncodedData(String mediaEncodedData) {
		this.mediaEncodedData = mediaEncodedData;
	}

}
