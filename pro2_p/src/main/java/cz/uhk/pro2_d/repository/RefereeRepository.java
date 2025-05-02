package cz.uhk.pro2_d.repository;

import cz.uhk.pro2_d.model.Referee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RefereeRepository extends JpaRepository<Referee, Long> {

}
