package facades;

import entities.Hobby;
import dtos.HobbyDTO;
import org.junit.jupiter.api.*;
import utils.EMF_Creator;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import static org.junit.jupiter.api.Assertions.assertEquals;

//Uncomment the line below, to temporarily disable this test
@Disabled
public class HobbyTest {

    private static EntityManagerFactory emf;
    private static HobbyFacade facade;

    public HobbyTest() {
    }

    @BeforeAll
    public static void setUpClass() {
       emf = EMF_Creator.createEntityManagerFactoryForTest();
       facade = HobbyFacade.getFacadeExample(emf);
       EntityManager em = emf.createEntityManager();

       Hobby hobby = new Hobby("3D-udskrivning","https://en.wikipedia.org/wiki/3D_printing","Generel","Indendørs");

         try {
              em.getTransaction().begin();
              em.createNamedQuery("Hobby.deleteAllRows").executeUpdate();
              em.persist(hobby);
              em.getTransaction().commit();
         } finally {
              em.close();
         }
    }

    @org.junit.jupiter.api.Test
    void getAllHobbies() {
        int actual = facade.getAllHobbies().getAll().size();
        int expected = 1;
        assertEquals(expected, actual);
    }

    @org.junit.jupiter.api.Test
    void createHobby() {
        HobbyDTO hobbyDTO = new HobbyDTO("2D-udskrivning","https://en.wikipedia.org/wiki/3D_printing","Generel","Indendørs");
        facade.createHobby(hobbyDTO);
        int expected = 2;
        int actual = facade.getAllHobbies().getAll().size();
        assertEquals(expected, actual);
    }
}
