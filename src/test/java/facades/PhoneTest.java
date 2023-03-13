package facades;

import dtos.PhoneDTO;
import entities.Phone;
import entities.RenameMe;
import org.junit.jupiter.api.*;
import utils.EMF_Creator;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

//Uncomment the line below, to temporarily disable this test
//@Disabled
public class PhoneTest {

    private static EntityManagerFactory emf;
    private static PhoneFacade facade;
    private static Phone p1;
    private static Phone p2;
    private static Phone p3;

    public PhoneTest() {
    }

    @BeforeAll
    public static void setUpClass() {
       emf = EMF_Creator.createEntityManagerFactoryForTest();
       facade = PhoneFacade.getFacadeExample(emf);
    }

    @AfterAll
    public static void tearDownClass() {
//        Clean up database after test is done or use a persistence unit with drop-and-create to start up clean on every test
    }

    // Setup the DataBase in a known state BEFORE EACH TEST
    //TODO -- Make sure to change the code below to use YOUR OWN entity class
    @BeforeEach
    public void setUp() {
        EntityManager em = emf.createEntityManager();
        p1 = new Phone("12345678", "Iphone");
        p2 = new Phone("64738291", "Android");
        p3 = new Phone("87654321", "Nokia");

        try {
            em.getTransaction().begin();
            em.createNamedQuery("Phone.deleteAllRows").executeUpdate();
            em.persist(p1);
            em.persist(p2);
            em.persist(p3);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    @Test
    public void getAllPhonesTest() {
        List<PhoneDTO> phones = facade.getAllPhones();
        assertEquals(3, phones.size());

        PhoneDTO phone1 = phones.get(0);
        assertEquals(p1.getId(), phone1.getId());
        assertEquals(p1.getDescriptionPhone(), phone1.getDescriptionPhone());

        PhoneDTO phone2 = phones.get(1);
        assertEquals(p2.getId(), phone2.getId());
        assertEquals(p2.getDescriptionPhone(), phone2.getDescriptionPhone());

        PhoneDTO phone3 = phones.get(2);
        assertEquals(p3.getId(), phone3.getId());
        assertEquals(p3.getDescriptionPhone(), phone3.getDescriptionPhone());
    }
}
