package HelperFunctions;

import Database.Appointments;
import Database.Customers;
import Model.Appointment;
import Model.Customer;

import java.util.concurrent.atomic.AtomicInteger;

public class Sequence {

    /**
     * Get the largest customer ID in the database and return next ID
     * @return next customer ID
     */
    public static int getNextCustomerID() {
        AtomicInteger maxID = new AtomicInteger(0);
        //Lambda expression to get the next customer ID
        Customers.getCustomers().forEach(c -> {
            if (c.getId() > maxID.get()) {
                maxID.set(c.getId());
            }
        });
        return maxID.get() + 1;
    }

    /**
     * Get the largest appointment ID in the database and return next ID
     * @return next appointment ID
     */
    public static int getNextAppointmentID() {
        // Lambda expression to get the next appointment ID
        AtomicInteger maxID = new AtomicInteger(0);
        //Lambda expression to get the next customer ID
        Appointments.getAllAppointments().forEach(c -> {
            if (c.getID() > maxID.get()) {
                maxID.set(c.getID());
            }
        });
        return maxID.get() + 1;
    }

    /**
     *
     * @return next customer ID
     */
    public static int nextCustomerID() {
        return getNextCustomerID();
    }

    /**
     *
     * @return next appointment ID
     */
    public static int getNextAppointment() {
        return getNextAppointmentID();
    }


}