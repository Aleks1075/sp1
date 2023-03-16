package entities;

import dtos.PhoneDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.LinkedHashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PhoneTest {

    private Phone phone;
    private PhoneDTO phoneDTO;
    private Set<Person> people;

    @BeforeEach
    void setUp() {
        people = new LinkedHashSet<>();
        phone = new Phone("12345678", "Hjem");
        phoneDTO = new PhoneDTO("12345678", "Hjem");
    }

    @Test
    @DisplayName("getId burde returnere korrekt id")
    void getId_shouldReturnCorrectId() {
        assertEquals("12345678", phone.getId());
    }

    @Test
    @DisplayName("getId burde opdatere id")
    void setId_shouldUpdateId() {
        phone.setId("98765432");
        assertEquals("98765432", phone.getId());
    }

    @Test
    @DisplayName("getDescriptionPhone burde returnere korrekt beskrivelse")
    void getDescriptionPhone_shouldReturnCorrectDescription() {
        assertEquals("Hjem", phone.getDescriptionPhone());
    }

    @Test
    @DisplayName("setDescriptionPhone burde opdatere beskrivelse")
    void setDescriptionPhone_shouldUpdateDescription() {
        phone.setDescriptionPhone("Arbejde");
        assertEquals("Arbejde", phone.getDescriptionPhone());
    }

    @Test
    @DisplayName("getPeople skal returnere et tomt sæt som standard")
    void getPeople_shouldReturnEmptySetByDefault() {
        assertEquals(0, phone.getPeople().size());
    }

    @Test
    @DisplayName("setPeople burde opdatere people")
    void setPeople_shouldUpdatePeople() {
        Person person = new Person();
        people.add(person);
        phone.setPeople(people);
        assertEquals(people, phone.getPeople());
    }
}