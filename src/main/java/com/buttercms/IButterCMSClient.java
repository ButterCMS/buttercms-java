package com.buttercms;

import com.buttercms.model.*;
import org.w3c.dom.Document;

import java.util.Map;

public interface IButterCMSClient {
    /**
     * @return current auth_token
     */
    String getAuthToken();

    /**
     * sets new auth_token
     *
     * @param authToken new auth_token
     */
    void setAuthToken(String authToken);

    /**
     * Retrieves an author’s information
     *
     * @param slug            the slug of the author to be retrieved.
     * @param queryParameters query parameters as described in https://buttercms.com/docs/api/#authors
     * @return author object
     */
    AuthorResponse getAuthor(String slug, Map<String, String> queryParameters);

    /**
     * Retrieves a list of authors
     *
     * @param queryParameters query parameters as described in https://buttercms.com/docs/api/#authors
     * @return a list of authors
     */
    AuthorsResponse getAuthors(Map<String, String> queryParameters);

    /**
     * Retrieves the details of a category
     *
     * @param slug            The slug of the category to be retrieved
     * @param queryParameters query parameters as described in https://buttercms.com/docs/api/#categories
     * @return a category object
     */
    CategoryResponse getCategory(String slug, Map<String, String> queryParameters);

    /**
     * Returns a list of blog categories. You can include each category’s posts using the include=recent_posts query parameter.
     *
     * @param queryParameters query parameters as described in https://buttercms.com/docs/api/#categories
     * @return an array of categories
     */
    CategoriesResponse getCategories(Map<String, String> queryParameters);

    /**
     * Get a single Page using its slug
     *
     * @param pageTypeSlug    Use '*'. If limiting to a Page Type, use the Page Type slug.
     * @param pageSlug        The slug of the page.
     * @param queryParameters query parameters as described in https://buttercms.com/docs/api/#get-a-single-page
     * @param classType       Object that Page should be deserialize in to
     * @return A single page
     */
    <T> PageResponse<T> getPage(String pageTypeSlug, String pageSlug, Map<String, String> queryParameters, Class<T> classType);

    /**
     * Get a list of all pages for a given Page Type using its slug.
     *
     * @param pageTypeSlug    The slug of the type of pages you want to retrieve
     * @param queryParameters query parameters as described in https://buttercms.com/docs/api/#get-multiple-pages-(page-type)
     * @param classType       Object that Page should be deserialize in to
     * @return A list of pages
     */
    <T> PagesResponse<T> getPages(String pageTypeSlug, Map<String, String> queryParameters, Class<T> classType);


    /**
     * Get a list of all pages for a given Page Type using its slug.
     *
     * @param pageTypeSlug    The slug of the type of pages you want to retrieve
     * @param queryParameters query parameters as described in https://buttercms.com/docs/api/#search-pages
     * @param classType       Object that Page should be deserialize in to
     * @return A list of pages
     */
    <T> PagesResponse<T> getSearchPages(String pageTypeSlug, Map<String, String> queryParameters, Class<T> classType);

    /**
     * Get your Blog Post
     *
     * @param slug The slug of post
     * @return a single post
     */
    PostResponse getPost(String slug);

    /**
     * Returns a list of published posts. The posts are returned sorted by publish date, with the most recent posts appearing first.
     * The endpoint supports pagination and filtering.
     *
     * @param queryParameters query parameters as described in https://buttercms.com/docs/api/?shell#get-your-blog-posts
     * @return a list of posts
     */
    PostsResponse getPosts(Map<String, String> queryParameters);

    /**
     * Returns a list of published posts searched by the given 'query' search term.
     * The posts are returned sorted by publish date, with the most recent posts appearing first.
     * The endpoint supports pagination and filtering.
     *
     * @param queryParameters query parameters as described in https://buttercms.com/docs/api/?shell#search-posts
     * @return a list of posts
     */
    PostsResponse getSearchPosts(Map<String, String> queryParameters);

    /**
     * Retrieves the details of a tag
     *
     * @param slug            Unique identifier
     * @param queryParameters query parameters as described in https://buttercms.com/docs/api/?shell#tags
     * @return a single tag object
     */
    TagResponse getTag(String slug, Map<String, String> queryParameters);

    /**
     * Returns a list of blog tags. You can incude each tags’s posts using the include=recent_posts query parameter.
     *
     * @param queryParameters query parameters as described in https://buttercms.com/docs/api/?shell#tags
     * @return a list of blog tags
     */
    TagsResponse getTags(Map<String, String> queryParameters);

    /**
     * @return site map
     */
    Document getSiteMap();

    /**
     * Returns values for the supplied Collection slug
     *
     * @param collectionSlug  The slug of collection
     * @param queryParameters query parameters as described in https://buttercms.com/docs/api/#retrieve-a-collection
     * @param classType       Object that Collection item should be deserialize in to
     * @return a single collection
     */
    public <T> CollectionResponse<T> getCollection(final String collectionSlug, final Map<String, String> queryParameters, Class<T> classType);

    /**
     * @return an RSS feed
     */
    Document getRSS();

    /**
     * @return an ATOM feed
     */
    Document getAtom();
}
