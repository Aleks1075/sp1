package entities;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "Hobby")
public class Hobby {
    @Id
    @Column(name = "name", nullable = false)
    private String id;

    @Column(name = "wikiLink", nullable = false)
    private String wikiLink;

    @Column(name = "category", nullable = false, length = 45)
    private String category;

    @Column(name = "type", nullable = false, length = 45)
    private String type;

    @OneToMany(mappedBy = "hobbyNamehobby")
    private Set<Person> people = new LinkedHashSet<>();

    public Hobby() {
    }

    public Hobby(String id, String wikiLink, String category, String type) {
        this.id = id;
        this.wikiLink = wikiLink;
        this.category = category;
        this.type = type;
    }

    public Set<Person> getPeople() {
        return people;
    }

    public void setPeople(Set<Person> people) {
        this.people = people;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getWikiLink() {
        return wikiLink;
    }

    public void setWikiLink(String wikiLink) {
        this.wikiLink = wikiLink;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}