package com.buttercms;

import com.buttercms.model.Author;
import com.buttercms.model.AuthorResponse;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.impl.client.CloseableHttpClient;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("ButterCMS client")
public class ButterCMSClientTest {
    @Test
    @DisplayName("should be able to deserialize an Author object")
    public void testGetAuthor() throws IOException {
        String json = "{\"data\":{\"first_name\":\"Fake\",\"last_name\":\"Name\",\"email\":\"fake@example.com\"," +
                "\"slug\":\"fake-name\",\"bio\":\"\",\"title\":\"\",\"linkedin_url\":\"\",\"facebook_url\":\"\"," +
                "\"instagram_url\":\"\",\"pinterest_url\":\"\",\"twitter_handle\":\"ButterCMS\"," +
                "\"profile_image\":\"\"}}";
        CloseableHttpClient closeableHttpClient = mock(CloseableHttpClient.class);
        CloseableHttpResponse closeableHttpResponse = mock(CloseableHttpResponse.class);
        HttpEntity httpEntity = mock(HttpEntity.class);

        when(closeableHttpClient.execute(any())).thenReturn(closeableHttpResponse);
        when(closeableHttpResponse.getEntity()).thenReturn(httpEntity);
        when(httpEntity.getContent()).thenReturn(new ByteArrayInputStream(json.getBytes()));

        Author expectedAuthor = new Author();
        expectedAuthor.setFirstName("Fake");
        expectedAuthor.setLastName("Name");
        expectedAuthor.setEmail("fake@example.com");
        expectedAuthor.setSlug("fake-name");
        expectedAuthor.setTwitterHandle("ButterCMS");
        expectedAuthor.setBio("");
        expectedAuthor.setTitle("");
        expectedAuthor.setLinkedinUrl("");
        expectedAuthor.setFacebookUrl("");
        expectedAuthor.setInstagramUrl("");
        expectedAuthor.setPinterestUrl("");
        expectedAuthor.setProfileImage("");

        ButterCMSClient client = new ButterCMSClient("token", closeableHttpClient);
        AuthorResponse actualAuthor = client.getAuthor("fake-name", null);

        Assertions.assertEquals(expectedAuthor, actualAuthor);
    }
}
