package cz.uhk.pro2_d.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;


@Entity
@Table(name = "courses")
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank(message = "Název kurzu nesmí být prázdný")
    private String name;

    @ManyToOne
    @NotNull(message = "Lektor musí být vybrán")
    private Lecturer lecturer;
    @ManyToOne
    @NotNull(message = "Místnost musí být vybrána")
    private Room room;


    public Lecturer getLecturer() {
        return lecturer;
    }

    public void setLecturer(Lecturer lecturer) {
        this.lecturer = lecturer;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public void setName(String name) {
        this.name = name;
    }
}
