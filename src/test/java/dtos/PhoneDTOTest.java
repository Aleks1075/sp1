package dtos;

import entities.Phone;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.List;

public class PhoneDTOTest {

    @Test
    @DisplayName("Test getPhoneDTO() metoden")
    public void testGetPhoneDTO() {

        Phone phone1 = new Phone("12345678", "Hjem");
        Phone phone2 = new Phone("98765432", "Arbejde");
        List<Phone> phones = new ArrayList<>();
        phones.add(phone1);
        phones.add(phone2);

        List<PhoneDTO> phoneDTOs = PhoneDTO.getPhoneDTO(phones);

        Assertions.assertEquals("12345678", phoneDTOs.get(0).getId());
        Assertions.assertEquals("Hjem", phoneDTOs.get(0).getDescriptionPhone());
        Assertions.assertEquals("98765432", phoneDTOs.get(1).getId());
        Assertions.assertEquals("Arbejde", phoneDTOs.get(1).getDescriptionPhone());
    }

    @Test
    @DisplayName("Test PhoneDTO getID og getDescriptionPhone metoderne")
    public void testPhoneDTOGettersAndSetters() {

        Phone phone = new Phone("12345678", "Hjem");

        PhoneDTO phoneDTO = new PhoneDTO(phone);

        Assertions.assertEquals("12345678", phoneDTO.getId());
        Assertions.assertEquals("Hjem", phoneDTO.getDescriptionPhone());
    }

    @Test
    @DisplayName("Test getPhoneDTO() metoden med en tom liste")
    public void testGetPhoneDTOWithEmptyList() {

        List<Phone>
        phones = new ArrayList<>();

        List<PhoneDTO> phoneDTOs = PhoneDTO.getPhoneDTO(phones);

        Assertions.assertEquals(0, phoneDTOs.size());
    }
}

