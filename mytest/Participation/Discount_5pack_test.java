package Participation;

import org.junit.*;

import static org.junit.Assert.*;

public class Discount_5pack_test {

    @Test
    public void testApplicable() {
        Discount_5pack d = new Discount_5pack();

        // less than 5 services
        Customer c = new Customer(123, "Test Customer", "TestCustomer@test.org");
        Service s1 = new Service(1, "Service1", 500);
        Service s2 = new Service(2, "Service2", 500);
        Service s3 = new Service(3, "Service3", 250);
        Service s4 = new Service(4, "Service4", 250);
        Participation p1 = new Participation(c, s1);
        Participation p2 = new Participation(c, s2);
        Participation p3 = new Participation(c, s3);
        Participation p4 = new Participation(c, s4);
        c.participations.add(p1);
        c.participations.add(p2);
        c.participations.add(p3);
        c.participations.add(p4);

        boolean applicable = d.applicable(c);
        assertFalse(applicable);

        // 5 services, but less than 5 have values >= 100
        Service s5 = new Service(5, "Service5", 50);
        Participation p5 = new Participation(c, s5);
        c.participations.add(p5);

        applicable = d.applicable(c);
        assertFalse(applicable);

        // more than 5 services and 5 have values >= 100
        Service s6 = new Service(4, "Service6", 100);
        Participation p6 = new Participation(c, s6);
        c.participations.add(p6);

        applicable = d.applicable(c);
        assertTrue(applicable);
    }

    @Test
    public void testCalcDiscount() {
        Discount_5pack d = new Discount_5pack();
        Customer c = new Customer(123, "Test Customer", "TestCustomer@test.org");
        int discount_value = d.calcDiscount(c);
        assertEquals(discount_value, 10);
    }
}
