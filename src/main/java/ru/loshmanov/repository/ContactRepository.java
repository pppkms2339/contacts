package ru.loshmanov.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.loshmanov.entity.Contact;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Long> {

}
