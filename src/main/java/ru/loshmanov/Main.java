package ru.loshmanov;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.loshmanov.config.AppConfig;
import ru.loshmanov.entity.Contact;
import ru.loshmanov.service.ContactService;
import ru.loshmanov.service.ContactServiceImpl;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        ApplicationContext ctx = new AnnotationConfigApplicationContext(AppConfig.class);
        ContactService contactService = (ContactService) ctx.getBean(ContactService.class);
        //List<Contact> contacts = contactService.findAll();
//        for(Contact c : contacts) {
//            System.out.println(c.getId() + " " + c.getFirstName() + " " + c.getLastName() + " " + c.getBirthDate());
//        }
        System.out.println("---");
        Contact contact = contactService.findById(1L);
        System.out.printf(contact.getId() + " " + contact.getFirstName() + " " + contact.getLastName() + " " + contact.getBirthDate());
        contact.setFirstName("qwerty");
        contact.setLastName("asdfgh");
        contactService.save(contact);
        contact = contactService.findById(1L);
        System.out.printf(contact.getId() + " " + contact.getFirstName() + " " + contact.getLastName() + " " + contact.getBirthDate());
    }
}
