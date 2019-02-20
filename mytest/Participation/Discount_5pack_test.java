package Participation;

import org.junit.*;

import static org.junit.Assert.*;

public class Discount_5pack_test {

    @Test
    public void testApplicable() {
        // less than 5 services
        Customer c = new Customer(123, "Test Customer", "TestCustomer@test.org");

        // more than 5 services, but not all values > 100

        // more than 5 services and all values > 100
    }

    @Test
    public void testCalcDiscount() {

    }
}
