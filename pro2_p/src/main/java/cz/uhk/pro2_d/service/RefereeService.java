package cz.uhk.pro2_d.service;

import cz.uhk.pro2_d.model.Referee;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface RefereeService {
    List<Referee> getAllReferees();
    void saveReferee(Referee referee);
    Referee getReferee(long id);
    void deleteReferee(long id);
}
