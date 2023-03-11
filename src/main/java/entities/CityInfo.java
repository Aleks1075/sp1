package entities;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "CityInfo")
public class CityInfo {
    @Id
    @Column(name = "zipCode", nullable = false)
    private Integer id;

    @Column(name = "city", nullable = false)
    private String city;

    @OneToMany(mappedBy = "cityinfoZipcode")
    private Set<Address> addresses = new LinkedHashSet<>();

    public CityInfo() {
    }

    public CityInfo(Integer id, String city) {
        this.id = id;
        this.city = city;
    }

    public Set<Address> getAddresses() {
        return addresses;
    }

    public void setAddresses(Set<Address> addresses) {
        this.addresses = addresses;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}