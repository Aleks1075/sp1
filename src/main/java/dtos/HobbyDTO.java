package dtos;

import entities.Hobby;

import java.util.ArrayList;
import java.util.List;

public class HobbyDTO
{
    private String name;
    private String wikiLink;
    private String category;
    private String type;

    private List<HobbyDTO> hobbies;

    public HobbyDTO(List<Hobby> hobbies) {
        this.hobbies = new ArrayList<>();
        for (Hobby hobby : hobbies) {
            this.hobbies.add(new HobbyDTO(hobby.getName(), hobby.getWikiLink(), hobby.getCategory(), hobby.getType()));
        }
    }

    public HobbyDTO(Hobby hobby)
    {
        this.name = hobby.getName();
        this.wikiLink = hobby.getWikiLink();
        this.category = hobby.getCategory();
        this.type = hobby.getType();
    }

    public static List<HobbyDTO> getHobbyDTO(List<Hobby> hobbies)
    {
        List<HobbyDTO> hobbyDTOs = new ArrayList<>();
        for (Hobby hobby : hobbies) {
            hobbyDTOs.add(new HobbyDTO(hobby));
        }
        return hobbyDTOs;
    }

    public static List<HobbyDTO> getHobbyDTO(Hobby hobbyNamehobby)
    {
        List<HobbyDTO> hobbyDTOs = new ArrayList<>();
        hobbyDTOs.add(new HobbyDTO(hobbyNamehobby));
        return hobbyDTOs;
    }

    public List<HobbyDTO> getAll() {
        return hobbies;
    }

    public void setAll(List<HobbyDTO> hobbies) {
        this.hobbies = hobbies;
    }

    public HobbyDTO() {
    }

    public HobbyDTO(String name, String wikiLink, String category, String type) {
        this.name = name;
        this.wikiLink = wikiLink;
        this.category = category;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWikiLink() {
        return wikiLink;
    }

    public void setWikiLink(String wikiLink) {
        this.wikiLink = wikiLink;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
