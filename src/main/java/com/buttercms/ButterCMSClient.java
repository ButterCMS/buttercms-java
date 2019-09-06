package com.buttercms;

import com.buttercms.model.AuthorResponse;
import com.buttercms.model.AuthorsResponse;
import com.buttercms.model.CategoriesResponse;
import com.buttercms.model.CategoryResponse;
import com.buttercms.model.Page;
import com.buttercms.model.PagesResponse;
import com.buttercms.model.PostResponse;
import com.buttercms.model.PostsResponse;
import com.buttercms.model.TagResponse;
import com.buttercms.model.TagsResponse;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.util.Map;

public class ButterCMSClient {
    private static final String API_BASE = "https://api.buttercms.com/v2";
    private String authToken;
    private final ObjectMapper mapper;

    // TODO: JavaDoc
    public ButterCMSClient(final String authToken, final CloseableHttpClient httpClient) {
        this.authToken = authToken;
        this.httpClient = httpClient;
        this.mapper = new ObjectMapper();
        this.mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        this.mapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    private String buildURL(final String path, final Map<String, String> queryParameters) {
        // TODO: filter out empty-string keys and values?
        // TODO: do i need to urlencode these strings, or does the library do it?
        final URIBuilder uriBuilder = new URIBuilder(URI.create(API_BASE + path));
        uriBuilder.addParameter("auth_token", this.authToken);
        if (queryParameters != null) {
            queryParameters.forEach(uriBuilder::addParameter);
        }
        return uriBuilder.toString();
    }

    private <T> T readValue(final String url, Class<T> valueType) throws IOException {
        return this.mapper.readValue(new URL(url), valueType);
    }

    // AUTHOR

    // TODO: JavaDoc
    public AuthorResponse getAuthor(final String slug, final Map<String, String> queryParameters) throws IOException {
        final String url = this.buildURL("/authors/" + slug, queryParameters);
        return this.readValue(url, AuthorResponse.class);
    }

    // TODO: JavaDoc
    public AuthorsResponse getAuthors(final Map<String, String> queryParameters) throws IOException {
        final String url = this.buildURL("/authors/", queryParameters);
        return this.readValue(url, AuthorsResponse.class);
    }

    // CATEGORY

    // TODO: JavaDoc
    public CategoryResponse getCategory(final String slug, final Map<String, String> queryParameters) throws IOException {
        final String url = this.buildURL("/categories/" + slug, queryParameters);
        return this.readValue(url, CategoryResponse.class);
    }

    // TODO: JavaDoc
    public CategoriesResponse getCategories(final Map<String, String> queryParameters) throws IOException {
        final String url = this.buildURL("/categories/", queryParameters);
        return this.readValue(url, CategoriesResponse.class);
    }

    // PAGE

    /*
    // TODO: JavaDoc
    public PageResponse getPage(final String pageTypeSlug, final String pageSlug, final Map<String, String> queryParameters) throws IOException {
        final String path = String.format("/pages/%s/%s/", pageTypeSlug, pageSlug);
        final String url = this.buildURL(path, queryParameters);
        return this.readValue(url, PageResponse.class);
    }
    */
    public <T> Page<T> getPage(final String pageTypeSlug, final String pageSlug, final Map<String, String> queryParameters) throws IOException {
        final String path = String.format("/pages/%s/%s/", pageTypeSlug, pageSlug);
        final String url = this.buildURL(path, queryParameters);
        TypeReference ref = new TypeReference<Page<T>>(){};
//        JavaType type = this.mapper.getTypeFactory().constructType(Page<pageType>);
//        return this.mapper.readValue(new URL(url), type);
        try (final CloseableHttpClient httpClient = HttpClients.createDefault()) {
            try (CloseableHttpResponse response = httpClient.execute(new HttpGet(url))) {
                InputStream json = response.getEntity().getContent();
                JsonNode root = this.mapper.readTree(json);
                JsonNode data = root.get("data");
//        return this.mapper.readValue(new URL(url), pageType);
                return this.mapper.convertValue(data, ref);
            }
        }
    }

    // TODO: JavaDoc
    public PagesResponse getPages(final String pageTypeSlug, final Map<String, String> queryParameters) throws IOException {
        final String url = this.buildURL("/pages/" + pageTypeSlug, queryParameters);
        return this.readValue(url, PagesResponse.class);
    }

    // POST

    // TODO: JavaDoc
    public PostResponse getPost(final String slug) throws IOException {
        final String url = this.buildURL("/posts/" + slug, null);
        return this.readValue(url, PostResponse.class);
    }

    // TODO: JavaDoc
    public PostsResponse getPosts(final Map<String, String> queryParameters) throws IOException {
        final String url = this.buildURL("/posts/", queryParameters);
        return this.readValue(url, PostsResponse.class);
    }

    // TODO: JavaDoc
    public PostsResponse searchPosts(String query, final Map<String, String> queryParameters) {
        if (query == null) {
            query = "";
        }
        throw new UnsupportedOperationException("Not implemented yet");
    }

    // TAG

    // TODO: JavaDoc
    public TagResponse getTag(final String slug, final Map<String, String> queryParameters) throws IOException {
        final String url = this.buildURL("/tags/" + slug, queryParameters);
        return this.readValue(url, TagResponse.class);
    }

    // TODO: JavaDoc
    public TagsResponse getTags(final Map<String, String> queryParameters) throws IOException {
        final String url = this.buildURL("/tags/", queryParameters);
        return this.readValue(url, TagsResponse.class);
    }

    // FEEDS AND SITEMAP (all XML)

    public void getAtom() {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    public void getRSS() {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    public void getSitemap() {
        throw new UnsupportedOperationException("Not implemented yet");
    }
}
