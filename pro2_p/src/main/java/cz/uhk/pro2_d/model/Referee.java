package cz.uhk.pro2_d.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "referees")
public class Referee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank(message = "Jméno rozhodčího nesmí být prázdné")
    private String name;

    @OneToMany(mappedBy = "referee")
    private List<Match> matches;

    // --- Gettery a settery ---

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Match> getMatches() {
        return matches;
    }

    public void setMatches(List<Match> matches) {
        this.matches = matches;
    }

    // --- equals(), hashCode(), toString() ---

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Referee)) return false;
        Referee referee = (Referee) o;
        return id == referee.id;
    }

    @Override
    public int hashCode() {
        return Long.hashCode(id);
    }

    @Override
    public String toString() {
        return "Referee{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
