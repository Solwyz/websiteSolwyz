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
    private Cloudinary cloudinary;


    public Blog addBlog(Blog blog, MultipartFile imageFile) {
        try {
            Map<String, Object> uploadResult = cloudinary.uploader()
                    .upload(imageFile.getBytes(), ObjectUtils.emptyMap());

            blog.setImage((String) uploadResult.get("secure_url"));
            return blogRepository.save(blog);
        } catch (IOException e) {
            throw new RuntimeException("Failed to upload blog image", e);
        }
    }

    public List<Blog> getAllBlog() {
        return blogRepository.findAll();
    }

    public Blog getBlogById(Long id) {
        return blogRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Blog not found with id: " + id));
    }

    public Blog updateBlog(Long id, String title, String shortDescription, String blogShortDescription, MultipartFile imageFile) {
        Blog blog = getBlogById(id);  

        blog.setTitle(title);
        blog.setShortDescription(shortDescription);
        blog.setBlogShortDescription(blogShortDescription);

        if (imageFile != null && !imageFile.isEmpty()) {
            try {
                Map<String, Object> uploadResult = cloudinary.uploader()
                        .upload(imageFile.getBytes(), ObjectUtils.emptyMap());
                blog.setImage((String) uploadResult.get("secure_url"));
            } catch (IOException e) {
                throw new RuntimeException("Failed to upload blog image", e);
            }
        }

        return blogRepository.save(blog);
    }

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
