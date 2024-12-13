package com.demo;

import com.buttercms.ButterCMSClient;
import com.buttercms.model.Page;
import com.buttercms.model.PageResponse;
import com.buttercms.model.PostResponse;
import com.buttercms.model.Post;
import com.demo.PageFields;

import java.util.Map;
import java.util.HashMap;


public class Demo {
    public static void main(String[] args) {
        String apiKey = System.getenv("API_KEY");
        if (apiKey == null || apiKey.isEmpty()) {
            System.out.println("API_KEY environment variable is not set");
            return;
        }

        ButterCMSClient client = new ButterCMSClient(apiKey, true);

        try {
            PageResponse<PageFields> pageResponse = client.getPage("*", "test-page-1", null, PageFields.class);
            Page<PageFields> page = pageResponse.getData();

            System.out.println("Page Slug: " + page.getSlug());
            System.out.println("Page Status: " + page.getStatus());
            System.out.println("Page Scheduled date: " + page.getScheduled());
        } catch (Exception e) {
            System.out.println("Error fetching page: " + e.getMessage());
        }

        try {
            PostResponse postResponse = client.getPost("test-blog-post");
            Post post = postResponse.getData();

            System.out.println("Post Title: " + post.getTitle());
            System.out.println("Post Status: " + post.getStatus());
            System.out.println("Post Published date: " + post.getPublished());
            System.out.println("Post Scheduled date: " + post.getScheduled());
        } catch (Exception e) {
            System.out.println("Error fetching post: " + e.getMessage());
        }
    }
}
