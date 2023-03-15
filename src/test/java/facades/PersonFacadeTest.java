package facades;

import dtos.PersonDTO;
import entities.Person;
import org.junit.jupiter.api.*;
import utils.EMF_Creator;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class PersonFacadeTest {

    private static EntityManagerFactory emf;
    private static PersonFacade facade;

    @BeforeAll
    static void setUpClass() {
        emf = EMF_Creator.createEntityManagerFactoryForTest();
        facade = PersonFacade.getFacadeExample(emf);
    }

    @BeforeEach
    void setUp() {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.createQuery("DELETE FROM Person").executeUpdate();
        em.getTransaction().commit();

        em.getTransaction().begin();

        //Tilføj personer til databasen
        Person person1 = new Person("john@mail.com", "John", "Doe", 30);
        Person person2 = new Person("jane@mail.com", "Jane", "Doe", 25);
        em.persist(person1);
        em.persist(person2);
        em.getTransaction().commit();

        em.close();
    }

    @AfterAll
    static void tearDownClass() {
        EMF_Creator.shutdown();
    }

    @Test
    void getPersonById() {
        int id = 1;
        PersonDTO result = facade.getPersonById(id);

        assertNotNull(result);
        assertEquals("john@mail.com", result.getEmail());
        assertEquals("John", result.getFirstName());
        assertEquals("Doe", result.getLastName());
        assertEquals(30, result.getAge());
    }

    @Test
    void getPersonByPhoneNumber() {
    }

    @Test
    void getPersonByHobby() {
    }

    @Test
    void deletePerson() {
        int id = 1;
        PersonDTO deleted = facade.deletePerson(id);

        assertNotNull(deleted);
        assertEquals("john@mail.com", deleted.getEmail());
        assertEquals("John", deleted.getFirstName());
        assertEquals("Doe", deleted.getLastName());
        assertEquals(30, deleted.getAge());

        // Tjek at personen er blevet slettet fra databasen
        EntityManager em = emf.createEntityManager();
        List<Person> persons = em.createQuery("SELECT p FROM Person p WHERE p.id = :id", Person.class)
                .setParameter("id", id)
                .getResultList();

        assertEquals(0, persons.size());
        em.close();
    }
}
