package facades;

import dtos.CityInfoDTO;
import dtos.HobbyDTO;
import entities.CityInfo;
import entities.Hobby;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import javax.ws.rs.WebApplicationException;
import java.util.List;

/**
 *
 * Rename Class to a relevant name Add add relevant facade methods
 */
public class HobbyFacade {

    private static HobbyFacade instance;
    private static EntityManagerFactory emf;

    //Private Constructor to ensure Singleton
    private HobbyFacade() {}
    
    
    /**
     * 
     * @param _emf
     * @return an instance of this facade class.
     */
    public static HobbyFacade getFacadeExample(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new HobbyFacade();
        }
        return instance;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public HobbyDTO getAllHobbies() {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Hobby> query = em.createQuery("SELECT h FROM Hobby h", Hobby.class);
            List<Hobby> hobbies = query.getResultList();
            if(hobbies == null) {
                throw new WebApplicationException("No hobbies found");
            }
            return new HobbyDTO(hobbies);
        } finally {
            em.close();
        }
    }

    public HobbyDTO createHobby(HobbyDTO hobbyDTO) throws WebApplicationException {
        EntityManager em = emf.createEntityManager();
        Hobby hobby = new Hobby(hobbyDTO.getName(), hobbyDTO.getWikiLink(), hobbyDTO.getCategory(), hobbyDTO.getType());

        if ((hobby.getName().length() == 0)) {
            throw new WebApplicationException("Name is missing", 400);
        }
        try {
            em.getTransaction().begin();
            em.persist(hobby);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        return new HobbyDTO(hobby.getName(), hobby.getWikiLink(), hobby.getCategory(), hobby.getType());
    }
}
