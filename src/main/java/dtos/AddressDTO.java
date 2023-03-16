package dtos;

import entities.Address;
import entities.CityInfo;

import java.util.List;

public class AddressDTO {
    private String id;
    private String additionalInfo;
    private String street;
    private CityInfoDTO cityInfo;

    public AddressDTO() {
    }

    public AddressDTO(String id, String additionalInfo, String street, CityInfoDTO cityInfo) {
        this.id = id;
        this.additionalInfo = additionalInfo;
        this.street = street;
        this.cityInfo = cityInfo;
    }

    public AddressDTO(String additionalInfo, String street, CityInfoDTO cityInfo) {
        this.additionalInfo = additionalInfo;
        this.street = street;
        this.cityInfo = cityInfo;
    }

    public AddressDTO(Address address)
    {
        this.id = address.getId();
        this.additionalInfo = address.getAdditionalInfo();
        this.street = address.getStreet();
        this.cityInfo = new CityInfoDTO(address.getCityinfoZipcode());
    }

    public AddressDTO(Address address, CityInfo cityInfo)
    {
        this.id = address.getId();
        this.additionalInfo = address.getAdditionalInfo();
        this.street = address.getStreet();
        this.cityInfo = new CityInfoDTO(cityInfo);
    }

    public AddressDTO(List<Address> addressEntities)
    {
        addressEntities.forEach((address) ->
        {
            this.id = address.getId();
            this.street = address.getStreet();
            this.additionalInfo = address.getAdditionalInfo();
            this.cityInfo = new CityInfoDTO(address.getCityInfo());
        });
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAdditionalInfo() {
        return additionalInfo;
    }

    public void setAdditionalInfo(String additionalInfo) {
        this.additionalInfo = additionalInfo;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public CityInfoDTO getCityInfo() {
        return cityInfo;
    }

    public void setCityInfo(CityInfoDTO cityInfo) {
        this.cityInfo = cityInfo;
    }
}