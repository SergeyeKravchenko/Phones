package com.example.service.db;

import com.example.model.Phone;
import com.example.repository.PhoneRepository;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service(value = "phoneService")
@Profile({"Mysql","Test"})
@NoArgsConstructor
public class PhoneServiceImpl implements PhoneService {

    private PhoneRepository repository;

    @Autowired
    public PhoneServiceImpl(PhoneRepository repository) {
        this.repository = repository;
    }


    private static final Logger log = LoggerFactory.getLogger(PhoneServiceImpl.class);

    @Override
    @Transactional
    public void savePhone(Phone phone) {
        repository.save(phone);
        log.info("Phone of " + phone.getLastname() + " was saved");
    }

    @Override
    @Transactional
    public void deletePhone(Long id) {
        repository.delete(id);
        log.info("Phone with " + id + " was deleted");
    }

    @Override
    @Transactional(readOnly = true)
    public Phone getPhoneById(Long id) {
        Phone phone = repository.findOne(id);
        log.info("Found phone with id : " + phone.getId());
        return phone;
    }

    @Override
    @Transactional
    public void updatePhone(Phone phone) {
        Phone savedPhone = repository.findOne(phone.getId());
        if (savedPhone != null) {
            savedPhone.setFirstname(phone.getFirstname());
            savedPhone.setLastname(phone.getLastname());
            savedPhone.setMiddlename(phone.getMiddlename());
            savedPhone.setPhonemobile(phone.getPhonemobile());
            savedPhone.setPhonepermanent(phone.getPhonepermanent());
            savedPhone.setAddress(phone.getAddress());
            savedPhone.setEmail(phone.getEmail());
            log.info("Updating phone with id : " + savedPhone.getId());
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<Phone> findRowBySearchCriteria(String login, String criteria) {
        log.info("Filter phones of user : " + login + " with substring " + criteria);
        return repository.findByUser_Login(login).stream().filter((e) -> e.getFirstname().toLowerCase().contains(criteria.toLowerCase())
                || (e.getLastname().toLowerCase().contains(criteria.toLowerCase())
                || (e.getPhonemobile()).contains(criteria))).collect(Collectors.toList());
    }
}
