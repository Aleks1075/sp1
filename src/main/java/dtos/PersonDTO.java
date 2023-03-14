package dtos;

import entities.Person;
import entities.Phone;
import entities.Hobby;
import entities.Address;
import entities.CityInfo;

import java.util.ArrayList;
import java.util.List;

public class PersonDTO
{
    private int id;
    private String email;
    private String firstName;
    private String lastName;
    private int age;
    private List<HobbyDTO> hobbies;
    private List<PhoneDTO> phones;
    private AddressDTO address;
    private PhoneDTO phone;
    private HobbyDTO hobby;

    public PersonDTO(int id, String email, String firstName, String lastName, int age) {
        this.id = id;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
    }

    public PersonDTO(String email, String firstName, String lastName, int age)
    {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
    }

    public PersonDTO(Person person)
    {
        this.id = person.getId();
        this.email = person.getEmail();
        this.firstName = person.getFirstName();
        this.lastName = person.getLastName();
        this.age = person.getAge();
    }

    public PersonDTO(Person person, List<Hobby> hobbies, List<Phone> phones, Address address, CityInfo cityInfo)
    {
        this.id = person.getId();
        this.email = person.getEmail();
        this.firstName = person.getFirstName();
        this.lastName = person.getLastName();
        this.age = person.getAge();
        this.hobbies = HobbyDTO.getHobbyDTO(hobbies);
        this.phones = PhoneDTO.getPhoneDTO(phones);
        this.address = new AddressDTO(address, cityInfo);
    }

    public PersonDTO(String email, String firstName, String lastName, int age, HobbyDTO hobbyDTO, AddressDTO addressDTO, PhoneDTO phoneDTO)
    {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.hobbies = new ArrayList<>();
        this.hobbies.add(hobbyDTO);
        this.phones = new ArrayList<>();
        this.phones.add(phoneDTO);
        this.address = addressDTO;
    }

    public PersonDTO(List<Person> personList)
    {
        this.hobbies = new ArrayList<>();
        this.phones = new ArrayList<>();
        for (Person person : personList) {
        this.id = person.getId();
        this.email = person.getEmail();
        this.firstName = person.getFirstName();
        this.lastName = person.getLastName();
        this.age = person.getAge();
        this.hobbies.add(new HobbyDTO(person.getHobbyNamehobby()));
        this.phones.add(new PhoneDTO(person.getPhonePhonenumber()));
        this.address = new AddressDTO(person.getAddressStreet());
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public String getFirstName()
    {
        return firstName;
    }

    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
    }

    public String getLastName()
    {
        return lastName;
    }

    public void setLastName(String lastName)
    {
        this.lastName = lastName;
    }

    public int getAge()
    {
        return age;
    }

    public void setAge(int age)
    {
        this.age = age;
    }

    public List<HobbyDTO> getHobbies()
    {
        return hobbies;
    }

    public void setHobbies(List<HobbyDTO> hobbies)
    {
        this.hobbies = hobbies;
    }

    public List<PhoneDTO> getPhones()
    {
        return phones;
    }

    public void setPhones(List<PhoneDTO> phones)
    {
        this.phones = phones;
    }

    public AddressDTO getAddress()
    {
        return address;
    }

    public void setAddress(AddressDTO address)
    {
        this.address = address;
    }

    public PhoneDTO getPhone() {
        return phone;
    }

    public void setPhone(PhoneDTO phone) {
        this.phone = phone;
    }

    public HobbyDTO getHobby() {
        return hobby;
    }

    public void setHobby(HobbyDTO hobby) {
        this.hobby = hobby;
    }

    @Override
    public String toString() {
        return "PersonDTO{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", age=" + age +
                ", hobbies=" + hobbies +
                ", phones=" + phones +
                ", address=" + address +
                '}';
    }
}