package com.example.util;

import com.example.dump.Users;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.FileOutputStream;

public class Utils {

    private static final Logger log = LoggerFactory.getLogger(Utils.class);

    public static String getLoginFromContext() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }

    public static <T> void dumpData(Class clazz, T source, String path) {
        try {
            File file = new File(path + clazz.getSimpleName() + ".xml");
            JAXBContext jc = JAXBContext.newInstance(clazz);
            Marshaller marshaller = jc.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.marshal(source, file);
        } catch (JAXBException e) {
            log.info("In Utils while marshalling");
        }
    }

    public static <T> T loadData(Class clazz, String path) {
        Object data = null;
        try {
            File file = new File(path + clazz.getSimpleName() + ".xml");
            JAXBContext jaxbContext = JAXBContext.newInstance(clazz);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            data = jaxbUnmarshaller.unmarshal(file);
        } catch (JAXBException e) {
            log.info("In Utils while unmarshalling");
        }
        return (T) data;
    }
}
