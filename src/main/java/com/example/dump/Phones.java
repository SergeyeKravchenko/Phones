package com.example.dump;

import com.example.model.Phone;
import com.example.service.file.FilePhoneServiceImpl;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Map;

@XmlRootElement
@XmlAccessorType(XmlAccessType.NONE)
public class Phones {
    private Map<Long,Phone> phones = FilePhoneServiceImpl.getPhonesFileDb();

    public Map<Long, Phone> getPhones() {
        return phones;
    }
    @XmlElement(name = "users")
    public void setPhones(Map<Long, Phone> phones) {
        this.phones = phones;
    }
}
