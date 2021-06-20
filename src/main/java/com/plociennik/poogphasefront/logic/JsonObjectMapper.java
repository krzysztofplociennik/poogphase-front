package com.plociennik.poogphasefront.logic;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.plociennik.poogphasefront.model.ChatMessageDto;
import com.plociennik.poogphasefront.model.CommentDto;
import com.plociennik.poogphasefront.model.PostDto;
import com.plociennik.poogphasefront.model.UserDto;

import java.io.IOException;

public class JsonObjectMapper {
    private ObjectMapper objectMapper;

    public JsonObjectMapper() {
        objectMapper = new ObjectMapper();
        objectMapper.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);
    }

    public String mapPostDtoToJson(PostDto postDto) throws IOException {
        ObjectNode post = objectMapper.createObjectNode();
        post.put("authorId", postDto.getAuthorId());
        post.put("dateTime", postDto.getDateTime().toString());
        post.put("content", postDto.getContent());

        ArrayNode comments = objectMapper.createArrayNode();
        for (CommentDto comment : postDto.getComments()) {
            ObjectNode objectNode = objectMapper.createObjectNode();
            objectNode.put("authorId", comment.getAuthorId());
            objectNode.put("postId", comment.getPostId());
            objectNode.put("dateTime", comment.getDateTime().toString());
            objectNode.put("content", comment.getContent());
            comments.add(objectNode);
        }

        return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(post);
    }

    public String mapCommentDtoToJson(CommentDto commentDto) throws IOException {
        ObjectNode comment = objectMapper.createObjectNode();
        comment.put("authorId", commentDto.getAuthorId());
        comment.put("postId", commentDto.getPostId());
        comment.put("dateTime", commentDto.getDateTime().toString());
        comment.put("content", commentDto.getContent());

        return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(comment);
    }

    public String mapChatMessageDtoToJson(ChatMessageDto chatMessageDto) throws IOException {
        ObjectNode message = objectMapper.createObjectNode();
        message.put("authorId", chatMessageDto.getAuthorId());
        message.put("recipient", chatMessageDto.getRecipient());
        message.put("dateTime", chatMessageDto.getDateTime().toString());
        message.put("content", chatMessageDto.getContent());

        return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(message);
    }

    public String mapUserDtoToJson(UserDto userDto) throws IOException {
        ObjectNode user = objectMapper.createObjectNode();
        user.put("id", userDto.getId());
        user.put("username", userDto.getUsername());
        user.put("password", userDto.getPassword());
        user.put("mail", userDto.getMail());
        user.put("dateOfBirth", userDto.getDateOfBirth().toString());
        ArrayNode friendsNode = user.putArray("friends");
        for (String friend : userDto.getFriends()) {
            friendsNode.add(friend);
        }

        ArrayNode commentsNode = user.putArray("comments");
        for (CommentDto commentDto : userDto.getComments()) {
            ObjectNode comment = objectMapper.createObjectNode();
            comment.put("id", commentDto.getId());
            comment.put("authorId", commentDto.getAuthorId());
            comment.put("postId", commentDto.getPostId());
            comment.put("dateTime", commentDto.getDateTime().toString());
            comment.put("content", commentDto.getContent());
            commentsNode.add(comment);
        }

        ArrayNode messagesNode = user.putArray("messages");
        for (ChatMessageDto messageDto : userDto.getMessages()) {
            ObjectNode message = objectMapper.createObjectNode();
            message.put("id", messageDto.getId());
            message.put("authorId", messageDto.getAuthorId());
            message.put("recipient", messageDto.getRecipient());
            message.put("dateTime", messageDto.getDateTime().toString());
            message.put("content", messageDto.getContent());
            messagesNode.add(message);
        }

        ArrayNode postsNode = user.putArray("posts");
        for (PostDto postDto : userDto.getPosts()) {
            ObjectNode post = objectMapper.createObjectNode();
            post.put("id", postDto.getId());
            post.put("authorId", postDto.getAuthorId());
            post.put("dateTime", postDto.getDateTime().toString());
            post.put("content", postDto.getContent());

            ArrayNode comments = objectMapper.createArrayNode();
            for (CommentDto comment : postDto.getComments()) {
                ObjectNode objectNode = objectMapper.createObjectNode();
                objectNode.put("id", comment.getId());
                objectNode.put("authorId", comment.getAuthorId());
                objectNode.put("postId", comment.getPostId());
                objectNode.put("dateTime", comment.getDateTime().toString());
                objectNode.put("content", comment.getContent());
                comments.add(objectNode);
            }
            postsNode.add(post);
        }
        return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(user);
    }
}
