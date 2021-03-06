package validation;

import domain.Student;
import org.junit.Before;
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
import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

public class AddStudentTest {
    String filenameStudent = "src/test/resources/fisiere/Studenti.xml";

    Student student;
    StudentValidator validator = new StudentValidator();
    Service service;

    @BeforeEach
    void setup(){
        student = new Student("id", "nume", 1, "mail", "profesor");
        service = new Service(new StudentXMLRepo(filenameStudent),
                validator,
                null,
                null,
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
                    new StreamResult(filenameStudent));
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @Test
    void tc1(){
        assertDoesNotThrow(()-> service.addStudent(student));
        checkSuccessfulAdd();
    }

    @Test
    void tc2(){
        student.setID("");

        assertThrows(ValidationException.class, ()->service.addStudent(student));
        assertFalse(service.getAllStudenti().iterator().hasNext());
    }

    @Test
    void tc3(){
        student.setID(null);

        assertThrows(ValidationException.class, ()->service.addStudent(student));
        assertFalse(service.getAllStudenti().iterator().hasNext());
    }

    @Test
    void tc4(){
        student.setNume("name");

        assertDoesNotThrow(()-> service.addStudent(student));
        checkSuccessfulAdd();
    }

    @Test
    void tc5(){
        student.setNume("");

        assertThrows(ValidationException.class, ()->service.addStudent(student));
        assertFalse(service.getAllStudenti().iterator().hasNext());
    }

    @Test
    void tc6(){
        student.setNume(null);

        assertThrows(ValidationException.class, ()->service.addStudent(student));
        assertFalse(service.getAllStudenti().iterator().hasNext());
    }

    @Test
    void tc7(){
        student.setEmail("mail");

        assertDoesNotThrow(()-> service.addStudent(student));
        checkSuccessfulAdd();
    }

    @Test
    void tc8(){
        student.setEmail("");

        assertThrows(ValidationException.class, ()->service.addStudent(student));
        assertFalse(service.getAllStudenti().iterator().hasNext());
    }

    @Test
    void tc9(){
        student.setEmail(null);

        assertThrows(ValidationException.class, ()->service.addStudent(student));
        assertFalse(service.getAllStudenti().iterator().hasNext());
    }

    @Test
    void tc10(){
        student.setGrupa(-1);

        assertThrows(ValidationException.class, ()->service.addStudent(student));
        assertFalse(service.getAllStudenti().iterator().hasNext());
    }

    @Test
    void tc11(){
        student.setGrupa(0);

        assertThrows(ValidationException.class, ()->service.addStudent(student));
        assertFalse(service.getAllStudenti().iterator().hasNext());
    }

    @Test
    void tc12(){
        student.setGrupa(1);

        assertDoesNotThrow(()-> service.addStudent(student));
        checkSuccessfulAdd();
    }

    @Test
    void tc13(){
        student.setProfessor("professor");

        assertDoesNotThrow(()-> service.addStudent(student));
        checkSuccessfulAdd();
    }

    @Test
    void tc14(){
        student.setProfessor("");

        assertThrows(ValidationException.class, ()->service.addStudent(student));
        assertFalse(service.getAllStudenti().iterator().hasNext());
    }

    @Test
    void tc15(){
        student.setProfessor(null);

        assertThrows(ValidationException.class, ()->service.addStudent(student));
        assertFalse(service.getAllStudenti().iterator().hasNext());
    }

    @Test
    void tc16() {
        assertDoesNotThrow(()-> service.addStudent(student));

        Student secondStudent = service.addStudent(student);

        assertNotNull(secondStudent);
        checkStudent(secondStudent);
    }

    void checkSuccessfulAdd(){
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
}
