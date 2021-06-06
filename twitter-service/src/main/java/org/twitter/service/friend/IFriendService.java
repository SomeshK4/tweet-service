package org.twitter.service.friend;

public interface IFriendService {

    void createFriends(String userContextEmail, String userNameToFollow);

    void deleteFriends(String userContextEmail, String userNameToUnfollow);
}
