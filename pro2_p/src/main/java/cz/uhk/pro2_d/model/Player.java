package cz.uhk.pro2_d.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "players")
public class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank(message = "Jméno nesmí být prázdné")
    private String name;

    @NotBlank(message = "Uživatelské jméno nesmí být prázdné")
    @Column(unique = true)
    private String username;

    @NotBlank(message = "Heslo nesmí být prázdné")
    private String password;

    private String role;

    @ManyToMany(mappedBy = "players")
    private Set<Match> matches = new HashSet<>();

    // --- GETTERY a SETTERY ---

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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Set<Match> getMatches() {
        return matches;
    }

    public void setMatches(Set<Match> matches) {
        this.matches = matches;
    }
}
