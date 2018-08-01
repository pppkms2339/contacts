package ru.loshmanov.entity;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "contact")
public class Contact implements Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Version
    @Column(name = "version")
    private int version;

    @Column(name = "first_name")
    @NotEmpty(message = "{validation.firstname.NotEmpty.message}")
    @Size(min = 3, max = 60, message = "{validation.firstname.Size.message}")
    private String firstName;

    @Column(name = "last_name")
    @NotEmpty(message = "{validation.lastname.NotEmpty.message}")
    @Size(min = 1, max = 40, message = "{validation.lastname.Size.message}")
    private String lastName;

    @Column(name = "birth_date")
    @DateTimeFormat(iso = ISO.DATE)
    @Temporal(TemporalType.DATE)
    private Date birthDate;

    @Column(name = "description")
    private String description;

    @Lob
    @Column(name = "photo")
    @Basic(fetch = FetchType.LAZY)
    private byte[] photo;

    public Contact() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

}
