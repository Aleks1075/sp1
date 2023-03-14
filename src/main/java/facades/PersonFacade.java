package facades;

import dtos.*;
import entities.*;
import utils.EMF_Creator;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.WebApplicationException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * Rename Class to a relevant name Add add relevant facade methods
 */
public class PersonFacade {

    private static PersonFacade instance;
    private static EntityManagerFactory emf;

    //Private Constructor to ensure Singleton
    private PersonFacade() {
    }


    /**
     * @param _emf
     * @return an instance of this facade class.
     */
    public static PersonFacade getFacadeExample(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new PersonFacade();
        }
        return instance;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public PersonDTO getPersonById(int id) {
        EntityManager em = emf.createEntityManager();
        try {
            Person person = em.find(Person.class, id);
            return new PersonDTO(person);
        } finally {
            em.close();
        }
    }

    public PersonDTO getPersonByPhoneNumber(String phoneNumber) {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<PersonDTO> query = em.createQuery("SELECT NEW dtos.PersonDTO(p) FROM Person p WHERE p.phonePhonenumber.id = :phoneNumber", PersonDTO.class);
            query.setParameter("phoneNumber", phoneNumber);
            return query.getSingleResult();
        } finally {
            em.close();
        }
    }

    public PersonDTO createPerson(PersonDTO personDTO) {
        EntityManager em = getEntityManager();
        Person person = new Person(personDTO.getEmail(), personDTO.getFirstName(), personDTO.getLastName(), personDTO.getAge());
        try {
            //add hobby
            for(HobbyDTO hobbyDTO : personDTO.getHobbies()) {
                TypedQuery<Hobby> query = em.createQuery("SELECT h FROM Hobby h WHERE h.name = :name", Hobby.class);
                query.setParameter("name", hobbyDTO.getName());
                Hobby hobby = query.getSingleResult();
                person.addHobby(hobby);
            }

            //add address
            Address address = new Address(personDTO.getAddress().getId(), personDTO.getAddress().getAdditionalInfo(), personDTO.getAddress().getStreet());
            TypedQuery<CityInfo> query = em.createQuery("SELECT c FROM CityInfo c WHERE c.zipCode = :zipCode", CityInfo.class);
            query.setParameter("zipCode", personDTO.getAddress().getCityInfo().getZipCode());
            CityInfo cityInfo = query.getSingleResult();
            address.setCityInfo(cityInfo);
            person.setAddress(address);

            //add phone
            for(PhoneDTO phoneDTO : personDTO.getPhones()) {
                Phone phone = new Phone(phoneDTO.getId(), phoneDTO.getDescriptionPhone());
                person.addPhone(phone);
            }

            em.getTransaction().begin();
            em.persist(person);
            em.getTransaction().commit();
            return new PersonDTO(person);
        } finally {
            em.close();
        }
    }

    public Person getPersonByHobby(String name) {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<Person> query = em.createQuery("SELECT p FROM Person p JOIN p.hobbyNamehobby h WHERE h.name = :name", Person.class);
            query.setParameter("name", name);
            return query.getSingleResult();
        } finally {
            em.close();
        }
    }

    public Person deletePerson(int id) {
        EntityManager em = getEntityManager();
        try {
            Person person = em.find(Person.class, id);
            em.getTransaction().begin();
            em.remove(person);
            em.getTransaction().commit();
            return person;
        } finally {
            em.close();
        }
    }

    public PersonDTO editPerson(PersonDTO personDTO) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            Person person = em.find(Person.class, personDTO.getId());
            if (person == null) {
                throw new NotFoundException("Person with ID " + personDTO.getId() + " not found.");
            }
            person.setFirstName(personDTO.getFirstName());
            person.setLastName(personDTO.getLastName());
            person.setEmail(personDTO.getEmail());

            if (personDTO.getAddress() != null) {
                person.setAddress(new Address(personDTO.getAddress().getId(), personDTO.getAddress().getAdditionalInfo(), personDTO.getAddress().getStreet(),
                        new CityInfo(personDTO.getAddress().getCityInfo().getZipCode(), personDTO.getAddress().getCityInfo().getCity())));
            }

            if (personDTO.getHobby() != null) {
                person.setHobby(new Hobby(personDTO.getHobby().getName(), personDTO.getHobby().getWikiLink(), personDTO.getHobby().getCategory(), personDTO.getHobby().getType()));
            }

            if (personDTO.getPhone() != null) {
                person.setPhone(new Phone(personDTO.getPhone().getId(), personDTO.getPhone().getDescriptionPhone()));
            }

            em.getTransaction().commit();
            em.merge(person);

            return new PersonDTO(person);
        } finally {
            em.close();
        }
    }

    public PersonDTO getAllPersons() {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Person> query = em.createQuery("SELECT p FROM Person p", Person.class);
            List<Person> personList = query.getResultList();
            return new PersonDTO(personList);
        } finally {
            em.close();
        }
    }
}
