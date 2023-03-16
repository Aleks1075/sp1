package dtos;

import entities.Phone;

import java.util.ArrayList;
import java.util.List;

public class PhoneDTO
{
    private String id;
    private String descriptionPhone;

    public PhoneDTO(String id, String descriptionPhone)
    {
        this.id = id;
        this.descriptionPhone = descriptionPhone;
    }

    public PhoneDTO(Phone phone)
    {
        this.id = phone.getId();
        this.descriptionPhone = phone.getDescriptionPhone();
    }

    public static List<PhoneDTO> getPhoneDTO(List<Phone> phones)
    {
        List<PhoneDTO> phoneDTOs = new ArrayList<>();
        for (Phone phone : phones)
        {
            phoneDTOs.add(new PhoneDTO(phone));
        }
        return phoneDTOs;
    }

    public static List<PhoneDTO> getPhoneDTO(Phone phonePhonenumber)
    {
        List<PhoneDTO> phoneDTOs = new ArrayList<>();
        phoneDTOs.add(new PhoneDTO(phonePhonenumber));
        return phoneDTOs;
    }

    public String getDescriptionPhone()
    {
        return descriptionPhone;
    }

    public void setDescriptionPhone(String descriptionPhone)
    {
        this.descriptionPhone = descriptionPhone;
    }

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }
}
