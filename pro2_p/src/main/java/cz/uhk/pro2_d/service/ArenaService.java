package cz.uhk.pro2_d.service;

import cz.uhk.pro2_d.model.Arena;

import java.util.List;

public interface ArenaService {
    List<Arena> getAllArenas();
    Arena getArenaById(Long id);
    Arena saveArena(Arena arena);
    void deleteArena(Long id);
}
