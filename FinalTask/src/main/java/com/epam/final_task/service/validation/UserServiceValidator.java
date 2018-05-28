package com.epam.final_task.service.validation;

import com.epam.final_task.entity.User;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class provides methods to verify the arguments UserService methods
 *
 * @author Ihnat Mikhalkovich
 * @since 1.0
 */
public class UserServiceValidator {

    private final static String EMAIL_REGEX = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    public static boolean addUserValidation(User user, String password) {
        Pattern pattern = Pattern.compile(EMAIL_REGEX);
        if (user == null) {
            return false;
        }
        Matcher matcher = pattern.matcher(user.getEmail());
        return (!user.getFirstName().equals(""))
                && (!user.getLastName().equals(""))
                && (!user.getTelephoneNumber().equals("")) && (user.getTelephoneNumber().length() == 13)
                && (user.getRegistrationDate() != null)
                && matcher.matches()
                && password.length() == 32;
    }

    /**
     * Validate {@code email} and {@code password}
     *
     * @param email entered email
     * @param password entered password
     * @return true if input args are satisfactory
     *         true if input args are not satisfactory
     */
    public static boolean userVerificationValidation(String email, String password) {
        Pattern pattern = Pattern.compile(EMAIL_REGEX);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches() && password.length() == 32;
    }

    /**
     * Validate {@code email}
     *
     * @param email entered email
     * @return true if email is satisfactory
     *         true if email is not satisfactory
     */
    public static boolean emailValidation(String email) {
        Pattern pattern = Pattern.compile(EMAIL_REGEX);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    /**
     * Validate input id.
     *
     * @param userId id of user.
     * @return true if the id is satisfactory
     *         false if the id is not satisfactory
     */
    public static boolean userIdValidation(int userId) {
        return userId > 0;
    }

    /**
     * Validate object of {@code User}
     *
     * @param user inspected object
     * @return true if the {@code user} is satisfactory
     *         false if the {@code user} is not satisfactory
     * @see User
     */
    public static boolean userValidation(User user) {
        Pattern pattern = Pattern.compile(EMAIL_REGEX);
        if (user == null) {
            return false;
        }
        Matcher matcher = pattern.matcher(user.getEmail());
        return (!user.getFirstName().equals(""))
                && (!user.getLastName().equals(""))
                && (!user.getTelephoneNumber().equals("")) && (user.getTelephoneNumber().length() == 13)
                && (user.getRegistrationDate() != null)
                && matcher.matches();
    }

    /**
     * Validate {@code userId} and {@code newPassword}
     *
     * @param userId entered id of user
     * @param newPassword entered password
     * @return true if input args are satisfactory
     *         true if input args are not satisfactory
     */
    public static boolean changePasswordValidation(int userId, String newPassword) {
        return userId > 0 && newPassword.length() == 34;
    }

    /**
     * Validate input number of page.
     *
     * @param pageNumber number of page.
     * @return true if the {@code pageNumber} pageNumber is satisfactory
     *         false if the {@code pageNumber} is not satisfactory
     */
    public static boolean pageNumberValidation(int pageNumber) {
        return pageNumber > 0;
    }

    /**
     * Validate {@code userId} and {@code raise}
     *
     * @param userId entered id of user
     * @param raise entered raise of balance
     * @return true if input args are satisfactory
     *         true if input args are not satisfactory
     */
    public static boolean raiseBalanceValidation(int userId, double raise) {
        return (userId > 0) && (raise > 0);
    }

}
