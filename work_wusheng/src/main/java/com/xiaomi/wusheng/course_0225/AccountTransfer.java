package com.xiaomi.wusheng.course_0225;

import java.sql.*;

public class AccountTransfer {
    private static final String URL = "jdbc:mysql://localhost:3306/test";
    private static final String USER = "test";
    private static final String PASSWORD = "password";

    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
            connection.setAutoCommit(false);

            boolean success = transferFunds(connection, "UserA", "UserB", 100.00);
            if (success) {
                System.out.println("Scenario 1: Transaction committed successfully.");
            } else {
                System.out.println("Scenario 1: Transaction rolled back.");
            }

            success = transferFunds(connection, "UserA", "UserB", 1000.00);
            if (success) {
                System.out.println("Scenario 2: Transaction committed successfully.");
            } else {
                System.out.println("Scenario 2: Transaction rolled back.");
            }

            connection.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static boolean transferFunds(Connection connection, String fromUserName, String toUserName, double amount) {
        PreparedStatement withdrawStmt = null;
        PreparedStatement depositStmt = null;

        try {
            double sourceBalance = getAccountBalance(connection, fromUserName);
            if (sourceBalance < amount) {
                System.out.println("Insufficient balance in source account.");
                return false;
            }

            withdrawStmt = connection.prepareStatement("UPDATE accounts SET balance = balance - ? WHERE user_name = ?");
            withdrawStmt.setDouble(1, amount);
            withdrawStmt.setString(2, fromUserName);
            int withdrawRowsAffected = withdrawStmt.executeUpdate();

            depositStmt = connection.prepareStatement("UPDATE accounts SET balance = balance + ? WHERE user_name = ?");
            depositStmt.setDouble(1, amount);
            depositStmt.setString(2, toUserName);
            int depositRowsAffected = depositStmt.executeUpdate();

            if (withdrawRowsAffected > 0 && depositRowsAffected > 0) {
                connection.commit();
                return true;
            } else {
                connection.rollback();
                return false;
            }
        } catch (SQLException e) {
            try {
                if (connection != null) {
                    connection.rollback();
                }
            } catch (SQLException rollbackEx) {
                rollbackEx.printStackTrace();
            }
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (withdrawStmt != null) withdrawStmt.close();
                if (depositStmt != null) depositStmt.close();
            } catch (SQLException closeEx) {
                closeEx.printStackTrace();
            }
        }
    }

    private static double getAccountBalance(Connection connection, String userName) throws SQLException {
        PreparedStatement balanceStmt = connection.prepareStatement("SELECT balance FROM accounts WHERE user_name = ?");
        balanceStmt.setString(1, userName);
        ResultSet resultSet = balanceStmt.executeQuery();

        if (resultSet.next()) {
            return resultSet.getDouble("balance");
        } else {
            throw new SQLException("Account not found for user: " + userName);
        }
    }
}
