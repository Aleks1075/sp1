package facades;

import entities.CityInfo;
import org.junit.jupiter.api.*;
import utils.EMF_Creator;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import static org.junit.jupiter.api.Assertions.*;

//Uncomment the line below, to temporarily disable this test
//@Disabled
public class CityInfoTest {

    private static EntityManagerFactory emf;
    private static CityInfoFacade facade;
    private static CityInfo cityInfo;

    public CityInfoTest() {
    }

    @BeforeAll
    public static void setUpClass() {
       emf = EMF_Creator.createEntityManagerFactoryForTest();
       facade = CityInfoFacade.getFacadeExample(emf);
    }

    @AfterAll
    public static void tearDownClass() {
//        Clean up database after test is done or use a persistence unit with drop-and-create to start up clean on every test
    }

    // Setup the DataBase in a known state BEFORE EACH TEST
    //TODO -- Make sure to change the code below to use YOUR OWN entity class
    @BeforeEach
    public void setUp() {

        cityInfo = new CityInfo(2500, "Valby");

        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.createNamedQuery("CityInfo.deleteAllRows").executeUpdate();
            em.persist(cityInfo);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    @AfterEach
    public void tearDown() {
//        Remove any data after each test was run
    }

    @Test
    public void getAllCityInfoTest() {
        int expected = 1;
        int actual = facade.getAllCityInfo().getSize();
        assertEquals(expected, actual);
        System.out.println("Expected: " + expected + " Actual: " + actual);
    }

    @Test
    public void getCityByIdTest() {
        int expected = 2500;
        int actual = facade.getCityByZipCode(cityInfo.getId()).getZipCode();
        assertEquals(expected, actual);
    }

    @Test
    public void getCityByNameTest() {
        String expected = "Valby";
        String actual = facade.getCityByName("Valby").getCity();
        assertEquals(expected, actual);
        System.out.println("Expected: " + expected + " Actual: " + actual);
    }
}
