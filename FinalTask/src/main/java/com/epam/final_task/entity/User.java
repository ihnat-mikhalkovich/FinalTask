package com.epam.final_task.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 * Javabean class describing the user.
 *
 * @author Ihnat Mikhalkovich
 * @since 1.0
 */
public class User implements Serializable {

    private int id = -1;

    private String firstName;

    private String lastName;

    private String telephoneNumber;

    private String email;

    private UserRoleEnum role = UserRoleEnum.USER;

    private Date registrationDate;

    private int discount = 0;

    private double balance = 0.0;

    /**
     * Constructor without parameters.
     */
    public User() {
    }

    /**
     * Constructor with parameters. Used to add a user to the database.
     *
     * @param firstName        first name of user
     * @param lastName         last name of user
     * @param telephoneNumber  telephone number of user
     * @param email            email of user
     * @param registrationDate date of user registration
     */
    public User(String firstName, String lastName, String telephoneNumber, String email, Date registrationDate) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.telephoneNumber = telephoneNumber;
        this.email = email;
        this.registrationDate = registrationDate;
    }

    /**
     * Constructor with parameters. Used for get information from database.
     *
     * @param id               user id
     * @param firstName        first name of user
     * @param lastName         last name of user
     * @param telephoneNumber  telephone number of user
     * @param email            email of user
     * @param role             role of user
     * @param registrationDate date of user registration
     * @param discount         discounted user percentage
     * @param balance          balance of user
     */
    public User(int id, String firstName, String lastName, String telephoneNumber, String email, UserRoleEnum role, Date registrationDate, int discount, double balance) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.telephoneNumber = telephoneNumber;
        this.email = email;
        this.role = role;
        this.registrationDate = registrationDate;
        this.discount = discount;
        this.balance = balance;
    }

    /**
     * Used to get user ID
     *
     * @return user ID
     */
    public int getId() {
        return id;
    }

    /**
     * Used to set user ID
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Used to get first name of user
     *
     * @return firstName
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Used to set first name of user
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Used to get last name of user
     *
     * @return last name
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Used to get set last name of user
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Used to get telephone number of user
     *
     * @return telephone number
     */
    public String getTelephoneNumber() {
        return telephoneNumber;
    }

    /**
     * Used to set telephone number of user
     */
    public void setTelephoneNumber(String telephoneNumber) {
        this.telephoneNumber = telephoneNumber;
    }

    /**
     * Used to get email of user
     *
     * @return email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Used to set email of user
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Used to get role of user
     *
     * @return role
     */
    public UserRoleEnum getRole() {
        return role;
    }

    /**
     * Used to set role of user
     */
    public void setRole(UserRoleEnum role) {
        this.role = role;
    }

    /**
     * Used to get registration date status of user
     *
     * @return registrationDate
     */
    public Date getRegistrationDate() {
        return registrationDate;
    }

    /**
     * Used to set registration date status of user
     */
    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
    }

    /**
     * Used to get discounted user percentage
     *
     * @return discount
     */
    public int getDiscount() {
        return discount;
    }

    /**
     * Used to set discounted user percentage
     */
    public void setDiscount(int discount) {
        this.discount = discount;
    }

    /**
     * Used to get user balance
     *
     * @return balance
     */
    public double getBalance() {
        return balance;
    }

    /**
     * Used to set user balance
     *
     * @param balance user balance
     */
    public void setBalance(double balance) {
        this.balance = balance;
    }

    /**
     * Overriding the {@code equals()} method of the {@code Object}.
     *
     * @param o compared to the current object
     * @return true if object equals {@code this}
     * false if object don't equals {@code this}
     * @see Object
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id &&
                discount == user.discount &&
                Double.compare(user.balance, balance) == 0 &&
                Objects.equals(firstName, user.firstName) &&
                Objects.equals(lastName, user.lastName) &&
                Objects.equals(telephoneNumber, user.telephoneNumber) &&
                Objects.equals(email, user.email) &&
                role == user.role &&
                Objects.equals(registrationDate, user.registrationDate);
    }

    /**
     * Overriding the {@code hashCode()} method of the {@code Object}.
     *
     * @return hash code of {@code this}
     * @see Objects
     * @see Object
     */
    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, telephoneNumber, email, role, registrationDate, discount, balance);
    }

    /**
     * Override the class {@code toString()} of class {@code Object}.
     *
     * @return string representation of the object
     * @see Object
     */
    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", telephoneNumber='" + telephoneNumber + '\'' +
                ", email='" + email + '\'' +
                ", role=" + role +
                ", registrationDate=" + registrationDate +
                ", discount=" + discount +
                ", balance=" + balance +
                '}';
    }
}
