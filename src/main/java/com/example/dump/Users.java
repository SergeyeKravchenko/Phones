package com.example.dump;

import com.example.model.User;
import com.example.service.file.FileUserServiceImpl;
import lombok.Data;

import java.util.Map;

@Data
public class Users{
    private Map<String,User> users = FileUserServiceImpl.getUsersFileDb();
}
