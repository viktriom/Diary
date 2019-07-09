package com.sonu.diary.remote;

import com.sonu.diary.domain.User;

import java.util.List;

public interface UserDataReceived {
    void dataReceived(List<User> users);
}
