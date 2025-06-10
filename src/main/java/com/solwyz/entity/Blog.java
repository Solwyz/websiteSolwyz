package com.solwyz.entity;

import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;

@Entity
@AllArgsConstructor
public class Blog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    @Column(length = 5000)
    private String shortDescription;
//    @Column(length = 5000)
//    private String blogShortDescription;
    
//    @Column(length = 5000)
//    private String paragraph;
    
    @ElementCollection
	@Column(name = "paragh")
	private List<String> paragraphss;
	
    
    
    private String image;

    public Blog() {}

    @CreationTimestamp
    private LocalDateTime createdAt;


    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

//    public String getBlogShortDescription() {
//        return blogShortDescription;
//    }
//
//    public void setBlogShortDescription(String blogShortDescription) {
//        this.blogShortDescription = blogShortDescription;
//    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public List<String> getParagraphss() {
		return paragraphss;
	}

	public void setParagraphss(List<String> paragraphss) {
		this.paragraphss = paragraphss;
	}

//	public String getParagraph() {
//		return paragraph;
//	}
//
//	public void setParagraph(String paragraph) {
//		this.paragraph = paragraph;
//	}
	
}
