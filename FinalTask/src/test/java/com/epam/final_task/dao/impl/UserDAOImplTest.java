package com.epam.final_task.dao.impl;

import com.epam.final_task.dao.UserDAO;
import com.epam.final_task.entity.User;
import com.epam.final_task.entity.UserRoleEnum;
import com.epam.final_task.exception.DAOException;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.Assert.*;

public class UserDAOImplTest {

    private final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

    @Test
    public void getUser() {
        Date registrationDate = null;
        try {
            registrationDate = format.parse("2018-04-11");
        } catch (ParseException e) {
            fail();
        }
        User user = new User();
        user.setId(15);
        user.setFirstName("Игнат");
        user.setLastName("Михалкович");
        user.setTelephoneNumber("+375291234567");
        user.setEmail("magnat118@mail.com");
        user.setRole(UserRoleEnum.USER);
        user.setRegistrationDate(registrationDate);
        user.setDiscount(7);
        user.setBalance(112.37);
        UserDAO userDAO = new UserDAOImpl();
        try {
            User userFromDb = userDAO.getUser(15);
            assertTrue(user.equals(userFromDb));
            user.setBalance(user.getBalance() + 0.01);
            assertFalse(user.equals(userFromDb));
        } catch (DAOException e) {
            fail();
        }
    }



    @Test
    public void userVerification() {
        String[][] trueEmailPassword = { {"magnat118@gmail.com", "d9eff2de5a0e0e46efad7ba4ef2e8706"},
                {"novicki118@gmail.com", "d9eff2de5a0e0e46efad7ba4ef2e8706"},
                { "amal@gmail.com", "d9eff2de5a0e0e46efad7ba4ef2e8706"} };
        String[][] falseEmailPassword = { {"magnat1180@gmail.com", "d9eff2de5a0e0e46efad7ba4ef2e8706"},
                {"novicki118@gmail.com", "d9eff2de5a0e0e46efad7ba3ef2e8706"},
                { "amal@gmail.ru", "d9eff2de5a0e0e46efad7ba4ef2e8706"} };
        UserDAO userDAO = new UserDAOImpl();
        try {
            for (int i = 0; i < trueEmailPassword.length; i++) {
                boolean userValid = userDAO.userVerification(trueEmailPassword[i][0], trueEmailPassword[i][1]);
                assertTrue(userValid);
            }
            for (int i = 0; i < falseEmailPassword.length; i++) {
                boolean userValid = userDAO.userVerification(falseEmailPassword[i][0], falseEmailPassword[i][1]);
                assertFalse(userValid);
            }
        } catch (DAOException e) {
            fail();
        }
    }

    @Test
    public void editUser() {
        Date registrationDate = null;
        try {
            registrationDate = format.parse("2018-04-11");
        } catch (ParseException e) {
            fail();
        }
        User user = new User();
        int userId = 20;
        user.setId(userId);
        user.setFirstName("Игнат");
        user.setLastName("Михалкович");
        user.setTelephoneNumber("+375291234567");
        user.setEmail("testemil1@mail.com");
        user.setRole(UserRoleEnum.USER);
        user.setRegistrationDate(registrationDate);
        user.setDiscount(7);
        user.setBalance(112.37);
        UserDAO userDAO = new UserDAOImpl();
        try {
            User beforeEdit = userDAO.getUser(userId);
            userDAO.editUser(user);
            User afterEdit = userDAO.getUser(userId);
            assertTrue(user.equals(afterEdit) && !beforeEdit.equals(afterEdit));
            userDAO.editUser(beforeEdit);
        } catch (DAOException e) {
            fail();
        }
    }

    @Test
    public void getUserId() {
        String[] emails = { "magnat118@gmail.com", "novicki118@gmail.com", "amal@gmail.com", "amalll@gmail.com" };
        int[] correctResults = { 1, 2, 3, 0 };
        UserDAO userDAO = new UserDAOImpl();
        try {
            for (int i = 0; i < emails.length; i++) {
                int result = userDAO.getUserId(emails[i]);
                assertEquals(correctResults[i], result);
            }
        } catch (DAOException e) {
            fail();
        }
    }

    @Test
    public void changePassword() {
        int[] idArray = {17, 18, 19};
        String[] passwords = {"test", "test", "test"};
        String[] newPasswords = {"newtest", "newtest", "newtest"};
        UserDAO userDAO = new UserDAOImpl();
        try {
            for (int i = 0; i < idArray.length; i++) {
                userDAO.changePassword(idArray[i], newPasswords[i]);
                User after = userDAO.getUser(idArray[i]);
                assertTrue(userDAO.userVerification(after.getEmail(), newPasswords[i]));
                userDAO.changePassword(idArray[i], passwords[i]);
            }
        } catch (DAOException e) {
            fail();
        }
    }

    @Test
    public void raiseBalance() {
        int[] idArray = {17, 18, 19};
        double raise = 12.32;
        double[] startBalance = {92.37, 9.37, 112.37};
        UserDAO userDAO = new UserDAOImpl();
        try {
            for (int i = 0; i < idArray.length; i++) {
                User before = userDAO.getUser(idArray[i]);
                userDAO.raiseBalance(idArray[i], raise);
                User after = userDAO.getUser(idArray[i]);
                assertTrue(after.getBalance() == (before.getBalance() + raise));
                userDAO.editUser(before);
            }
        } catch (DAOException e) {
            fail();
        }
    }

    @Test
    public void getUserBalance() {
        int[] idArray = {2, 3, 100500, -100500};
        double[] correctResult = {211.37, 112.37, -1, -1};
        UserDAO userDAO = new UserDAOImpl();
        try {
            for (int i = 0; i < idArray.length; i++) {
                double result = userDAO.getUserBalance(idArray[i]);
                assertTrue((correctResult[i] - result) == 0);
            }
        } catch (DAOException e) {
            fail();
        }
    }

    @Test
    public void banUser() {
        int[] idArray = {17, 18, 19, 20};
        UserDAO userDAO = new UserDAOImpl();
        try {
            for (int i = 0; i < idArray.length; i++) {
                User beforeBan = userDAO.getUser(idArray[i]);
                userDAO.banUser(idArray[i]);
                User afterBan = userDAO.getUser(idArray[i]);
                assertTrue(afterBan.getRole().equals(UserRoleEnum.BANNED));
                userDAO.editUser(beforeBan);
            }
        } catch (DAOException e) {
            fail();
        }
    }

}