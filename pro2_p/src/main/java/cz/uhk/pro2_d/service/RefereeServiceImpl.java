package cz.uhk.pro2_d.service;

import cz.uhk.pro2_d.model.Referee;
import cz.uhk.pro2_d.repository.RefereeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RefereeServiceImpl implements RefereeService {

    private final RefereeRepository refereeRepository;

    @Autowired
    public RefereeServiceImpl(RefereeRepository refereeRepository) {
        this.refereeRepository = refereeRepository;
    }

    @Override
    public List<Referee> getAllReferees() {
        return refereeRepository.findAll();
    }

    @Override
    public void saveReferee(Referee referee) {
        refereeRepository.save(referee);
    }

    @Override
    public Referee getReferee(long id) {
        return refereeRepository.findById(id).orElse(null);
    }

    @Override
    public void deleteReferee(long id) {
        refereeRepository.deleteById(id);
    }
}
