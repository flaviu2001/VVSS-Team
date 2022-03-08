package validation;

import domain.Tema;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TemaValidatorTest {

    @Test
    void validate1() {
        TemaValidator tv = new TemaValidator();
        Tema tema = new Tema("nr1", "grea tema", 2, 1);
        assertDoesNotThrow(() -> tv.validate(tema));
    }

    @Test
    void validate2() {
        TemaValidator tv = new TemaValidator();
        Tema tema = new Tema("", "grea tema", 2, 1);
        assertThrows(ValidationException.class, () -> tv.validate(tema));
    }
}