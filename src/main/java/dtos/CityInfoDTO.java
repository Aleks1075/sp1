package dtos;

import entities.CityInfo;

import java.util.ArrayList;
import java.util.List;

public class CityInfoDTO
{
    private int zipCode;
    private String city;

    List<CityInfoDTO> all = new ArrayList();

    public CityInfoDTO(List<CityInfo> cityInfoEntities) {
        cityInfoEntities.forEach(cityInfo -> {
            this.zipCode = cityInfo.getId();
            this.city = cityInfo.getCity();
        });
    }

    public CityInfoDTO(int zipCode, String city)
    {
        this.zipCode = zipCode;
        this.city = city;
    }

    public CityInfoDTO(CityInfo cityInfo)
    {
        this.zipCode = cityInfo.getId();
        this.city = cityInfo.getCity();
    }

    public int getZipCode()
    {
        return zipCode;
    }

    public void setZipCode(int zipCode)
    {
        this.zipCode = zipCode;
    }

    public String getCity()
    {
        return city;
    }

    public void setCity(String city)
    {
        this.city = city;
    }

    public int getSize() {
        int counter = 0;
        for (CityInfoDTO p : all) {
            counter++;
        }
        return counter;
    }
}