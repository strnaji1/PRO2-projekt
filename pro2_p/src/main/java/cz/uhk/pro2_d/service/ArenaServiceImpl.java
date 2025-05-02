package cz.uhk.pro2_d.service;

import cz.uhk.pro2_d.model.Arena;
import cz.uhk.pro2_d.repository.ArenaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArenaServiceImpl implements ArenaService {

    @Autowired
    private ArenaRepository arenaRepository;

    @Override
    public List<Arena> getAllArenas() {
        return arenaRepository.findAll();
    }

    @Override
    public Arena getArenaById(Long id) {
        return arenaRepository.findById(id).orElse(null);
    }

    @Override
    public Arena saveArena(Arena arena) {
        return arenaRepository.save(arena);
    }

    @Override
    public void deleteArena(Long id) {
        arenaRepository.deleteById(id);
    }
}
