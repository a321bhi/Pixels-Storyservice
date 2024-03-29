package com.pixels.storyservice.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public class Payload implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String mediaId;
	private Date mediaDate;
	private List<String> mediaTags = new ArrayList<>();
	private String mediaCaption;
	private MultipartFile image = null;
	private String imageAsBase64 = "";

	public Payload(String mediaId, Date mediaDate, List<String> mediaTags, String mediaCaption, String imageAsBase64) {
		super();
		this.mediaId = mediaId;
		this.mediaDate = mediaDate;
		this.mediaTags = mediaTags;
		this.mediaCaption = mediaCaption;
		this.imageAsBase64 = imageAsBase64;
	}

	public Payload(String mediaId, Date mediaDate, List<String> mediaTags, String mediaCaption, MultipartFile image) {
		super();
		this.mediaId = mediaId;
		this.mediaDate = mediaDate;
		this.mediaTags = mediaTags;
		this.mediaCaption = mediaCaption;
		this.image = image;
	}

	public Payload() {
		super();
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
	public String toString() {
		return "Payload [mediaId=" + mediaId + ", mediaDate=" + mediaDate + ", mediaTags=" + mediaTags
				+ ", mediaCaption=" + mediaCaption + ", image=" + image + "]";
	}

	public MultipartFile getImage() {
		return image;
	}

	public void setImage(MultipartFile image) {
		this.image = image;
	}

	public String getImageAsBase64() {
		return imageAsBase64;
	}

	public void setImageAsBase64(String imageAsBase64) {
		this.imageAsBase64 = imageAsBase64;
	}

}
