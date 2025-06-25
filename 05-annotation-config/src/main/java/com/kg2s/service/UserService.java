package com.kg2s.service;

public interface UserService {
    void saveUser(String name, String gender);
    String getUser(Long userId);
    void deleteUser(Long userId);
}
