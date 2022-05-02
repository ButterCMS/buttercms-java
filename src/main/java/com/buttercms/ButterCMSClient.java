package com.buttercms;

import com.buttercms.config.ButterCMSAPIConfig;
import com.buttercms.exception.ButterCMSResponseException;
import com.buttercms.model.*;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
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
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;


public class ButterCMSClient implements IButterCMSClient {
    private final ObjectMapper mapper;
    private final HttpClient client;
    private String authToken;
    private final Integer preview;

    /**
     * Requests made with a missing or invalid token will get a 401 Unauthorized response. All requests must be made over HTTPS
     *
     * @param authToken ButterCMS auth_token
     */
    public ButterCMSClient(final String authToken, final boolean preview) {
        this.authToken = authToken;
        this.mapper = new ObjectMapper();
        this.mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        this.mapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
        this.client = HttpClients.createDefault();
        this.preview = preview ? 1 : 0;
    }

    /**
     * Requests made with a missing or invalid token will get a 401 Unauthorized response. All requests must be made over HTTPS
     *
     * @param authToken ButterCMS auth_token
     * @param client    custom HttpClient
     */
    public ButterCMSClient(final String authToken, final boolean preview, HttpClient client) {
        this.authToken = authToken;
        this.mapper = new ObjectMapper();
        this.mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        this.mapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
        this.preview = preview ? 1 : 0;
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

    // AUTHOR

    @Override
    public AuthorResponse getAuthor(final String slug, final Map<String, String> queryParameters) {
        return readGetRequest(ButterCMSAPIConfig.AUTHORS + slug, queryParameters, AuthorResponse.class);
    }

    @Override
    public AuthorsResponse getAuthors(final Map<String, String> queryParameters) {
        return readGetRequest(ButterCMSAPIConfig.AUTHORS, queryParameters, AuthorsResponse.class);
    }

    // CATEGORY

    @Override
    public CategoryResponse getCategory(final String slug, final Map<String, String> queryParameters) {
        return readGetRequest(ButterCMSAPIConfig.CATEGORIES + slug, queryParameters, CategoryResponse.class);
    }

    @Override
    public CategoriesResponse getCategories(final Map<String, String> queryParameters) {
        return readGetRequest(ButterCMSAPIConfig.CATEGORIES, queryParameters, CategoriesResponse.class);
    }


    @Override
    public <T> PageResponse<T> getPage(final String pageTypeSlug, final String pageSlug, final Map<String, String> queryParameters, Class<T> classType) {
        final String path = String.format(ButterCMSAPIConfig.PAGES + "%s/%s/", pageTypeSlug, pageSlug);
        return readGenericRequest(path, queryParameters, PageResponse.class, classType);
    }

    @Override
    public <T> PagesResponse<T> getPages(final String pageTypeSlug, final Map<String, String> queryParameters, Class<T> classType) {
        return readGenericRequest(ButterCMSAPIConfig.PAGES + pageTypeSlug, queryParameters, PagesResponse.class, classType);
    }

    @Override
    public <T> PagesResponse<T> getSearchPages(final String pageTypeSlug, final Map<String, String> queryParameters, Class<T> classType) {
        return readGenericRequest(ButterCMSAPIConfig.PAGE_SEARCH + pageTypeSlug, queryParameters, PagesResponse.class, classType);
    }

    @Override
    public <T> CollectionResponse<T> getCollection(final String collectionSlug, final Map<String, String> queryParameters, Class<T> classType) {
        final String path = String.format(ButterCMSAPIConfig.CONTENT + "%s/", collectionSlug);
        HttpGet httpGet = new HttpGet(buildURL(path, queryParameters));
        try {
            // manual parsing due to variable json field name
            InputStream json = client.execute(httpGet).getEntity().getContent();
            JsonNode root = this.mapper.readTree(json);
            CollectionResponse<T> response = new CollectionResponse<>();
            response.setMeta(mapper.convertValue(root.get("meta"), PaginationMeta.class));
            Collection<T> collection = new Collection<>();
            collection.setItems(Arrays.asList(mapper.convertValue(root.get("data").get(collectionSlug),
                    mapper.getTypeFactory().constructArrayType(classType))));
            response.setData(collection);
            return response;
        } catch (IOException e) {
            throw new ButterCMSResponseException("Unexpected response deserialization error", e);
        } finally {
            httpGet.releaseConnection();
        }
    }

    // POST


    @Override
    public PostResponse getPost(final String slug) {
        return readGetRequest(ButterCMSAPIConfig.POSTS + slug, null, PostResponse.class);
    }


    @Override
    public PostsResponse getPosts(final Map<String, String> queryParameters) {
        return readGetRequest(ButterCMSAPIConfig.POSTS, queryParameters, PostsResponse.class);
    }

    @Override
    public PostsResponse getSearchPosts(final Map<String, String> queryParameters) {
        return readGetRequest(ButterCMSAPIConfig.POST_SEARCH, queryParameters, PostsResponse.class);
    }


    // TAG


    @Override
    public TagResponse getTag(final String slug, final Map<String, String> queryParameters) {
        return readGetRequest(ButterCMSAPIConfig.TAGS + slug, queryParameters, TagResponse.class);
    }


    @Override
    public TagsResponse getTags(final Map<String, String> queryParameters) {
        return readGetRequest(ButterCMSAPIConfig.TAGS, queryParameters, TagsResponse.class);
    }

    @Override
    public Document getSiteMap() {
        return readXML(ButterCMSAPIConfig.SITE_MAP_ENDPOINT);
    }

    @Override
    public Document getRSS() {
        return readXML(ButterCMSAPIConfig.RSS_FEED_ENDPOINT);
    }


    @Override
    public Document getAtom() {
        return readXML(ButterCMSAPIConfig.ATOM_ENDPOINT);
    }


    private <T> T readGetRequest(String url, Map<String, String> queryParameters, Class<T> classType) {
        queryParameters = addPreview(queryParameters);
        HttpGet httpGet = new HttpGet(buildURL(url, queryParameters));
        try {
            HttpResponse response = client.execute(httpGet);
            if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
                throw new ButterCMSResponseException(getErrorMessage(response.getEntity()));
            }
            return mapper.readValue(response.getEntity().getContent(), classType);
        } catch (IOException e) {
            throw new ButterCMSResponseException("Unexpected response deserialization error", e);
        } finally {
            httpGet.releaseConnection();
        }
    }

