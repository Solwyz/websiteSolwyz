package com.solwyz.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.solwyz.entity.Blog;
import com.solwyz.repo.BlogRepository;

@Service
public class BlogService {
	
	@Autowired
    private BlogRepository blogRepository;


    @Autowired
    private AwsS3Service awsS3Service;

    
    public Blog addBlog(Blog blog, MultipartFile imageFile) {
        try {
            String imageUrl = awsS3Service.uploadFile(imageFile);
            blog.setImage(imageUrl);
            
            return blogRepository.save(blog);
        }
        catch (IOException e) {
            throw new RuntimeException("Failed to upload blog image to S3", e);
        }
    }



    public List<Blog> getAllBlog() {
        return blogRepository.findAll();
    }

    public Blog getBlogById(Long id) {
        return blogRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Blog not found with id: " + id));
    }


   
   
//    public Blog updateBlog(Long id, String title, String shortDescription, String blogShortDescription, MultipartFile imageFile) {
//        Blog existingBlog = blogRepository.findById(id)
//                .orElseThrow(() -> new RuntimeException("Blog not found with id " + id));
//
//        existingBlog.setTitle(title);
//        existingBlog.setShortDescription(shortDescription);
//        existingBlog.setBlogShortDescription(blogShortDescription);
//       // existingBlog.setParagraph(paragraph);
//
//        if (imageFile != null && !imageFile.isEmpty()) {
//            try {
//                String imageUrl = awsS3Service.uploadFile(imageFile);
//                existingBlog.setImage(imageUrl);
//            } catch (IOException e) {
//                
//                throw new RuntimeException("Failed to upload image", e);
//            }
//        }
//
//        return blogRepository.save(existingBlog);
//    }


    public void deleteBlog(Long id) {
        if (!blogRepository.existsById(id)) {
            throw new RuntimeException("Blog not found with id: " + id);
        }
        blogRepository.deleteById(id);
    }
    public List<Blog> getSimilarBlogs(Long excludedId) {
        return blogRepository.findTop3ByIdNotOrderByCreatedAtDesc(excludedId);
    }


   
   

    }
