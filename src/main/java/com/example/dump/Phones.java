package com.example.dump;

import com.example.model.Phone;
import com.example.service.file.FilePhoneServiceImpl;
import lombok.Data;

import java.util.Map;

@Data
public class Phones{
    private Map<Long,Phone> phones = FilePhoneServiceImpl.getPhonesFileDb();
}
