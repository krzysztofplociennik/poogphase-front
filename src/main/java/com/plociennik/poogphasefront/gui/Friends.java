package com.plociennik.poogphasefront.gui;

import com.plociennik.poogphasefront.client.ApiClient;
import com.plociennik.poogphasefront.gui.forms.FriendListForm;
import com.plociennik.poogphasefront.gui.forms.PossibleFriendForm;
import com.plociennik.poogphasefront.logic.FriendsManager;
import com.plociennik.poogphasefront.logic.SessionManager;
import com.plociennik.poogphasefront.model.UserDto;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.splitlayout.SplitLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

@Route("friends")
@PageTitle("Friends")
public class Friends extends HorizontalLayout {
    private GeneralView generalView;
    private SplitLayout friendsPageContentLayout;
    private ApiClient apiClient;

    @Autowired
    public Friends(ApiClient apiClient) {
        this.apiClient = apiClient;
        setSizeFull();
        setupFriendsPageContentView();
        setupFriendsAndMaybeFriends();
    }

    public void setupFriendsPageContentView() {
        this.generalView = new GeneralView();
        this.friendsPageContentLayout = new SplitLayout();

        friendsPageContentLayout.setOrientation(SplitLayout.Orientation.HORIZONTAL);
        friendsPageContentLayout.setPrimaryStyle("minWidth", "750px");
        friendsPageContentLayout.setPrimaryStyle("maxWidth", "750px");
        generalView.addToSecondary(friendsPageContentLayout);
        add(generalView);
    }

    public void setupFriendsAndMaybeFriends() {
        FriendsManager friendsManager = new FriendsManager(this.apiClient);
        SessionManager sessionManager = new SessionManager(this.apiClient);

        VerticalLayout friendsLayout = new VerticalLayout();
        friendsPageContentLayout.addToPrimary(friendsLayout);
        friendsLayout.add(new H2("Your friends"));

        for (UserDto user : friendsManager.searchFriends(sessionManager.getLoggedInUser())) {
            friendsLayout.add(new FriendListForm(this.apiClient, user));
        }

        VerticalLayout maybeFriendsLayout = new VerticalLayout();
        friendsPageContentLayout.addToSecondary(maybeFriendsLayout);
        maybeFriendsLayout.add(new H2("Do you know these people?"));

        for (UserDto user : friendsManager.searchPossibleFriends(sessionManager.getLoggedInUser())) {
            maybeFriendsLayout.add(new PossibleFriendForm(this.apiClient, user));
        }
    }
}
