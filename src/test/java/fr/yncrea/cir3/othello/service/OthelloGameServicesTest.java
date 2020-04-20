package fr.yncrea.cir3.othello.service;

import fr.yncrea.cir3.othello.domain.User;
import fr.yncrea.cir3.othello.exception.othello.OthelloCreateGameException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class OthelloGameServicesTest {

    private OthelloGameService service;

    @BeforeAll
    public void beforeAll(){
            service = new OthelloGameService();
    }

    @Test
    public void testCannotStartGameWithoutPlayer(){

        assertThrows(OthelloCreateGameException.class,() ->{
            service.create(null, null, OthelloGameService.EIGHT);
        });


    }
}