    private <T, G> T readGenericRequest(String url, Map<String, String> queryParameters, Class<T> classType, Class<G> genericClassType) {
        queryParameters = addPreview(queryParameters);
        HttpGet httpGet = new HttpGet(buildURL(url, queryParameters));
        try {
            HttpResponse response = client.execute(httpGet);
            if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
                throw new ButterCMSResponseException(getErrorMessage(response.getEntity()));
            }
            return mapper.readValue(response.getEntity().getContent(),
                    mapper.getTypeFactory().constructParametricType(classType, genericClassType));
        } catch (IOException e) {
            throw new ButterCMSResponseException("Unexpected response deserialization error", e);
        } finally {
            httpGet.releaseConnection();
        }
    }

    private Document readXML(String url) {
        HttpGet httpGet = new HttpGet(buildURL(url, null));
        try {
            HttpResponse response = client.execute(httpGet);
            if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
                throw new ButterCMSResponseException(getErrorMessage(response.getEntity()));
            }
            InputStream json = response.getEntity().getContent();
            JsonNode root = mapper.readTree(json);
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(new ByteArrayInputStream(
                    root.get("data").asText().getBytes(Charset.forName("UTF-8")))
            );
            return doc;
        } catch (SAXException | ParserConfigurationException | IOException e) {
            throw new ButterCMSResponseException("Unexpected response deserialization error", e);
        } finally {
            httpGet.releaseConnection();
        }
    }

    private String buildURL(final String path, final Map<String, String> queryParameters) {
        final URIBuilder uriBuilder = new URIBuilder(URI.create(ButterCMSAPIConfig.API_BASE + path));
        uriBuilder.addParameter("auth_token", this.authToken);
        uriBuilder.addParameter("preview", this.preview.toString());
        if (queryParameters != null) {
            queryParameters.forEach(uriBuilder::addParameter);
        }
        return uriBuilder.toString();
    }

    private String getErrorMessage(HttpEntity httpEntity) throws IOException {
        JsonNode root = mapper.readTree(httpEntity.getContent());
        return root.get("detail").isNull() ? null : root.get("detail").asText();
    }

    private Map<String, String> addPreview(Map<String, String> queryParameters) {
        if (queryParameters == null || queryParameters.isEmpty()) {
            queryParameters = new HashMap<String, String>() {{
                put("preview", preview.toString());
            }};
        } else {
            queryParameters.put("preview", this.preview.toString());
        }
        return queryParameters;
    }
}
