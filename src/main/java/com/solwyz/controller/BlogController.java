package com.solwyz.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.solwyz.entity.Blog;
import com.solwyz.pojo.response.ApiResponse;
import com.solwyz.service.BlogService;

import io.swagger.v3.oas.annotations.tags.Tag;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/blog")
@Tag(name = "blog Authentication", description = "APIs for All blog page")
public class BlogController {
	
	  @Autowired
	  private BlogService blogService;


	  
	  @PostMapping(value = "/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	  public ResponseEntity<Blog> addBlog(@RequestParam("title") String title,
	                                      @RequestParam("shortDescription") String shortDescription,
	                                     
	                                      @RequestParam("paragraphss") List<String> paragraphss,
	                                      @RequestParam("image") MultipartFile imageFile) {

	      Blog blog = new Blog();
	      blog.setTitle(title);
	      blog.setShortDescription(shortDescription);
	     
	      blog.setParagraphss(paragraphss);  

	      Blog savedBlog = blogService.addBlog(blog, imageFile);
	      return ResponseEntity.ok(savedBlog);
	  }


	    @GetMapping("/all")
	    public ResponseEntity<ApiResponse<List<Blog>>> getAllBlog() {
	        List<Blog> blogList = blogService.getAllBlog();
	        return ResponseEntity.ok(new ApiResponse<>("success", blogList));
	    }

	    @GetMapping("/{id}")
	    public ResponseEntity<Blog> getBlogById(@PathVariable Long id) {
	        return ResponseEntity.ok(blogService.getBlogById(id));
	    }
	    
//	    @PutMapping(value = "/update/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
//	    public ResponseEntity<Blog> updateBlog(
//	            @PathVariable Long id,
//	            @RequestParam("title") String title,
//	            @RequestParam("shortDescription") String shortDescription,
//	           
//	            
//	            @RequestParam(value = "image", required = false) MultipartFile imageFile) {
//
//	        Blog updatedBlog = blogService.updateBlog(id, title, shortDescription,imageFile);
//	        return ResponseEntity.ok(updatedBlog);
//	    }

	    
	    @GetMapping("/similar/{id}")
	    public ResponseEntity<Map<String, Object>> getBlogWithSimilar(@PathVariable Long id) {
	        Blog blog = blogService.getBlogById(id);
	        List<Blog> similarBlogs = blogService.getSimilarBlogs(id);

	        Map<String, Object> response = new HashMap<>();
	        response.put("blog", blog);
	        response.put("similarBlogs", similarBlogs);

	        return ResponseEntity.ok(response);
	    }



	    @DeleteMapping("/delete/{id}")
	    public ResponseEntity<String> deleteBlog(@PathVariable Long id) {
	        blogService.deleteBlog(id);
	        return ResponseEntity.ok("Blog deleted successfully");
	    }
	}
