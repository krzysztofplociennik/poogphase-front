package com.plociennik.poogphasefront.gui;

import com.plociennik.poogphasefront.client.ApiClient;
import com.plociennik.poogphasefront.gui.forms.PostWallForm;
import com.plociennik.poogphasefront.model.PostDto;
import com.plociennik.poogphasefront.model.UserDto;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.splitlayout.SplitLayout;
import com.vaadin.flow.component.splitlayout.SplitLayoutVariant;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

@Route("dash")
@PageTitle("Dashboard")
@UIScope
public class Dash extends HorizontalLayout {
    private GeneralView generalView;
    private SplitLayout firstPartOfContentLayout;
    private SplitLayout secondPartOfContentLayout;
    private ApiClient apiClient;

    @Autowired
    public Dash(ApiClient apiClient) {
        this.apiClient = apiClient;
        setSizeFull();
        setupContentView();

        add(generalView);
    }

    public void setupContentView() {
        this.generalView = new GeneralView();
        this.firstPartOfContentLayout = new SplitLayout();
        this.secondPartOfContentLayout = new SplitLayout();
        generalView.setSizeFull();
        generalView.addToSecondary(firstPartOfContentLayout);
        firstPartOfContentLayout.addToSecondary(secondPartOfContentLayout);

        setupPostActionView();
        setupWallView();
        setupAPIView();
    }

    public void setupPostActionView() {
        firstPartOfContentLayout.setOrientation(SplitLayout.Orientation.HORIZONTAL);
        firstPartOfContentLayout.setPrimaryStyle("minWidth", "300px");
        firstPartOfContentLayout.setPrimaryStyle("maxWidth", "300px");

        ComboBox<UserDto> userPick = new ComboBox<>("user");
        userPick.setItems(apiClient.getUsers());
        userPick.setItemLabelGenerator(UserDto::getUsername);

        TextArea whatDoYouThinkTextArea = new TextArea("Want to share something?");
        whatDoYouThinkTextArea.setWidth("250px");
        whatDoYouThinkTextArea.setHeight("150px");
        whatDoYouThinkTextArea.setPreventInvalidInput(true);
        Button createPostButton = new Button("post!");
        createPostButton.addClickListener(buttonClickEvent -> {
            PostDto post = new PostDto();
            post.setAuthorId(userPick.getValue().getId());
            post.setDateTime(LocalDateTime.now());
            post.setContent(whatDoYouThinkTextArea.getValue());
            try {
                apiClient.createPost(post);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        VerticalLayout verticalLayout = new VerticalLayout();
        verticalLayout.add(userPick, whatDoYouThinkTextArea, createPostButton);
        firstPartOfContentLayout.addToPrimary(verticalLayout);
    }

    public void setupWallView() {
        secondPartOfContentLayout.addThemeVariants(SplitLayoutVariant.LUMO_SMALL);
        secondPartOfContentLayout.setOrientation(SplitLayout.Orientation.HORIZONTAL);
        secondPartOfContentLayout.setPrimaryStyle("minWidth", "950px");
        secondPartOfContentLayout.setPrimaryStyle("maxWidth", "950px");

        List<PostDto> posts = apiClient.getPosts();
        Comparator<PostDto> compareByDate = Comparator.comparing(PostDto::getDateTime).reversed();
        posts.sort(compareByDate);

        VerticalLayout verticalLayout = new VerticalLayout();
        secondPartOfContentLayout.addToPrimary(verticalLayout);
        verticalLayout.add(new H4("Look what your friends have been doing recently!"));

        Button closeViewButton = new Button("close", buttonClickEvent -> setupAPIView());
        for (PostDto post : posts) {
            verticalLayout.add(new PostWallForm(this.apiClient, post, secondPartOfContentLayout, closeViewButton), new Paragraph("================="));
        }
    }

    public void setupAPIView() {
        secondPartOfContentLayout.addToSecondary(new Button("API WIDGETS"));
    }
}
