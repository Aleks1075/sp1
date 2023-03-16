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

    public PersonDTO deletePerson(int id) {
        EntityManager em = getEntityManager();
        Person person = em.find(Person.class, id);
        if (person == null) {
            throw new IllegalArgumentException("No person found with the provided ID");
        }
        try {
            em.getTransaction().begin();
            em.remove(person);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        return new PersonDTO(person);
    }

    public List<PersonDTO> getAllPersons() {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Person> query = em.createQuery("SELECT p FROM Person p", Person.class);
            List<Person> personList = query.getResultList();
            return PersonDTO.fromPersonList(personList);
        } finally {
            em.close();
        }
    }

    // Edit person
    public PersonDTO editPerson(PersonDTO personDTO) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            Person person = em.find(Person.class, personDTO.getId());

            if (personDTO.getEmail() != null) {
                person.setEmail(personDTO.getEmail());
            }
            if (personDTO.getFirstName() != null) {
                person.setFirstName(personDTO.getFirstName());
            }
            if (personDTO.getLastName() != null) {
                person.setLastName(personDTO.getLastName());
            }
            if (personDTO.getAge() != 0) {
                person.setAge(personDTO.getAge());
            }

            if (personDTO.getAddress() != null) {
                Address address = new Address(personDTO.getAddress().getId(), personDTO.getAddress().getStreet(), personDTO.getAddress().getAdditionalInfo());
                TypedQuery<CityInfo> query = em.createQuery("SELECT c FROM CityInfo c WHERE c.zipCode = :zipCode", CityInfo.class);
                query.setParameter("zipCode", personDTO.getAddress().getCityInfo().getZipCode());
                CityInfo cityInfo = query.getSingleResult();
                address.setCityInfo(cityInfo);
                person.setAddress(address);
            }

            if (personDTO.getHobbies() != null) {
                for (HobbyDTO hobbyDTO : personDTO.getHobbies()) {
                    Hobby hobby = new Hobby(hobbyDTO.getName(), hobbyDTO.getWikiLink(), hobbyDTO.getCategory(), hobbyDTO.getType());
                    person.addHobby(hobby);
                }
            }

            if (personDTO.getPhones() != null) {
                for (PhoneDTO phoneDTO : personDTO.getPhones()) {
                    Phone phone = new Phone(phoneDTO.getId(), phoneDTO.getDescriptionPhone());
                    person.addPhone(phone);
                }
            }
            em.merge(person);
            em.getTransaction().commit();
            em.merge(person);

            return new PersonDTO(person);
        } finally {
            em.close();
        }
    }

    public PersonDTO addPerson(PersonDTO personDTO) {
        EntityManager em = emf.createEntityManager();
        Person person = new Person(personDTO.getEmail(), personDTO.getFirstName(), personDTO.getLastName(), personDTO.getAge());

        for (HobbyDTO hobbyDTO : personDTO.getHobbies()) {
            // Check if the hobby already exists in the database
            TypedQuery<Hobby> hobbyQuery = em.createQuery("SELECT h FROM Hobby h WHERE h.name = :name", Hobby.class);
            hobbyQuery.setParameter("name", hobbyDTO.getName());
            List<Hobby> existingHobbies = hobbyQuery.getResultList();

            Hobby hobby;
            if (existingHobbies.isEmpty()) {
                // If the hobby does not exist, create a new one
                hobby = new Hobby(hobbyDTO.getName(), hobbyDTO.getWikiLink(), hobbyDTO.getCategory(), hobbyDTO.getType());
            } else {
                // If the hobby exists, use the existing one
                hobby = existingHobbies.get(0);
            }

            person.setHobbyNamehobby(hobby);
        }

        Address address = new Address(personDTO.getAddress().getId(), personDTO.getAddress().getAdditionalInfo(), personDTO.getAddress().getStreet());
        TypedQuery<CityInfo> query = em.createQuery("SELECT c FROM CityInfo c WHERE c.zipCode = :zipCode", CityInfo.class);
        query.setParameter("zipCode", personDTO.getAddress().getCityInfo().getZipCode());
        CityInfo cityInfo = query.getSingleResult();
        address.setCityInfo(cityInfo);
        person.setAddressStreet(address);

        for (PhoneDTO phoneDTO : personDTO.getPhones()) {
            Phone phone = new Phone(phoneDTO.getId(), phoneDTO.getDescriptionPhone());
            person.setPhonePhonenumber(phone);
        }

        try {
            em.getTransaction().begin();
            em.persist(address); // Persist the Address object
            em.persist(person);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        return new PersonDTO(person);
    }

    public List<PersonDTO> getPersonsByHobby(String hobby) {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Person> query = em.createQuery("SELECT p FROM Person p JOIN p.hobbyNamehobby h WHERE h.name = :hobby", Person.class);
            query.setParameter("hobby", hobby);
            List<Person> persons = query.getResultList();
            return persons.stream().map(PersonDTO::new).collect(Collectors.toList());
        } finally {
            em.close();
        }
    }

    public long getNumberOfPersonsByHobby(String hobby) {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Long> query = em.createQuery("SELECT COUNT(p) FROM Person p JOIN p.hobbyNamehobby h WHERE h.name = :hobby", Long.class);
            query.setParameter("hobby", hobby);
            return query.getSingleResult();
        } finally {
            em.close();
        }
    }

    public List<PersonDTO> getPersonsByZipCode(int zipCode) {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Person> query = em.createQuery("SELECT p FROM Person p WHERE p.addressStreet.cityinfoZipcode.zipCode = :zipCode", Person.class);
            query.setParameter("zipCode", zipCode);
            List<Person> persons = query.getResultList();
            return persons.stream().map(PersonDTO::new).collect(Collectors.toList());
        } finally {
            em.close();
        }
    }
}