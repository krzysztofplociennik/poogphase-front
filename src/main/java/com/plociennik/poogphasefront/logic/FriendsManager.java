package com.plociennik.poogphasefront.logic;

import com.plociennik.poogphasefront.client.ApiClient;
import com.plociennik.poogphasefront.model.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class FriendsManager {
    private ApiClient apiClient;

    @Autowired
    public FriendsManager(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public boolean areTheyFriends(UserDto userDto1, UserDto userDto2) {
        return userDto1.getFriends().contains(userDto2.getUsername())
                && userDto2.getFriends().contains(userDto1.getUsername());
    }

    public List<UserDto> searchFriends(UserDto userDto) {
        List<UserDto> resultList = new ArrayList<>();

        for (String username : userDto.getFriends()) {
            Optional<UserDto> searchedUser = apiClient.getUsers().stream()
                    .filter(userDto1 -> userDto1.getUsername().equals(username))
                    .findFirst();
            searchedUser.ifPresent(resultList::add);
        }
        return resultList;
    }

    public List<UserDto> searchPossibleFriends(UserDto userDto) {
        List<UserDto> resultList = new ArrayList<>();

        for (UserDto friend : searchFriends(userDto)) {
            List<UserDto> friendsOfFriend = searchFriends(friend);
            for (UserDto possibleFriend : friendsOfFriend) {
                Optional<UserDto> optionalDuplicate = resultList.stream()
                        .filter(userDto1 -> userDto1.getUsername().equals(possibleFriend.getUsername()))
                        .findAny();
                if (optionalDuplicate.isEmpty()
                        && !possibleFriend.getUsername().equals(userDto.getUsername())
                        && !areTheyFriends(userDto, possibleFriend)) {
                    resultList.add(possibleFriend);
                }
            }
        }
        return resultList;
    }
}
