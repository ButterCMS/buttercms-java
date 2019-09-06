package com.example;

import com.buttercms.ButterCMSClient;
import com.buttercms.model.Page;
import com.buttercms.model.Post;
import com.example.model.RecipePage;
import com.fasterxml.jackson.core.type.TypeReference;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Demo {
    private ButterCMSClient client;
    private Map<String, String> params;

    private Demo(ButterCMSClient client) {
        this.client = client;
        this.params = new HashMap<>();
    }

    private void h1(final String message) {
        System.out.println("\n# ==== " + message + " ==== #");
    }

    private void h2(final String message) {
        System.out.println("# -- " + message + " -- #");
    }

    private void printAuthor() throws IOException {
        this.params.clear();
        this.params.put("include", "recent_posts");

        this.h1("AUTHOR");
        this.h2("getAuthor()");
        System.out.println(client.getAuthor("chad-glendenin", null).getData());
        this.h2("getAuthors()");
        System.out.println(client.getAuthors(this.params).getData());
    }

    private void printCategory() throws IOException {
        this.params.clear();
        this.params.put("include", "recent_posts");
        this.h1("CATEGORY");
        this.h2("getCategory()");
        System.out.println(client.getCategory("example-category", null).getData());
        this.h2("getCategories()");
        System.out.println(client.getCategories(this.params).getData());
    }

    private void printPage() throws IOException {
        this.h1("PAGE");

        this.params.clear();
        this.params.put("locale", "en");

        this.h2("getPage()");
        Page<RecipePage> recipe = client.getPage("recipe", "test-page-11", this.params);
        System.out.println(recipe);
        RecipePage recipeFields = recipe.getFields();
        System.out.println("test content: " + recipeFields.getTestContent());

        this.h2("getPages()");
        System.out.println(client.getPages("recipe", this.params).getData());
    }

    private void printPost() throws IOException {
        this.h1("POST");

        // If you set the auth_token to "garbage", then this shows the
        // auth_token in the stack trace:
        System.out.println(client.getPost("second-post").getData());

        this.params.clear();
        this.params.put("category_slug", "example-category");
        this.params.put("exclude_body", "true");

        this.h2("posts");
//        params.put("page", "2"); // this will crash until i merge the `custom-http-exceptions` branch
//        params.put("page_size", "5");
        List<Post> posts = client.getPosts(this.params).getData();
        System.out.println("number of posts: " + posts.size());
        System.out.println(posts);
    }

    private void printTag() throws IOException {
        this.h1("TAG");

        this.params.clear();
        this.params.put("include", "recent_posts");

        this.h2("getTag()");
        System.out.println(client.getTag("example-tag", this.params).getData());
        this.h2("getTags()");
        System.out.println(client.getTags(null).getData());
    }

    public static void main(String[] args) {
        ButterCMSClient client = new ButterCMSClient("468cc2e45f1bd9df5f40064a0b017035f8a5bec7");
        Demo demo = new Demo(client);
        try {
//            demo.printAuthor();
//            demo.printCategory();
            demo.printPage();
//            demo.printPost();
//            demo.printTag();
        } catch (FileNotFoundException e) {
            System.out.println("!!! 404 !!!");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("!!! non-404 http error !!!");
            e.printStackTrace();
        }
    }
}
