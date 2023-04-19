package facades;

import dtos.PhoneDTO;
import entities.Phone;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import javax.ws.rs.NotFoundException;
import java.util.ArrayList;
import java.util.List;

public class PhoneFacade {

    private static PhoneFacade instance;

    private static EntityManagerFactory emf;

    private PhoneFacade() {
    }

    public static PhoneFacade getFacadeExample(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new PhoneFacade();
        }
        return instance;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public List<PhoneDTO> getAllPhones() {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Phone> query = em.createQuery("SELECT p FROM Phone p ORDER BY p.id", Phone.class);
            List<Phone> phones = query.getResultList();
            List<PhoneDTO> phoneDTOs = new ArrayList<>();
            for (Phone phone : phones) {
                phoneDTOs.add(new PhoneDTO(phone));
            }
            return phoneDTOs;
        } finally {
            em.close();
        }
    }

    public PhoneDTO getPhoneByNumber(String phoneNumber)
    {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Phone> query = em.createQuery("SELECT p FROM Phone p WHERE p.id = :PhoneNumber", Phone.class);
            query.setParameter("PhoneNumber", phoneNumber);
            Phone phone = query.getSingleResult();
            return new PhoneDTO(phone);
        } catch (Exception e) {
            throw new NotFoundException("Phone with number " + phoneNumber + " not found");
        } finally {
            em.close();
        }
    }

    public PhoneDTO getPhoneByDescription(String description)
    {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Phone> query = em.createQuery("SELECT p FROM Phone p WHERE p.descriptionPhone = :descriptionPhone", Phone.class);
            query.setParameter("descriptionPhone", description);
            Phone phone = query.getSingleResult();
            return new PhoneDTO(phone);
        } catch (Exception e) {
            throw new NotFoundException("Phone with description " + description + " not found");
        } finally {
            em.close();
        }
    }

    public PhoneDTO addPhone(PhoneDTO phoneDTO)
    {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            Phone phone = new Phone(phoneDTO.getId(), phoneDTO.getDescriptionPhone());
            em.persist(phone);
            em.getTransaction().commit();
            return new PhoneDTO(phone);
        } finally {
            em.close();
        }
    }

    public PhoneDTO editPhone(PhoneDTO phoneDTO) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            Phone phone = em.find(Phone.class, phoneDTO.getId());
            if (phone == null) {
                throw new NotFoundException("Phone with number: " + phoneDTO.getId() + " not found");
            }
            phone.setId(phoneDTO.getId());
            phone.setDescriptionPhone(phoneDTO.getDescriptionPhone());
            em.getTransaction().commit();
            return new PhoneDTO(phone);
        } finally {
            em.close();
        }
    }
}