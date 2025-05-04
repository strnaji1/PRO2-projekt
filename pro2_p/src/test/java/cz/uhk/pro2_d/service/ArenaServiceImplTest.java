package cz.uhk.pro2_d.service;

import cz.uhk.pro2_d.model.Arena;
import cz.uhk.pro2_d.repository.ArenaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ArenaServiceImplTest {

    @Mock
    private ArenaRepository arenaRepository;

    @InjectMocks
    private ArenaServiceImpl arenaService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllArenas_ReturnsListFromRepository() {
        Arena a = new Arena();
        a.setId(1L);
        List<Arena> expected = Collections.singletonList(a);
        when(arenaRepository.findAll()).thenReturn(expected);

        List<Arena> actual = arenaService.getAllArenas();

        assertEquals(expected, actual);
        verify(arenaRepository).findAll();
    }

    @Test
    void getArenaById_WhenFound_ReturnsArena() {
        Arena a = new Arena();
        a.setId(2L);
        when(arenaRepository.findById(2L)).thenReturn(Optional.of(a));

        Arena actual = arenaService.getArenaById(2L);

        assertEquals(a, actual);
        verify(arenaRepository).findById(2L);
    }

    @Test
    void getArenaById_WhenNotFound_ReturnsNull() {
        when(arenaRepository.findById(3L)).thenReturn(Optional.empty());

        Arena actual = arenaService.getArenaById(3L);

        assertNull(actual);
        verify(arenaRepository).findById(3L);
    }

    @Test
    void saveArena_DelegatesToRepository() {
        Arena a = new Arena();
        a.setName("TestArena");
        when(arenaRepository.save(a)).thenReturn(a);

        Arena actual = arenaService.saveArena(a);

        assertEquals(a, actual);
        verify(arenaRepository).save(a);
    }

    @Test
    void deleteArena_DelegatesToRepository() {
        doNothing().when(arenaRepository).deleteById(4L);

        arenaService.deleteArena(4L);

        verify(arenaRepository).deleteById(4L);
    }
}
