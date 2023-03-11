package facades;

import dtos.HobbyDTO;
import dtos.PersonDTO;
import dtos.PhoneDTO;
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

import static org.junit.jupiter.api.Assertions.*;

//Uncomment the line below, to temporarily disable this test
//@Disabled
public class PersonTest {

    private static EntityManagerFactory emf;
    private static PersonFacade facade;
    private static Person p1;

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
        Hobby h1 = new Hobby("1D-udskrivning","https://en.wikipedia.org/wiki/3D_printing","Generel","Indendørs");

        EntityManager em = emf.createEntityManager();
        p1 = new Person("test@email.dk", "Hans", "Hansen", 20);
        a1.setCityinfoZipcode(c1);
        p1.setAddressStreet(a1);
        p1.setPhonePhonenumber(ph1);
        p1.setHobbyNamehobby(h1);

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

            // Insert a new Person entity with a different ID
            Person p2 = new Person("test2@email.dk", "Ole", "Olsen", 21);
            p2.setAddressStreet(a1);
            p2.setPhonePhonenumber(ph1);
            p2.setHobbyNamehobby(h1);

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
}
//test