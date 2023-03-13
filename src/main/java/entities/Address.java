package entities;

import dtos.AddressDTO;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "Address")
@NamedQuery(name = "Address.deleteAllRows", query = "DELETE from Address")
public class Address {
    @Id
    @Column(name = "adress", nullable = false)
    private String id;

    @Column(name = "additionalInfo", nullable = false)
    private String additionalInfo;

    @Column(name = "street", nullable = false)
    private String street;

    @ManyToOne(fetch = FetchType.LAZY, optional = false, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "CityInfo_zipCode", nullable = false)
    private CityInfo cityinfoZipcode;

    @OneToMany(mappedBy = "addressStreet")
    private Set<Person> people = new LinkedHashSet<>();

    public Address() {
    }

    public Address(String id, String additionalInfo, String street, CityInfo cityinfoZipcode) {
        this.id = id;
        this.additionalInfo = additionalInfo;
        this.street = street;
        this.cityinfoZipcode = cityinfoZipcode;
    }

    public Address(AddressDTO addressDTO)
    {
        this.id = addressDTO.getId();
        this.additionalInfo = addressDTO.getAdditionalInfo();
        this.street = addressDTO.getStreet();
        this.cityinfoZipcode = new CityInfo(addressDTO.getCityInfo());
    }

    public Address(String id, String street, String additionalInfo)
    {
        this.id = id;
        this.street = street;
        this.additionalInfo = additionalInfo;
    }

    public Address(String street, String additionalInfo)
    {
        this.street = street;
        this.additionalInfo = additionalInfo;
    }

    public Set<Person> getPeople() {
        return people;
    }

    public void setPeople(Set<Person> people) {
        this.people = people;
    }

    public CityInfo getCityinfoZipcode() {
        return cityinfoZipcode;
    }

    public void setCityinfoZipcode(CityInfo cityinfoZipcode) {
        this.cityinfoZipcode = cityinfoZipcode;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getAdditionalInfo() {
        return additionalInfo;
    }

    public void setAdditionalInfo(String additionalInfo) {
        this.additionalInfo = additionalInfo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setCityInfo(CityInfo cityInfo)
    {
        this.cityinfoZipcode = cityInfo;
    }

    public CityInfo getCityInfo()
    {
        return this.cityinfoZipcode;
    }

    public void addPerson(Person person) {
        this.people.add(person);
        if (person != null) {
            person.setAddress(this);
        }
    }
}