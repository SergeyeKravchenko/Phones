package com.example.service.db;

import com.example.model.Phone;

import java.util.List;

public interface PhoneService {
    void savePhone(Phone phone);
    void deletePhone(Long id);
    Phone getPhoneById(Long id);
    void updatePhone(Phone phone);
    List<Phone> findRowBySearchCriteria(String login, String criteria);
}
