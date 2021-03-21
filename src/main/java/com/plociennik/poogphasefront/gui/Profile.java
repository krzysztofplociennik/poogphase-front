package com.plociennik.poogphasefront.gui;

import com.plociennik.poogphasefront.client.ApiClient;
import com.plociennik.poogphasefront.gui.forms.PostWallForm;
import com.plociennik.poogphasefront.logic.SessionManager;
import com.plociennik.poogphasefront.model.PostDto;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H5;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.splitlayout.SplitLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Route("profile")
@PageTitle("Profile")
public class Profile extends HorizontalLayout {
    private GeneralView generalView;
    private SplitLayout firstPartOfProfilePageContent;
    private SplitLayout secondPartOfProfilePageContent;
    private ApiClient apiClient;
    private SessionManager sessionManager;

    @Autowired
    public Profile(ApiClient apiClient) {
        this.apiClient = apiClient;
        setSizeFull();
        setupProfilePageContentView();
    }

    public void setupProfilePageContentView() {
        this.generalView = new GeneralView();
        this.firstPartOfProfilePageContent = new SplitLayout();
        this.secondPartOfProfilePageContent = new SplitLayout();
        this.sessionManager = new SessionManager(this.apiClient);

        generalView.setSizeFull();
        generalView.addToSecondary(firstPartOfProfilePageContent);
        firstPartOfProfilePageContent.addToSecondary(secondPartOfProfilePageContent);

        setupInfoView();
        setupUserPostWallView();
        setupPicturesView();

        add(generalView);
    }

    public void setupInfoView() {
        VerticalLayout infoLayout = new VerticalLayout();
        firstPartOfProfilePageContent.setOrientation(SplitLayout.Orientation.HORIZONTAL);
        firstPartOfProfilePageContent.setPrimaryStyle("minWidth", "300px");
        firstPartOfProfilePageContent.setPrimaryStyle("maxWidth", "300px");

        infoLayout.add(new H5("This is what people here know about you"));

        TextField firstNameTextField = new TextField("Firstname");
        firstNameTextField.setValue(sessionManager.getLoggedUser().getFirstName());
        firstNameTextField.setReadOnly(true);
        TextField lastNameTextField = new TextField("Lastname");
        lastNameTextField.setValue(sessionManager.getLoggedUser().getLastName());
        lastNameTextField.setReadOnly(true);
        TextField dateOfBirthTextField = new TextField("Date of birth");
        dateOfBirthTextField.setValue(sessionManager.getLoggedUser().getDateOfBirth().toString());
        dateOfBirthTextField.setReadOnly(true);

        infoLayout.add(firstNameTextField, lastNameTextField, dateOfBirthTextField);

        firstPartOfProfilePageContent.addToPrimary(infoLayout);
    }

    public void setupUserPostWallView() {
        VerticalLayout userPostsLayout = new VerticalLayout();
        secondPartOfProfilePageContent.setOrientation(SplitLayout.Orientation.HORIZONTAL);
        secondPartOfProfilePageContent.setPrimaryStyle("minWidth", "950px");
        secondPartOfProfilePageContent.setPrimaryStyle("maxWidth", "950px");
        secondPartOfProfilePageContent.addToPrimary(userPostsLayout);

        List<PostDto> reverseSortedUserPosts = apiClient.getPosts().stream()
                .filter(postDto -> postDto.getAuthorId() == sessionManager.getLoggedUser().getId())
                .sorted(Comparator.comparing(PostDto::getDateTime).reversed())
                .collect(Collectors.toList());

        Button closeViewButton = new Button("close", buttonClickEvent -> setupPicturesView());
        for (PostDto post : reverseSortedUserPosts) {
            userPostsLayout.add(new PostWallForm(this.apiClient, post, secondPartOfProfilePageContent, closeViewButton), new Paragraph("================="));
        }
    }

    public void setupPicturesView() {
        secondPartOfProfilePageContent.addToSecondary(new Button("pictures placeholder"));
    }
}
