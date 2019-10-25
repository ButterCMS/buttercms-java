package com.buttercms;

import com.buttercms.config.ButterCMSAPIConfig;
import com.buttercms.model.*;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.HttpClients;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.util.Map;

public class ButterCMSClient implements IButterCMSClient {
    private final ObjectMapper mapper;
    private final HttpClient client;
    private String authToken;

    public ButterCMSClient(final String authToken) {
        this.authToken = authToken;
        this.mapper = new ObjectMapper();
        this.mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        this.mapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
        this.client = HttpClients.createDefault();
    }

    public ButterCMSClient(final String authToken, HttpClient client) {
        this.authToken = authToken;
        this.mapper = new ObjectMapper();
        this.mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        this.mapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
        this.client = client;
    }

    @Override
    public String getAuthToken() {
        return authToken;
    }

    @Override
    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }


    private <T> T readValue(final String url, Class<T> valueType) throws IOException {
        return this.mapper.readValue(new URL(url), valueType);
    }

    // AUTHOR


    @Override
    public AuthorResponse getAuthor(final String slug, final Map<String, String> queryParameters) throws IOException {
        return readGetRequest(ButterCMSAPIConfig.AUTHORS + slug, queryParameters, AuthorResponse.class);
    }


    @Override
    public AuthorsResponse getAuthors(final Map<String, String> queryParameters) throws IOException {
        return readGetRequest(ButterCMSAPIConfig.AUTHORS, queryParameters, AuthorsResponse.class);
    }

    // CATEGORY


    @Override
    public CategoryResponse getCategory(final String slug, final Map<String, String> queryParameters) throws IOException {
        return readGetRequest(ButterCMSAPIConfig.CATEGORIES + slug, queryParameters, CategoryResponse.class);
    }


    @Override
    public CategoriesResponse getCategories(final Map<String, String> queryParameters) throws IOException {
        return readGetRequest(ButterCMSAPIConfig.CATEGORIES, queryParameters, CategoriesResponse.class);
    }

    @Override
    public <T> Page<T> getPage(final String pageTypeSlug, final String pageSlug, final Map<String, String> queryParameters, Class<T> classType) throws IOException {
        final String path = String.format(ButterCMSAPIConfig.PAGES + "%s/%s/", pageTypeSlug, pageSlug);
        HttpGet httpGet = new HttpGet(buildURL(path, queryParameters));
        try {
            InputStream json = client.execute(httpGet).getEntity().getContent();
            JsonNode root = this.mapper.readTree(json);
            return mapper.convertValue(root.get("data"), mapper.getTypeFactory().constructParametricType(Page.class, classType));
        } finally {
            httpGet.releaseConnection();
        }
    }


    @Override
    public <T> PagesResponse<T> getPages(final String pageTypeSlug, final Map<String, String> queryParameters, Class<T> classType) throws IOException {
        HttpGet httpGet = new HttpGet(buildURL(ButterCMSAPIConfig.PAGES + pageTypeSlug, queryParameters));
        try {
            return mapper.readValue(client.execute(httpGet).getEntity().getContent(),
                    mapper.getTypeFactory().constructParametricType(PagesResponse.class, classType));
        } finally {
            httpGet.releaseConnection();
        }
    }

    // POST


    @Override
    public PostResponse getPost(final String slug) throws IOException {
        return readGetRequest(ButterCMSAPIConfig.POSTS + slug, null, PostResponse.class);
    }


    @Override
    public PostsResponse getPosts(final Map<String, String> queryParameters) throws IOException {
        return readGetRequest(ButterCMSAPIConfig.POSTS, queryParameters, PostsResponse.class);
    }


    // TAG


    @Override
    public TagResponse getTag(final String slug, final Map<String, String> queryParameters) throws IOException {
        return readGetRequest(ButterCMSAPIConfig.TAGS + slug, queryParameters, TagResponse.class);
    }


    @Override
    public TagsResponse getTags(final Map<String, String> queryParameters) throws IOException {
        return readGetRequest(ButterCMSAPIConfig.TAGS, queryParameters, TagsResponse.class);
    }

    // FEEDS AND SITEMAP (all XML)

    @Override
    public void getAtom() {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public void getRSS() {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public void getSitemap() {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    private <T> T readGetRequest(String url, Map<String, String> queryParameters, Class<T> classType) throws IOException {
        HttpGet httpGet = new HttpGet(buildURL(url, queryParameters));
        try {
            return mapper.readValue(client.execute(httpGet).getEntity().getContent(), classType);
        } finally {
            httpGet.releaseConnection();
        }
    }

    private String buildURL(final String path, final Map<String, String> queryParameters) {
        final URIBuilder uriBuilder = new URIBuilder(URI.create(ButterCMSAPIConfig.API_BASE + path));
        uriBuilder.addParameter("auth_token", this.authToken);
        if (queryParameters != null) {
            queryParameters.forEach(uriBuilder::addParameter);
        }
        return uriBuilder.toString();
    }
}
