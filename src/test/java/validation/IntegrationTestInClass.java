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
import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class IntegrationTestInClass {
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
        nota = new Nota("1","id", "t1", 10, LocalDate.of(2022, 4, 5));

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
        Tema tema = service.addTema(this.tema);

        assertNull(tema);
        checkSuccessfulAddTema();
    }

    @Test
    void tc2(){
        assertDoesNotThrow(()-> service.addStudent(student));
        checkSuccessfulAddStudent();
    }

    @Test
    void tc3(){
        assertThrows(ValidationException.class, ()->{service.addNota(nota, "feedback");});
    }

    @Test
    void tc4(){
        service.addStudent(student);
        tema.setID("badId");
        service.addTema(tema);
        assertThrows(ValidationException.class, ()->{service.addNota(nota, "feedback");});
    }

    void checkSuccessfulAddStudent(){
        Iterator<Student> students = service.getAllStudenti().iterator();
        Student student = students.next();
        assertFalse(students.hasNext());
        checkStudent(student);
    }

    void checkStudent(Student student){
        assertEquals(this.student.getID(), student.getID());
        assertEquals(this.student.getNume(), student.getNume());
        assertEquals(this.student.getEmail(), student.getEmail());
        assertEquals(this.student.getGrupa(), student.getGrupa());
        assertEquals(this.student.getProfessor(), student.getProfessor());
    }


    void checkSuccessfulAddTema(){
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
