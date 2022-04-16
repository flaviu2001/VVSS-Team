package validation;

import domain.Nota;
import domain.Student;
import domain.Tema;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import repository.NotaXMLRepo;
import repository.StudentXMLRepo;
import repository.TemaXMLRepo;
import service.Service;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class AddGradeExploratoryDragos {
    String filenameStudent = "src/test/resources/fisiere/Studenti.xml";
    String filenameTema = "src/test/resources/fisiere/Teme.xml";
    String filenameGrade = "src/test/resources/fisiere/Note.xml";

    StudentXMLRepo studentRepo = new StudentXMLRepo(filenameStudent);
    TemaXMLRepo temaRepo = new TemaXMLRepo(filenameTema);
    NotaXMLRepo notaRepo = new NotaXMLRepo(filenameGrade);


    Student student;
    StudentValidator validatorStudent = new StudentValidator();
    Tema tema;
    TemaValidator validatorTema = new TemaValidator();
    Nota nota;
    NotaValidator validatorNota = new NotaValidator(studentRepo, temaRepo);
    Service service;

    @BeforeEach
    void setup(){
        student = new Student("id", "nume", 1, "mail", "profesor");
        tema = new Tema("t1", "description", 1, 1);
        nota = new Nota("1","id", "t1", 10, LocalDate.of(2022, 10, 2));

        service = new Service(studentRepo,
                validatorStudent,
                temaRepo,
                validatorTema,
                notaRepo,
                validatorNota
        );
    }

    @AfterEach
    void cleanup(){
        clean(filenameStudent);
        clean(filenameTema);
        clean(filenameGrade);
    }

    void clean(String file){
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
                    new StreamResult(file));
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @Test
    void tc1(){
        assertThrows(ValidationException.class, ()->{service.addNota(nota, "feedback");});
    }

    @Test
    void tc2(){
        service.addTema(tema);
        assertThrows(ValidationException.class, ()->{service.addNota(nota, "feedback");});
    }

    @Test
    void tc3(){
        service.addStudent(student);
        assertThrows(ValidationException.class, ()->{service.addNota(nota, "feedback");});
    }

    @Test
    void tc4(){
        service.addStudent(student);
        service.addTema(tema);
        assertDoesNotThrow(()->{service.addNota(nota, "feedback");});
    }

    @Test
    void tc5(){
        service.addStudent(student);
        service.addTema(tema);
        assertDoesNotThrow(()->{service.addNota(nota, "feedback");});
        assertThrows(ValidationException.class,()->service.addNota(nota, "feedback"));
    }

    @Test
    void tc6(){
        service.addStudent(student);
        service.addTema(tema);
        nota.setIdStudent("a");
        assertThrows(ValidationException.class, ()->{service.addNota(nota, "feedback");});
    }

    @Test
    void tc7(){
        service.addStudent(student);
        service.addTema(tema);
        nota = new Nota("1","id", "t1", 10, LocalDate.of(2022, 9, 1));
        assertThrows(ValidationException.class,()->service.addNota(nota, "feedback"));
    }

    @Test
    void tc8(){
        service.addStudent(student);
        service.addTema(tema);
        nota.setNota(0.2);
        assertThrows(ValidationException.class,()->service.addNota(nota, "feedback"));
    }

    @Test
    void tc9(){
        service.addStudent(student);
        service.addTema(tema);
        service.addNota(nota, "feedback");
        service.deleteStudent(student.getID());
        assertFalse(service.getAllNote().iterator().hasNext());
    }

    @Test
    void tc10(){
        service.addStudent(student);
        service.addTema(tema);
        service.addNota(nota, "feedback");
        service.deleteTema(tema.getID());
        assertFalse(service.getAllNote().iterator().hasNext());
    }

    @Test
    void tc11(){
        service.addStudent(student);
        tema.setDeadline(2);
        service.addTema(tema);
        nota = new Nota("1","id", "t1", 10, LocalDate.of(2022, 10, 20));
        double notaVal = service.addNota(nota, "feedback");
        assertEquals(7.5, notaVal);
    }

    @Test
    void tc12(){
        service.addStudent(student);
        tema.setDeadline(2);
        service.addTema(tema);
        nota = new Nota("1","id", "t1", 10, LocalDate.of(2022, 10, 20));
        service.addNota(nota, "feedback");
        tema.setDeadline(5);
        service.updateTema(tema);
        Nota addedNota = service.getAllNote().iterator().next();
        assertEquals(10, addedNota.getNota());
    }

    @Test
    void tc13(){
        service.addTema(tema);
        tema.setDeadline(5);
        service.updateTema(tema);
        assertEquals(5, service.getAllTeme().iterator().next().getDeadline());
    }

    @Test
    void tc14(){
        service.addStudent(student);
        tema.setPrimire(2);
        tema.setDeadline(3);
        service.addTema(tema);
        nota = new Nota("1","id", "t1", 10, LocalDate.of(2022, 10, 2));
        assertThrows(ValidationException.class, ()->{service.addNota(nota, "feedback");});
    }
}
