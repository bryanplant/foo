package Participation;

import org.junit.*;

import java.util.Map;
import java.util.Set;

import static org.junit.Assert.*;

/**
 * This is just a simple template for a JUnit test-class for testing
 * the class Customer.
 */
public class Customer_test {

    // Implementation of Discount that allows for the discount to be greater
    // than the participation value
    class Discount_Test extends Discount {

        @Override
        public boolean applicable(Customer c) {
            return true;
        }

        @Override
        public int calcDiscount(Customer c) {
            return 500;
        }

        @Override
        public String description() {
            return null;
        }
    }

    @Test
    public void testGetParticipationValue() {
        Customer c = new Customer(123, "Test Customer", "TestCustomer@test.org");
        Service s1 = new Service(1, "Service1", 500);
        Service s2 = new Service(2, "Service2", 150);
        c.participations.add(new Participation(c, s1));
        c.participations.add(new Participation(c, s2));

        int value = c.participationValue();
        assertEquals(value, 650);
    }

    @Test
    public void testGetDiscountValue() {
        // Discount less than participation value
        Customer c = new Customer(123, "Test Customer", "TestCustomer@test.org");
        Service s = new Service(1, "Service", 600);
        Discount d = new Discount_Test();
        c.discounts.add(d);
        c.participations.add(new Participation(c, s));

        int discount = c.getDiscountValue();
        assertEquals(discount, 500);

        // Discount greater than participation value
        c = new Customer(123, "Test Customer", "TestCustomer@test.org");
        s = new Service(1, "Service", 400);
        d = new Discount_Test();
        c.discounts.add(d);
        c.participations.add(new Participation(c, s));

        discount = c.getDiscountValue();
        assertEquals(discount, 400);
    }

    @Test
    public void testGetCostToPay() {
        Customer c = new Customer(123, "Test Customer", "TestCustomer@test.org");
        Service s = new Service(1, "Service", 600);
        Discount d = new Discount_Test();
        c.discounts.add(d);
        c.participations.add(new Participation(c, s));

        int cost = c.getCostToPay();
        assertEquals(cost, 100);
    }

    @Test
    public void testGetParticipationGroups() {
        Customer c = new Customer(123, "Test Customer", "TestCustomer@test.org");
        Service s1 = new Service(1, "Service1", 500);
        Service s2 = new Service(2, "Service2", 250);
        Participation p1 = new Participation(c, s1);
        Participation p2 = new Participation(c, s1);
        Participation p3 = new Participation(c, s2);
        c.participations.add(p1);
        c.participations.add(p2);
        c.participations.add(p3);

        Map<Service, Customer.ServiceInfo> groups = c.getParticipationGroups();

        assertEquals(groups.keySet(), Set.of(s1, s2));
        // s1 group
        assertEquals(groups.get(s1).participations, Set.of(p1, p2));
        assertEquals(groups.get(s1).totalParticipationValue, 1000);
        // s2 group
        assertEquals(groups.get(s2).participations, Set.of(p3));
        assertEquals(groups.get(s2).totalParticipationValue, 250);
    }
}
