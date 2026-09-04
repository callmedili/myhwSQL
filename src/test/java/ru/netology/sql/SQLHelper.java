package ru.netology.sql;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLHelper {

    private static final String URL = "jdbc:mysql://localhost:3306/app";
    private static final String USER = "app";
    private static final String PASSWORD = "pass";

    private SQLHelper() {
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public static String getVerificationCode() {
        var runner = new QueryRunner();
        var codeSQL = "SELECT code FROM auth_codes ORDER BY created DESC LIMIT 1;";

        for (int i = 0; i < 10; i++) {
            try (var conn = getConnection()) {
                String code = runner.query(conn, codeSQL, new ScalarHandler<>());

                if (code != null) {
                    return code;
                }

                Thread.sleep(100);
            } catch (SQLException | InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        throw new RuntimeException("Verification code was not found");
    }

    public static void cleanAuthCodes() {
        var runner = new QueryRunner();
        var deleteCodesSQL = "DELETE FROM auth_codes;";

        try (var conn = getConnection()) {
            runner.update(conn, deleteCodesSQL);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void cleanDatabase() {
        var runner = new QueryRunner();

        try (var conn = getConnection()) {
            runner.update(conn, "DELETE FROM card_transactions;");
            runner.update(conn, "DELETE FROM auth_codes;");
            runner.update(conn, "DELETE FROM cards;");
            runner.update(conn, "DELETE FROM users;");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}