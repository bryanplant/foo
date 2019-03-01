package Participation;

import org.junit.*;

import static org.junit.Assert.*;

public class Discount_1000_test {

    @Test
    public void testApplicable() {
        Discount_1000 d = new Discount_1000();

        // less than 1000 participation value
        Customer c = new Customer(123, "Test Customer", "TestCustomer@test.org");
        Service s1 = new Service(1, "Service1", 50000);
        Service s2 = new Service(2, "Service2", 10000);
        Participation p1 = new Participation(c, s1);
        Participation p2 = new Participation(c, s2);
        c.participations.add(p1);
        c.participations.add(p2);

        boolean applicable = d.applicable(c);
        assertFalse(applicable);

        // greater than 1000 participation value
        Service s3 = new Service(2, "Service2", 450000);
        Participation p3 = new Participation(c, s3);
        c.participations.add(p3);

        applicable = d.applicable(c);
        assertTrue(applicable);
    }

    @Test
    public void testCalcDiscount() {
        Discount_1000 d = new Discount_1000();
        Customer c = new Customer(123, "Test Customer", "TestCustomer@test.org");
        Service s1 = new Service(1, "Service1", 500000);
        Service s2 = new Service(2, "Service2", 500000);
        Participation p1 = new Participation(c, s1);
        Participation p2 = new Participation(c, s2);
        c.participations.add(p1);
        c.participations.add(p2);

        int discount_value = d.calcDiscount(c);
        assertEquals(discount_value, 50000);
    }
}
