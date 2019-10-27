package com.buttercms;

import com.buttercms.model.*;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.Map;

public interface IButterCMSClient {
    String getAuthToken();

    void setAuthToken(String authToken);

    // TODO: JavaDoc
    AuthorResponse getAuthor(String slug, Map<String, String> queryParameters) throws IOException;

    // TODO: JavaDoc
    AuthorsResponse getAuthors(Map<String, String> queryParameters) throws IOException;

    // TODO: JavaDoc
    CategoryResponse getCategory(String slug, Map<String, String> queryParameters) throws IOException;

    // TODO: JavaDoc
    CategoriesResponse getCategories(Map<String, String> queryParameters) throws IOException;

    <T> PageResponse<T> getPage(String pageTypeSlug, String pageSlug, Map<String, String> queryParameters, Class<T> classType) throws IOException;

    // TODO: JavaDoc
    <T> PagesResponse<T> getPages(String pageTypeSlug, Map<String, String> queryParameters, Class<T> classType) throws IOException;

    // TODO: JavaDoc
    PostResponse getPost(String slug) throws IOException;

    // TODO: JavaDoc
    PostsResponse getPosts(Map<String, String> queryParameters) throws IOException;

    // TODO: JavaDoc
    TagResponse getTag(String slug, Map<String, String> queryParameters) throws IOException;

    // TODO: JavaDoc
    TagsResponse getTags(Map<String, String> queryParameters) throws IOException;

    Document getSiteMap() throws IOException, ParserConfigurationException, SAXException;

    public <T> CollectionResponse<T> getCollection(final String collectionSlug, final Map<String, String> queryParameters, Class<T> classType) throws IOException;

    Document getRSS() throws IOException, ParserConfigurationException, SAXException;

    Document getAtom() throws IOException, ParserConfigurationException, SAXException;
}
