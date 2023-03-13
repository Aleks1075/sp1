package facades;

import dtos.CityInfoDTO;
import dtos.RenameMeDTO;
import entities.CityInfo;
import entities.RenameMe;
import utils.EMF_Creator;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 *
 * Rename Class to a relevant name Add add relevant facade methods
 */
public class CityInfoFacade {

    private static CityInfoFacade instance;
    private static EntityManagerFactory emf;

    //Private Constructor to ensure Singleton
    private CityInfoFacade() {}
    
    
    /**
     * 
     * @param _emf
     * @return an instance of this facade class.
     */
    public static CityInfoFacade getFacadeExample(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new CityInfoFacade();
        }
        return instance;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public CityInfoDTO getAllCityInfo() {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<CityInfo> query = em.createQuery("SELECT c FROM CityInfo c", CityInfo.class);
            List<CityInfo> cityInfo = query.getResultList();
            return new CityInfoDTO(cityInfo);
        } finally {
            em.close();
        }
    }

    public CityInfoDTO getCityByZipCode(int zipCode) {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<CityInfo> query = em.createQuery("SELECT c FROM CityInfo c WHERE c.zipCode = :zipCode", CityInfo.class);
            query.setParameter("zipCode", zipCode);
            CityInfo cityInfo = query.getSingleResult();
            return new CityInfoDTO(cityInfo);
        } finally {
            em.close();
        }
    }

    public CityInfoDTO getCityByName(String city) {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<CityInfo> query = em.createQuery("SELECT c FROM CityInfo c WHERE c.city = :city", CityInfo.class);
            query.setParameter("city", city);
            CityInfo cityInfo = query.getSingleResult();
            return new CityInfoDTO(cityInfo);
        } finally {
            em.close();
        }
    }
}
