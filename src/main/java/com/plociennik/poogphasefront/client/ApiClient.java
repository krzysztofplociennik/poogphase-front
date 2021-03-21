package com.plociennik.poogphasefront.client;

import com.plociennik.poogphasefront.logic.JsonObjectMapper;
import com.plociennik.poogphasefront.model.ChatMessageDto;
import com.plociennik.poogphasefront.model.CommentDto;
import com.plociennik.poogphasefront.model.PostDto;
import com.plociennik.poogphasefront.model.UserDto;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.util.Optional.ofNullable;

@Component
public class ApiClient {
    private static final Logger LOGGER = LoggerFactory.getLogger(ApiClient.class);
    @Value("${api.endpoint}")
    private String baseEndpoint;
    private JsonObjectMapper objectMapper = new JsonObjectMapper();

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    private URI getAllUsersURI() {
        return UriComponentsBuilder.fromHttpUrl(baseEndpoint + "/user/getUsers")
                .build().encode().toUri();
    }

    private URI getALlPostsURI() {
        return UriComponentsBuilder.fromHttpUrl(baseEndpoint + "/post/getPosts")
                .build().encode().toUri();
    }

    private URI getAllCommentsURI() {
        return UriComponentsBuilder.fromHttpUrl(baseEndpoint + "/comment/getComments")
                .build().encode().toUri();
    }

    private URI getAllMessagesURI() {
        return UriComponentsBuilder.fromHttpUrl(baseEndpoint + "/message/getMessages")
                .build().encode().toUri();
    }

    public List<UserDto> getUsers() {
        try {
            UserDto[] response = restTemplate().getForObject(getAllUsersURI(), UserDto[].class);
            return Arrays.asList(ofNullable(response).orElse(new UserDto[0]));
        } catch (RestClientException e) {
            LOGGER.error(e.getMessage(), e);
            return new ArrayList<>();
        }
    }

    public List<PostDto> getPosts() {
        try {
            PostDto[] response = restTemplate().getForObject(getALlPostsURI(), PostDto[].class);
            return Arrays.asList(ofNullable(response).orElse(new PostDto[0]));
        } catch (RestClientException e) {
            LOGGER.error(e.getMessage(), e);
            return new ArrayList<>();
        }
    }

    public List<CommentDto> getComments() {
        try {
            CommentDto[] response = restTemplate().getForObject(getAllCommentsURI(), CommentDto[].class);
            return Arrays.asList(ofNullable(response).orElse(new CommentDto[0]));
        } catch (RestClientException e) {
            LOGGER.error(e.getMessage(), e);
            return new ArrayList<>();
        }
    }

    public List<ChatMessageDto> getChatMessages() {
        try {
            ChatMessageDto[] response = restTemplate().getForObject(getAllMessagesURI(), ChatMessageDto[].class);
            return Arrays.asList(ofNullable(response).orElse(new ChatMessageDto[0]));
        } catch (RestClientException e) {
            LOGGER.error(e.getMessage(), e);
            return new ArrayList<>();
        }
    }

    public void createPost(PostDto postDto) throws IOException {
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        try {
            HttpPost request = new HttpPost(baseEndpoint + "/post/createPost");
            StringEntity params = new StringEntity(objectMapper.mapPostDtoToJson(postDto));
            request.addHeader("content-type", "application/json");
            request.setEntity(params);
            httpClient.execute(request);
        } catch (Exception ex) {
        } finally {
            httpClient.close();
        }
    }

    public void createComment(CommentDto commentDto) throws IOException {
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        try {
            HttpPost request = new HttpPost(baseEndpoint + "/comment/createComment");
            StringEntity params = new StringEntity(objectMapper.mapCommentDtoToJson(commentDto));
            request.addHeader("content-type", "application/json");
            request.setEntity(params);
            httpClient.execute(request);
        } catch (Exception ex) {
        } finally {
            httpClient.close();
        }
    }

    public void createMessage(ChatMessageDto chatMessageDto) throws IOException {
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        try {
            HttpPost request = new HttpPost(baseEndpoint + "/message/createMessage");
            StringEntity params = new StringEntity(objectMapper.mapChatMessageDtoToJson(chatMessageDto));
            request.addHeader("content-type", "application/json");
            request.setEntity(params);
            httpClient.execute(request);
        } catch (Exception ex) {
        } finally {
            httpClient.close();
        }
    }

    public void updateUser(UserDto userDto) throws IOException {
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        try {
            HttpPut request = new HttpPut(baseEndpoint + "/user/updateUser");
            StringEntity params = new StringEntity(objectMapper.mapUserDtoToJson(userDto));
            request.addHeader("content-type", "application/json");
            request.setEntity(params);
            httpClient.execute(request);
        } catch (Exception ex) {
        } finally {
            httpClient.close();
        }
    }
}
