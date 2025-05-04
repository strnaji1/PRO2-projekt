package cz.uhk.pro2_d.service;


import cz.uhk.pro2_d.model.Referee;
import cz.uhk.pro2_d.repository.RefereeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RefereeServiceImplTest {

    private RefereeRepository refereeRepository;
    private RefereeServiceImpl refereeService;

    @BeforeEach
    void setUp() {
        refereeRepository = mock(RefereeRepository.class);
        refereeService = new RefereeServiceImpl(refereeRepository);
    }

    @Test
    void testGetAllReferees() {
        when(refereeRepository.findAll()).thenReturn(Arrays.asList(new Referee(), new Referee()));
        assertEquals(2, refereeService.getAllReferees().size());
    }

    @Test
    void testSaveReferee() {
        Referee referee = new Referee();
        refereeService.saveReferee(referee);
        verify(refereeRepository, times(1)).save(referee);
    }

    @Test
    void testGetReferee() {
        Referee referee = new Referee();
        when(refereeRepository.findById(1L)).thenReturn(Optional.of(referee));
        assertEquals(referee, refereeService.getReferee(1L));
    }

    @Test
    void testDeleteReferee() {
        refereeService.deleteReferee(1L);
        verify(refereeRepository, times(1)).deleteById(1L);
    }
}
