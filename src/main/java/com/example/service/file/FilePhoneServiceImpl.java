package com.example.service.file;

import com.example.model.Phone;
import com.example.service.db.PhoneService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Profile("file")
@Service
public class FilePhoneServiceImpl implements PhoneService {

    private static Map<Long, Phone> phonesFileDb = new HashMap<>();
    private static Long id = 1L;

    public static Map<Long, Phone> getPhonesFileDb() {
        return phonesFileDb;
    }

    public static void setPhonesFileDb(Map<Long, Phone> phonesFileDb) {
        FilePhoneServiceImpl.phonesFileDb = phonesFileDb;
    }

    @Override
    public void savePhone(Phone phone) {
        phone.setId(id);
        phonesFileDb.put(id++, phone);
        System.out.println("Phones :" + phonesFileDb);
    }

    @Override
    public void deletePhone(Long id) {
        phonesFileDb.remove(id);
    }

    @Override
    public Phone getPhoneById(Long id) {
        return phonesFileDb.get(id);
    }

    @Override
    public void updatePhone(Phone phone) {
        System.out.println("In update method " + phone);
        phonesFileDb.put(phone.getId(), phone);
    }

    @Override
    public List<Phone> findRowBySearchCriteria(String login, String criteria) {
        System.out.println("param login :" + login + " param criteria :" + criteria);
        List<Phone> phoneList = new ArrayList<>();
        System.out.println("Database :" + phonesFileDb);
        for (Long l : phonesFileDb.keySet()) {
            Phone phone = phonesFileDb.get(l);
            System.out.println("Phone :" + phone);
            if (phone.getUser().getLogin().equals(login)) {
                phoneList.add(phone);
            }
        }
        return phoneList.stream().filter((e) -> e.getFirstname().toLowerCase().contains(criteria.toLowerCase())
                || (e.getLastname().toLowerCase().contains(criteria.toLowerCase())
                || (e.getPhonemobile()).contains(criteria))).collect(Collectors.toList());
    }

}
