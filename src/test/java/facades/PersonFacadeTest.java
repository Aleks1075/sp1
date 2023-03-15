package facades;

import dtos.*;
import entities.Address;
import entities.CityInfo;
import entities.Hobby;
import entities.Person;
import entities.Phone;
import entities.*;
import org.junit.jupiter.api.*;
import utils.EMF_Creator;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

//Uncomment the line below, to temporarily disable this test
@Disabled
public class PersonTest {

    private static EntityManagerFactory emf;
    private static PersonFacade facade;
    private static Person p1;
    private static Person p2;
    PersonDTO p;

    public PersonTest() {
    }

    @BeforeAll
    public static void setUpClass() {
       emf = EMF_Creator.createEntityManagerFactoryForTest();
       facade = PersonFacade.getFacadeExample(emf);
    }

    @AfterAll
    public static void tearDownClass() {
//        Clean up database after test is done or use a persistence unit with drop-and-create to start up clean on every test
    }

    // Setup the DataBase in a known state BEFORE EACH TEST
    //TODO -- Make sure to change the code below to use YOUR OWN entity class
    @BeforeEach
    public void setUp() {

        CityInfo c1 = new CityInfo(2300, "København S");
        Address a1 = new Address("Testvej 1", "test", "test", c1);
        Phone ph1 = new Phone("12345678", "testPhone");
        Phone ph2 = new Phone("87654321", "testPhone2");
        Hobby h1 = new Hobby("1D-udskrivning","https://en.wikipedia.org/wiki/3D_printing","Generel","Indendørs");
        Hobby h2 = new Hobby("2D-udskrivning","https://en.wikipedia.org/wiki/3D_printing","Generel","Indendørs");

        EntityManager em = emf.createEntityManager();
        p1 = new Person("test@email.dk", "Hans", "Hansen", 20);
        a1.setCityinfoZipcode(c1);
        p1.setAddressStreet(a1);
        p1.setPhonePhonenumber(ph1);
        p1.setHobbyNamehobby(h1);

        // Insert a new Person entity with a different ID
        p2 = new Person("test2@email.dk", "Ole", "Olsen", 21);
        p2.setAddressStreet(a1);
        p2.setPhonePhonenumber(ph2);
        p2.setHobbyNamehobby(h2);

        try {
            em.getTransaction().begin();
            em.createNamedQuery("Person.deleteAllRows").executeUpdate();
            em.createNamedQuery("Address.deleteAllRows").executeUpdate();
            em.createNamedQuery("CityInfo.deleteAllRows").executeUpdate();
            em.createNamedQuery("Phone.deleteAllRows").executeUpdate();
            em.createNamedQuery("Hobby.deleteAllRows").executeUpdate();
            em.persist(c1);
            em.persist(a1);
            em.persist(ph1);
            em.persist(h1);
            em.persist(ph2);

            em.persist(p1);
            em.persist(p2);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    @Test
    public void getPersonByIdTest() {
        String expected = "Hans";
        int id = p1.getId(); // Use ID of p1
        String actual = facade.getPersonById(id).getFirstName();
        assertEquals(expected, actual);
    }

    @Test
    public void getPersonByPhoneTest() {
        String expected = "Hans";
        String phone = p1.getPhonePhonenumber().getId(); // Use ID of p1
        String actual = facade.getPersonByPhoneNumber(phone).getFirstName();
        assertEquals(expected, actual);
    }

//    @Test
//    void createPersonTest() {
//        p = new PersonDTO("test@hotmail.com", "Mikkel", "Hansen", 20);
//
//        //add address
//        CityInfoDTO c = new CityInfoDTO(2500, "Valby");
//        AddressDTO a = new AddressDTO("Valbyvej 20", "test", "test", c);
//
//        //add phone
//        PhoneDTO ph = new PhoneDTO("93601903", "home");
//
//        //add hobby
//        HobbyDTO h = new HobbyDTO("3D-udskrivning","https://en.wikipedia.org/wiki/3D_printing","Generel","Indendørs");
//
//        p.setAddress(a);
//        p.setPhone(ph);
//        p.setHobby(h);
//
//        PersonDTO result = facade.createPerson(p);
//        assertEquals(p.getEmail(), result.getEmail());
//        System.out.println(result);
//    }

    @Test
    public void getPersonByHobbyTest() {
        String expected = "Hans";
        String hobby = p1.getHobbyNamehobby().getName(); // Use ID of p1
        String actual = facade.getPersonByHobby(hobby).getFirstName();
        assertEquals(expected, actual);
        System.out.println("Hobby: " + hobby + ", hører til " + actual);
    }

//    @Test
//    public void deletePersonTest() {
//        String expected = "Hans";
//        int id = p1.getId(); // Use ID of p1
//        String actual = facade.deletePerson(id).getFirstName();
//        assertEquals(expected, actual);
//    }

//    @Test
//    public void editPersonTest() {
//        p2.setFirstName("Mikkel");
//        p2.setLastName("Jakobsen");
//        p2.setEmail("edit@test.dk");
//        p2.setPhone(new Phone("12345678", "editPhone"));
//        p2.setHobby(new Hobby("4D-udskrivning","https://en.wikipedia.org/wiki/3D_printing","Generel","Indendørs"));
//        p2.setAddress(new Address("Testvej 2", "test", "test", new CityInfo(2100, "København Ø")));
//        PersonDTO editPerson = new PersonDTO(p2);
//        facade.editPerson(editPerson);
//
//        PersonDTO updatedPerson = facade.getPersonById(p2.getId());
//        assertEquals("Mikkel", updatedPerson.getFirstName());
//        assertEquals("Jakobsen", updatedPerson.getLastName());
//        assertEquals("edit@test.dk", updatedPerson.getEmail());
//        assertEquals("12345678", updatedPerson.getPhone().getId());
//        assertEquals("editPhone", updatedPerson.getPhone().getDescriptionPhone());
//        assertEquals("4D-udskrivning", updatedPerson.getHobby().getName());
//        assertEquals("https://en.wikipedia.org/wiki/3D_printing", updatedPerson.getHobby().getWikiLink());
//        assertEquals("Generel", updatedPerson.getHobby().getCategory());
//        assertEquals("Indendørs", updatedPerson.getHobby().getType());
//        assertEquals("Testvej 2", updatedPerson.getAddress().getStreet());
//        assertEquals("test", updatedPerson.getAddress().getAdditionalInfo());
//        assertEquals("test", updatedPerson.getAddress().getCityInfo().getCity());
//        assertEquals(2100, updatedPerson.getAddress().getCityInfo().getZipCode());
//        assertEquals("København Ø", updatedPerson.getAddress().getCityInfo().getCity());
//    }
}
