package com.example.repository.file;

import com.example.model.Phone;
import com.example.repository.db.PhoneRepository;
import com.example.service.file.FilePhoneServiceImpl;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Profile("file")
@Service
public interface FilePhoneRepository extends PhoneRepository {

    @Override
    public default List<Phone> findByUser_Login(String login) {
        List<Phone> phoneList = new ArrayList<>();
        for (Long l : FilePhoneServiceImpl.getPhonesFileDb().keySet()) {
            Phone phone = FilePhoneServiceImpl.getPhonesFileDb().get(l);
            if (phone.getUser().getLogin().equals(login)) {
                phoneList.add(phone);
            }
        }
        return phoneList;
    }
}