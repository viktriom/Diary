package com.sonu.diary.remote;

import com.sonu.diary.domain.Groups;

import java.util.List;

public interface GroupDataReceived {
    void dataReceived(List<Groups> data);
}
