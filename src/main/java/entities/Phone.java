package entities;

import dtos.PhoneDTO;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "Phone")
@NamedQuery(name = "Phone.deleteAllRows", query = "DELETE from Phone")
public class Phone {
    @Id
    @Column(name = "PhoneNumber", nullable = false)
    private String id;

    @Column(name = "descriptionPhone", nullable = false)
    private String descriptionPhone;

    @OneToMany(mappedBy = "phonePhonenumber")
    private Set<Person> people = new LinkedHashSet<>();

    public Phone() {
    }

    public Phone(String id, String descriptionPhone) {
        this.id = id;
        this.descriptionPhone = descriptionPhone;
    }

    public Phone(PhoneDTO phoneDTO)
    {
        this.id = phoneDTO.getId();
        this.descriptionPhone = phoneDTO.getDescriptionPhone();
    }

    public Set<Person> getPeople() {
        return people;
    }

    public void setPeople(Set<Person> people) {
        this.people = people;
    }

    public String getDescriptionPhone() {
        return descriptionPhone;
    }

    public void setDescriptionPhone(String descriptionPhone) {
        this.descriptionPhone = descriptionPhone;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}