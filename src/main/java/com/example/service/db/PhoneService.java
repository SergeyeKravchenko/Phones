package com.example.service.db;

import com.example.model.Phone;
import com.example.dump.Phones;

import javax.naming.OperationNotSupportedException;
import java.util.List;

public interface PhoneService {
    public void savePhone(Phone phone);
    public void deletePhone(Long id);
    public Phone getPhoneById(Long id);
    public void updatePhone(Phone phone);
    public List<Phone> findRowBySearchCriteria(String login, String criteria);
    public Phones getPhones() throws OperationNotSupportedException;
    public void setPhones(Phones phones) throws OperationNotSupportedException;
}
