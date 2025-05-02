package cz.uhk.pro2_d.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Min;

@Entity
@Table(name = "matches")
public class Match {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank(message = "Název zápasu nesmí být prázdný")
    private String name;

    @ManyToOne
    @NotNull(message = "Rozhodčí musí být vybrán")
    private Referee referee;

    @ManyToOne
    @NotNull(message = "Aréna musí být vybrána")
    private Arena arena;

    @Min(value = 1, message = "Počet hráčů musí být alespoň 1")
    private int participants;

    // Gettery a settery

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

    public Referee getReferee() {
        return referee;
    }

    public void setReferee(Referee referee) {
        this.referee = referee;
    }

    public Arena getArena() {
        return arena;
    }

    public void setArena(Arena arena) {
        this.arena = arena;
    }

    public int getParticipants() {
        return participants;
    }

    public void setParticipants(int participants) {
        this.participants = participants;
    }
}
