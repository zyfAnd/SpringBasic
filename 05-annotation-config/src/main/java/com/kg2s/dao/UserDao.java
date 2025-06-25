package com.kg2s.dao;

public interface UserDao {
    void saveUser(String name, String gender);
    String getUser(Long userId);
    void deleteUser(Long userId);
}
