package entities;

import javax.persistence.*;

@Entity
@Table(name = "Person")
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "firstName", nullable = false)
    private String firstName;

    @Column(name = "lastName", nullable = false)
    private String lastName;

    @Column(name = "age", nullable = false)
    private Integer age;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "Hobby_nameHobby", nullable = false)
    private Hobby hobbyNamehobby;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "Address_street", nullable = false)
    private Address addressStreet;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "Phone_PhoneNumber", nullable = false)
    private Phone phonePhonenumber;

    public Person() {
    }

    public Person(String email, String firstName, String lastName, Integer age, Hobby hobbyNamehobby, Address addressStreet, Phone phonePhonenumber) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.hobbyNamehobby = hobbyNamehobby;
        this.addressStreet = addressStreet;
        this.phonePhonenumber = phonePhonenumber;
    }

    public Hobby getHobbyNamehobby() {
        return hobbyNamehobby;
    }

    public void setHobbyNamehobby(Hobby hobbyNamehobby) {
        this.hobbyNamehobby = hobbyNamehobby;
    }

    public Phone getPhonePhonenumber() {
        return phonePhonenumber;
    }

    public void setPhonePhonenumber(Phone phonePhonenumber) {
        this.phonePhonenumber = phonePhonenumber;
    }

    public Address getAddressStreet() {
        return addressStreet;
    }

    public void setAddressStreet(Address addressStreet) {
        this.addressStreet = addressStreet;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}