package com.example.servicesnovigrad;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void testServiceConstructor() {
        Service service = new Service("Jaune attend!", 23);
        assertEquals("Jaune attend!", service.getServiceType());
        assertEquals(23, service.getServicePrice());
    }
    @Test
    public void testServiceAddOneRequiredDocumentType() {
        Service service = new Service("Jaune attend!", 23);
        service.addRequiredDoc(DocumentType.PHOTO);
        assertEquals(DocumentType.PHOTO, service.getRequiredDocument().get(0));
    }
    @Test
    public void testServiceAddTwoRequiredDocumentType() {
        Service service = new Service("Jaune attend!", 23);
        service.addRequiredDoc(DocumentType.PHOTO);
        service.addRequiredDoc(DocumentType.PREUVE_DE_DOMICILE);
        assertEquals(DocumentType.PHOTO, service.getRequiredDocument().get(0));
        assertEquals(DocumentType.PREUVE_DE_DOMICILE, service.getRequiredDocument().get(1));
    }
    @Test
    public void testServiceAddThreeRequiredDocuments() {
        Service service = new Service("Jaune attend!", 23);
        service.addRequiredDoc(DocumentType.PHOTO);
        service.addRequiredDoc(DocumentType.PREUVE_DE_DOMICILE);
        service.addRequiredDoc(DocumentType.PREUVE_DE_STATUS);
        assertEquals(DocumentType.PHOTO, service.getRequiredDocument().get(0));
        assertEquals(DocumentType.PREUVE_DE_DOMICILE, service.getRequiredDocument().get(1));
        assertEquals(DocumentType.PREUVE_DE_STATUS, service.getRequiredDocument().get(2));
    }
    @Test
    public void testServiceAddTwoRemoveFirstRequiredDocumentType() {
        Service service = new Service("Jaune attend!", 23);
        service.addRequiredDoc(DocumentType.PHOTO);
        service.addRequiredDoc(DocumentType.PREUVE_DE_DOMICILE);
        service.removeRequiredDoc(DocumentType.PHOTO);
        assertEquals(DocumentType.PREUVE_DE_DOMICILE, service.getRequiredDocument().get(0));
    }

    @Test
    public void testBranchConstructor() {
        Branch branch = new Branch(123123,new Address(), new WeeklySchedule());

        assertEquals(branch.getBranchNumber(), 123123);
        assertNotNull(branch.getAddress());
        assertNotNull(branch.getSchedule());
    }
    @Test
    public void testBranchConstructorAndRatings() {
        Branch branch = new Branch(123123,new Address(), new WeeklySchedule());
        branch.getRatings().put("isudiu", 3.5f);
        branch.getRatings().put("isudi23u", 1f);
        assertTrue(branch.getRatings().get("isudiu") == 3.5f);
        assertTrue(branch.getRatings().get("isudi23u") == 1f);
    }

    @Test
    public void formDocConstructor() {
        FormDocument doc = new FormDocument(DocumentType.FORM);
        assertEquals(DocumentType.FORM, doc.getDocType());
    }
    @Test
    public void formDocConstructorAndLicense() {
        FormDocument doc = new FormDocument(DocumentType.FORM);
        doc.setLicenseType(LicenseType.G);
        assertEquals(DocumentType.FORM, doc.getDocType());
        assertEquals(LicenseType.G, doc.getLicenseType());
    }
    @Test
    public void formDocConstructorAndName() {
        FormDocument doc = new FormDocument(DocumentType.FORM);
        doc.setLastName("Jojojo");
        assertEquals(DocumentType.FORM, doc.getDocType());
        assertEquals("Jojojo", doc.getLastName());
    }
    @Test
    public void imageDocConstructor() {
        ImageDocument doc = new ImageDocument(DocumentType.PHOTO, "Photo");
        assertEquals(DocumentType.PHOTO, doc.getDocType());
        assertEquals("Photo", doc.getDocName());

    }
    @Test
    public void serviceApplicationConstructor() {
        ServiceApplication appli = new ServiceApplication(new Client(), new Service());
        assertNotEquals(appli.getApplicant(), null);
        assertNotNull(appli.getService());
    }
    @Test
    public void serviceApplicationConstructorAndClientName() {
        ServiceApplication appli = new ServiceApplication(new Client(), new Service());
        appli.getApplicant().setUsername("JOJO");
        assertEquals("JOJO", appli.getApplicant().getUsername());
        assertNotEquals(appli.getApplicant(), null);
        assertNotNull(appli.getService());
    }
    @Test //nouveau test
    public void userConstructor() {
        User user = new User("rony","12345678","rymel@.com");
        assertEquals("rony",user.getUsername());
        assertEquals("12345678",user.getPassword());
    }


}