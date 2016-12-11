package com.tmoon8730.api;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@JsonIgnoreProperties(ignoreUnknown = true)
class APITest{
	private String id;
	private String redditId;
	
	public String getId(){
		return this.id;
	}
	public void setId(String id){
		this.id = id;
	}
	public String getRedditId(){
		return this.redditId;
	}
	public void setRedditId(String redditId){
		this.redditId = redditId;
	}
}

public class RedditAPI {
	private static final Logger log = LoggerFactory.getLogger(RedditAPI.class);
	
	private final String APIURL = "http://127.0.0.1:8080/api/redditpost/";
	
	private RestTemplate restTemplate;
	
	public RedditAPI(){
		restTemplate = new RestTemplate();
	}
	
	public void putByRedditPost(RedditPost post){
		System.out.println("  [x] Trying " + APIURL + post.getId());
		
		String uri = APIURL + "{id}";
		
		Map<String, String> params = new HashMap<String,String>();
		params.put("id", post.getId());
		
		restTemplate.put(uri, post, params);
	}
	
	public RedditPost getById(String id){
		System.out.println("  [x] Trying " + APIURL + id);
		return restTemplate.getForObject(APIURL + id, RedditPost.class);
	}
	public RedditPost[] getAllPosts(){
		System.out.println("  [x] Trying " + APIURL);
		ResponseEntity<RedditPost[]> response = restTemplate.getForEntity(APIURL, RedditPost[].class);
		return response.getBody();
	}
}
