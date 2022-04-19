package validation;

import domain.Student;
import domain.Tema;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import repository.TemaXMLRepo;
import service.Service;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

public class AddAssignmentTest {
    String filenameTema = "src/test/resources/fisiere/Teme.xml";

    Tema tema;
    TemaValidator validator = new TemaValidator();
    Service service;

    @BeforeEach
    void setup(){
        tema = new Tema("t1", "description", 1, 1);
        service = new Service(null,
                null,
                new TemaXMLRepo(filenameTema),
                validator,
                null,
                null
        );
    }

    @AfterEach
    void cleanup(){
        try {
            Document document = DocumentBuilderFactory
                    .newInstance()
                    .newDocumentBuilder()
                    .newDocument();
            Element root  = document.createElement("inbox");
            document.appendChild(root);

            Transformer transformer = TransformerFactory.
                    newInstance().newTransformer();
            transformer.transform(new DOMSource(document),
                    new StreamResult(filenameTema));
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @Test
    void tc1(){
        Tema tema = service.addTema(this.tema);

        assertNull(tema);
        checkSuccessfulAdd();
    }

    @Test
    void tc2(){
        Tema tema = service.addTema(this.tema);
        Tema tema2 = service.addTema(this.tema);

        assertNull(tema);
        checkTema(tema2);
        checkSuccessfulAdd();
    }

    void checkSuccessfulAdd(){
        Iterator<Tema> teme = service.getAllTeme().iterator();
        Tema tema = teme.next();
        assertFalse(teme.hasNext());
        checkTema(tema);
    }

    void checkTema(Tema tema){
        assertEquals(this.tema.getID(), tema.getID());
        assertEquals(this.tema.getDescriere(), tema.getDescriere());
        assertEquals(this.tema.getDeadline(), tema.getDeadline());
        assertEquals(this.tema.getPrimire(), tema.getPrimire());
    }
}
