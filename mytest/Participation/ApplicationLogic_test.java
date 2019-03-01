package Participation;

import org.junit.*;

import java.util.Map;

import static org.junit.Assert.*;

/**
 * This is just a simple template for Junit test-class for testing
 * the class ApplicationLogic. Testing this class is a bit more
 * complicated. It uses database, which form an implicit part of
 * the state of ApplicationLogic. After each test case, you need to
 * reset the content of the database to the value it had, before
 * the test case. Otherwise, multiple test cases will interfere
 * with each other, which makes debugging hard should you find error.
 */
public class ApplicationLogic_test {

    /**
     * Provide a functionality to reset the database. Here I will just
     * delete the whole database file.
     */
    private void setupDB() {
        Persistence.wipedb();
    }

    @Test
    public void testRemoveService() {
        setupDB();
        ApplicationLogic SUT = new ApplicationLogic();

        int cID = SUT.addCustomer("Test Customer", "TestCustomer@test.org");
        int sID = SUT.addService("Service1", 100);
        SUT.addParticipation(cID, sID);

        // check that the participation value is correct and the service exists
        assertEquals(SUT.participationValue(cID), 100);
        assertTrue(SUT.serviceExists(sID));
        SUT.removeService(sID);
        // check that there is no participation value and the service is deleted
        assertEquals(SUT.participationValue(cID), 0);
        assertFalse(SUT.serviceExists(sID));
    }

    @Test
    public void testResolve() {
        setupDB();
        ApplicationLogic SUT = new ApplicationLogic();

        int cID1 = SUT.addCustomer("Test Customer1", "TestCustomer1@test.org");
        int sID1 = SUT.addService("Service1", 100000);
        int cID2 = SUT.addCustomer("Test Customer2", "TestCustomer2@test.org");
        int sID2 = SUT.addService("Service2", 1000);

        // add the participations and give customer 1 a discount
        SUT.addParticipation(cID1, sID1);
        SUT.addParticipation(cID2, sID2);
        SUT.awardDiscount(cID1, ApplicationLogic.D1000);

        // verify that both participation values exist and the discount was applied
        Map<Customer, Integer> results = SUT.resolve();
        assertEquals(results.size(), 2);
        assertTrue(results.containsValue(95000));
        assertTrue(results.containsValue(1000));
    }
}
