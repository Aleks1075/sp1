package facades;

import dtos.AddressDTO;
import dtos.CityInfoDTO;
import entities.Address;
import entities.CityInfo;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import java.util.List;

public class AddressFacade
{
    private static AddressFacade instance;
    private static EntityManagerFactory emf;

    //Private Constructor to ensure Singleton
    private AddressFacade() {}

    /**
     *
     * @param _emf
     * @return an instance of this facade class.
     */
    public static AddressFacade getAddressFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new AddressFacade();
        }
        return instance;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public AddressDTO getAllAddresses() {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Address> query = em.createQuery("SELECT a FROM Address a", Address.class);
            List<Address> address = query.getResultList();
            return new AddressDTO(address);
        } finally {
            em.close();
        }
    }

    public CityInfoDTO getAddressByName(String adress)
    {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Address> query = em.createQuery("SELECT a FROM Address a WHERE a.id = :adress", Address.class);
            query.setParameter("adress", adress);
            Address address = query.getSingleResult();
            return new CityInfoDTO(address.getCityInfo());
        } finally {
            em.close();
        }
    }

    public CityInfoDTO getAddressByZipCode(int zipCode)
    {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Address> query = em.createQuery("SELECT a FROM Address a WHERE a.cityinfoZipcode.zipCode = :CityInfo_zipCode", Address.class);
            query.setParameter("CityInfo_zipCode", zipCode);
            Address address = query.getSingleResult();
            return new CityInfoDTO(address.getCityInfo());
        } finally {
            em.close();
        }
    }

    public AddressDTO addAddress(AddressDTO addressDTO) {
        EntityManager em = emf.createEntityManager();
        Address address = new Address(addressDTO.getId(), addressDTO.getStreet(), addressDTO.getAdditionalInfo());
        TypedQuery<CityInfo> query = em.createQuery("SELECT c FROM CityInfo c WHERE c.zipCode = :zipCode", CityInfo.class);
        query.setParameter("zipCode", addressDTO.getCityInfo().getZipCode());
        CityInfo cityInfo = query.getSingleResult();
        address.setCityInfo(cityInfo);
        try {
            em.getTransaction().begin();
            em.persist(address);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        return new AddressDTO(address);
    }
}
