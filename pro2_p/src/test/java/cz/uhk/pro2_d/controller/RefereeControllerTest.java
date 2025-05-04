package cz.uhk.pro2_d.controller;

import cz.uhk.pro2_d.model.Referee;
import cz.uhk.pro2_d.service.RefereeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.ui.Model;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class RefereeControllerTest {

    private RefereeService refereeService;
    private RefereeController refereeController;
    private Model model;

    @BeforeEach
    void setUp() {
        refereeService = mock(RefereeService.class);
        refereeController = new RefereeController(refereeService);
        model = mock(Model.class);
    }

    @Test
    void testList() {
        List<Referee> list = Collections.singletonList(new Referee());
        when(refereeService.getAllReferees()).thenReturn(list);

        String view = refereeController.list(model);

        verify(model).addAttribute("referees", list);
        assertEquals("referees_list", view);
    }

    @Test
    void testDetail() {
        Referee ref = new Referee();
        when(refereeService.getReferee(42L)).thenReturn(ref);

        String view = refereeController.detail(model, 42L);

        verify(model).addAttribute("referee", ref);
        assertEquals("referees_detail", view);
    }

    @Test
    void testAddForm() {
        String view = refereeController.add(model);

        verify(model).addAttribute(eq("referee"), any(Referee.class));
        assertEquals("referees_add", view);
    }

    @Test
    void testEditForm() {
        Referee ref = new Referee();
        when(refereeService.getReferee(5L)).thenReturn(ref);

        String view = refereeController.edit(model, 5L);

        verify(model).addAttribute("referee", ref);
        assertEquals("referees_add", view);
    }

    @Test
    void testSaveRedirect() {
        Referee ref = new Referee();

        String view = refereeController.addSave(ref);

        verify(refereeService).saveReferee(ref);
        assertEquals("redirect:/referees/", view);
    }

    @Test
    void testDeleteForm() {
        Referee ref = new Referee();
        when(refereeService.getReferee(7L)).thenReturn(ref);

        String view = refereeController.delete(model, 7L);

        verify(model).addAttribute("referee", ref);
        assertEquals("referees_delete", view);
    }

    @Test
    void testDeleteConfirmRedirect() {
        String view = refereeController.deleteConfirm(7L);

        verify(refereeService).deleteReferee(7L);
        assertEquals("redirect:/referees/", view);
    }
}
