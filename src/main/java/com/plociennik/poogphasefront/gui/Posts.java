package com.plociennik.poogphasefront.gui;

import com.plociennik.poogphasefront.client.ApiClient;
import com.plociennik.poogphasefront.gui.forms.CommentListForm;
import com.plociennik.poogphasefront.gui.forms.PostWallForm;
import com.plociennik.poogphasefront.model.CommentDto;
import com.plociennik.poogphasefront.model.PostDto;
import com.plociennik.poogphasefront.model.UserDto;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.splitlayout.SplitLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Route("posts")
@PageTitle("Posts")
public class Posts extends HorizontalLayout {
    private GeneralView generalView;
    private SplitLayout firstPartOfPostsContent;
    private SplitLayout secondPartOfPostsContent;
    private ApiClient apiClient;

    @Autowired
    public Posts(ApiClient apiClient) {
        this.apiClient = apiClient;
        setSizeFull();
        setupContentView();

        add(generalView);
    }

    public void setupContentView() {
        this.generalView = new GeneralView();
        this.firstPartOfPostsContent = new SplitLayout();
        this.secondPartOfPostsContent = new SplitLayout();
        generalView.setSizeFull();
        generalView.addToSecondary(firstPartOfPostsContent);
        firstPartOfPostsContent.addToSecondary(secondPartOfPostsContent);

        recentAuthorsView();
        postsView();
        setupRecentCommentsView();
    }

    public void recentAuthorsView() {
        VerticalLayout authorsLayout = new VerticalLayout();
        firstPartOfPostsContent.setOrientation(SplitLayout.Orientation.HORIZONTAL);
        firstPartOfPostsContent.setPrimaryStyle("minWidth", "300px");
        firstPartOfPostsContent.setPrimaryStyle("maxWidth", "300px");

        authorsLayout.add(new H3("Most recent active users: "));

        for (UserDto user : recentActiveUsers()) {
            authorsLayout.add(authorsForm(user));
        }

        authorsLayout.add(new H3("h3 text"));
        firstPartOfPostsContent.addToPrimary(authorsLayout);
    }

    public void postsView() {
        VerticalLayout wallLayout = new VerticalLayout();
        secondPartOfPostsContent.setOrientation(SplitLayout.Orientation.HORIZONTAL);
        secondPartOfPostsContent.setPrimaryStyle("minWidth", "950px");
        secondPartOfPostsContent.setPrimaryStyle("maxWidth", "950px");
        secondPartOfPostsContent.addToPrimary(wallLayout);

        List<PostDto> reverseSortedPosts = apiClient.getPosts().stream()
                .sorted(Comparator.comparing(PostDto::getDateTime).reversed())
                .limit(10)
                .collect(Collectors.toList());

        Button closeViewButton = new Button("close", buttonClickEvent -> setupRecentCommentsView());
        for (PostDto post : reverseSortedPosts) {
            wallLayout.add(new PostWallForm(this.apiClient, post, secondPartOfPostsContent, closeViewButton), new Paragraph("================="));
        }
    }

    public void setupRecentCommentsView() {
        VerticalLayout recentCommentsLayout = new VerticalLayout();
        List<CommentDto> reverseSortedComments = apiClient.getComments().stream()
                .sorted(Comparator.comparing(CommentDto::getDateTime).reversed())
                .limit(10)
                .collect(Collectors.toList());

        recentCommentsLayout.add(new H3("Most recent comments"));

        for (CommentDto comment : reverseSortedComments) {
            recentCommentsLayout.add(new CommentListForm(this.apiClient, comment));
        }

        secondPartOfPostsContent.addToSecondary(recentCommentsLayout);
    }

    public FormLayout authorsForm(UserDto userDto) {
        FormLayout form = new FormLayout();
        VerticalLayout layout = new VerticalLayout();
        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.add(new Text(userDto.getUsername()), new Button("profile"));
        layout.add(horizontalLayout, new Paragraph("============="));
        form.add(layout);
        return form;
    }

    public List<UserDto> recentActiveUsers() {
        List<UserDto> resultList = new ArrayList<>();

        List<PostDto> mostRecentPosts = apiClient.getPosts().stream()
                .sorted(Comparator.comparing(PostDto::getDateTime).reversed())
                .collect(Collectors.toList());

        for (int i = 0; i < 10; i++) {
            int tempIndex = i;
            Optional<UserDto> user = apiClient.getUsers().stream()
                    .filter(userDto -> userDto.getId() == mostRecentPosts.get(tempIndex).getAuthorId())
                    .findAny();
            Optional<UserDto> searchedUserIsAlreadyInResultList = resultList.stream()
                    .filter(userDto -> userDto.getUsername().equals(user.get().getUsername()))
                    .findAny();
            if (searchedUserIsAlreadyInResultList.isEmpty()) {
                resultList.add(user.get());
            }
        }
        return resultList;
    }
}
