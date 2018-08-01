package ru.loshmanov.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import ru.loshmanov.entity.Contact;

import java.util.List;

public interface ContactService {

    List<Contact> findAll();

    Page<Contact> findAll(PageRequest request);

    Contact findById(Long id);

    Contact save(Contact contact);

    void delete(Contact contact);

}
