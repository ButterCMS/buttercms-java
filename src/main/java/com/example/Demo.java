package com.example;

import com.buttercms.ButterCMSClient;
import com.buttercms.IButterCMSClient;
import com.buttercms.model.CollectionResponse;
import com.buttercms.model.PageResponse;
import com.buttercms.model.Post;
import com.example.model.Car;
import com.example.model.RecipePage;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Demo {
    private IButterCMSClient client;
    private Map<String, String> params;

    private Demo(IButterCMSClient client) {
        this.client = client;
        this.params = new HashMap<>();
    }

    public static void main(String[] args) {
        ButterCMSClient client = new ButterCMSClient("your_api_token", false);
        Demo demo = new Demo(client);
        try {
            demo.printCollection();
            demo.printSiteMap();
            demo.printAuthor();
            demo.printCategory();
            demo.printPage();
            demo.printPost();
            demo.printTag();
        } catch (FileNotFoundException e) {
            System.out.println("!!! 404 !!!");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("!!! non-404 http error !!!");
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void h1(final String message) {
        System.out.println("\n# ==== " + message + " ==== #");
    }

    private void h2(final String message) {
        System.out.println("# -- " + message + " -- #");
    }

    private void printCollection() throws Exception {
        CollectionResponse response = client.getCollection("cars", new HashMap<String, String>() {{
            put("fields.weight", "400");
            put("page_size", "1");
        }}, Car.class);
        System.out.println(response.toString());
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
        PageResponse<RecipePage> recipe = client.getPage("recipe", "test-page-11", this.params, RecipePage.class);
        System.out.println(recipe);
        RecipePage recipeFields = recipe.getData().getFields();
        System.out.println("test content: " + recipeFields.getTestContent());

        this.h2("getPages()");
        System.out.println(client.getPages("recipe", this.params, RecipePage.class).getData());
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

    private void printSiteMap() throws Exception {
        DOMSource domSource = new DOMSource(client.getSiteMap());
        StringWriter writer = new StringWriter();
        StreamResult result = new StreamResult(writer);
        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer transformer = tf.newTransformer();
        transformer.transform(domSource, result);
        System.out.println(writer.toString());
    }
}
