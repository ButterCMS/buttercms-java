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
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Arrays;
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
    public <T> PageResponse<T> getPage(final String pageTypeSlug, final String pageSlug, final Map<String, String> queryParameters, Class<T> classType) throws IOException {
        final String path = String.format(ButterCMSAPIConfig.PAGES + "%s/%s/", pageTypeSlug, pageSlug);
        return readGenericRequest(path, queryParameters, PageResponse.class, classType);
    }

    @Override
    public <T> PagesResponse<T> getPages(final String pageTypeSlug, final Map<String, String> queryParameters, Class<T> classType) throws IOException {
        return readGenericRequest(ButterCMSAPIConfig.PAGES + pageTypeSlug, queryParameters, PagesResponse.class, classType);
    }


    @Override
    public <T> CollectionResponse<T> getCollection(final String collectionSlug, final Map<String, String> queryParameters, Class<T> classType) throws IOException {
        final String path = String.format(ButterCMSAPIConfig.CONTENT + "%s/", collectionSlug);
        HttpGet httpGet = new HttpGet(buildURL(path, queryParameters));
        try {
            // manual parsing due to variable json field name
            InputStream json = client.execute(httpGet).getEntity().getContent();
            JsonNode root = this.mapper.readTree(json);
            CollectionResponse<T> response = new CollectionResponse<>();
            response.setMeta(mapper.convertValue(root.get("meta"), PaginationMeta.class));
            Collection<T> collection = new Collection<>();
            for (JsonNode coll : root.get("data")) {
                collection.setItems(Arrays.asList(mapper.convertValue(coll,
                        mapper.getTypeFactory().constructArrayType(classType))));
                break;
            }
            response.setData(collection);
            return response;
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

    @Override
    public Document getSiteMap() throws IOException, ParserConfigurationException, SAXException {
        return readXML(ButterCMSAPIConfig.SITE_MAP_ENDPOINT);
    }

    @Override
    public Document getRSS() throws IOException, ParserConfigurationException, SAXException {
        return readXML(ButterCMSAPIConfig.RSS_FEED_ENDPOINT);
    }


    @Override
    public Document getAtom() throws IOException, ParserConfigurationException, SAXException {
        return readXML(ButterCMSAPIConfig.ATOM_ENDPOINT);
    }


    private <T> T readGetRequest(String url, Map<String, String> queryParameters, Class<T> classType) throws IOException {
        HttpGet httpGet = new HttpGet(buildURL(url, queryParameters));
        try {
            return mapper.readValue(client.execute(httpGet).getEntity().getContent(), classType);
        } finally {
            httpGet.releaseConnection();
        }
    }

    private <T, G> T readGenericRequest(String url, Map<String, String> queryParameters, Class<T> classType, Class<G> genericClassType) throws IOException {
        HttpGet httpGet = new HttpGet(buildURL(url, queryParameters));
        try {
            return mapper.readValue(client.execute(httpGet).getEntity().getContent(),
                    mapper.getTypeFactory().constructParametricType(classType, genericClassType));
        } finally {
            httpGet.releaseConnection();
        }
    }

    private Document readXML(String url) throws IOException, ParserConfigurationException, SAXException {
        HttpGet httpGet = new HttpGet(buildURL(url, null));
        try {
            InputStream json = client.execute(httpGet).getEntity().getContent();
            JsonNode root = this.mapper.readTree(json);
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(new ByteArrayInputStream(
                    root.get("data").asText().getBytes(Charset.forName("UTF-8")))
            );
            return doc;
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
