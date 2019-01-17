package com.sonu.diary.services;

import java.io.IOException;

public interface HttpResponseCallback<T> {
    void handleResponse(T response);
}
