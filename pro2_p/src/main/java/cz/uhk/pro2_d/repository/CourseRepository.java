package cz.uhk.pro2_d.repository;

import cz.uhk.pro2_d.model.Course;
import cz.uhk.pro2_d.model.Room;
import cz.uhk.pro2_d.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
    List<Course> findAllByRoom(Room room);
}
