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

}