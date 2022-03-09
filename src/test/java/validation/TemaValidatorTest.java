package validation;

import domain.Tema;
import org.junit.Test;

public class TemaValidatorTest {
    @Test
    public void validate1() {
        TemaValidator tv = new TemaValidator();
        Tema tema = new Tema("nr1", "grea tema", 2, 1);
        //Assert.assertDoesNotThrow(() -> tv.validate(tema));
        try{
            tv.validate(tema);
        }catch (ValidationException ex){
            assert false;
        }
    }

//    @Test
//    void validate2() {
//        TemaValidator tv = new TemaValidator();
//        Tema tema = new Tema("", "grea tema", 2, 1);
//        assertThrows(ValidationException.class, () -> tv.validate(tema));
//    }
}
