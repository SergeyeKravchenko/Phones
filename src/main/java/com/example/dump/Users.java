package com.example.dump;

import com.example.model.User;
import com.example.service.file.FileUserServiceImpl;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Map;

@XmlRootElement
@XmlAccessorType(XmlAccessType.NONE)
public class Users {

    private Map<String,User> users = FileUserServiceImpl.getUsersFileDb();

    public Map<String, User> getUsers() {
        return users;
    }

    @XmlElement(name = "users")
    public void setUsers(Map<String, User> users) {
        this.users = users;
    }
}
