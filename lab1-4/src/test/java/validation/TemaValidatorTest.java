package validation;

import domain.Tema;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TemaValidatorTest {
    TemaValidator tv = new TemaValidator();

    @Test
    void tc1(){
        Tema tema = new Tema("", "d", 1, 1);
        assertThrows(ValidationException.class, () -> tv.validate(tema));
    }

    @Test
    void tc2(){
        Tema tema = new Tema(null, "d", 1, 1);
        assertThrows(ValidationException.class, () -> tv.validate(tema));
    }

    @Test
    void tc3(){
        Tema tema = new Tema("1", "", 1, 1);
        assertThrows(ValidationException.class, () -> tv.validate(tema));
    }

    @Test
    void tc4(){
        Tema tema = new Tema("1", "d", 0, 1);
        assertThrows(ValidationException.class, () -> tv.validate(tema));
    }

    @Test
    void tc5(){
        Tema tema = new Tema("1", "d", 15, 1);
        assertThrows(ValidationException.class, () -> tv.validate(tema));
    }

    @Test
    void tc6(){
        Tema tema = new Tema("1", "d", 1, 0);
        assertThrows(ValidationException.class, () -> tv.validate(tema));
    }

    @Test
    void tc7(){
        Tema tema = new Tema("1", "d", 1, 15);
        assertThrows(ValidationException.class, () -> tv.validate(tema));
    }

    @Test
    void tc8() {
        TemaValidator tv = new TemaValidator();
        Tema tema = new Tema("1", "d", 1, 1);
        assertDoesNotThrow(() -> tv.validate(tema));
    }
}
