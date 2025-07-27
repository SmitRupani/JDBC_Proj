package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

public class DBConnection {
    private static Connection connection;

    public static Connection getConnection() throws SQLException {
        if (connection != null && !connection.isClosed()) return connection;

        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter MySQL host (default: localhost): ");
        String host = scanner.nextLine().trim();
        if (host.isEmpty()) host = "localhost";

        System.out.print("Enter MySQL port (default: 3306): ");
        String port = scanner.nextLine().trim();
        if (port.isEmpty()) port = "3306";

        System.out.print("Enter database name: ");
        String dbName = scanner.nextLine().trim();

        System.out.print("Enter username: ");
        String user = scanner.nextLine().trim();

        System.out.print("Enter password: ");
        String password = scanner.nextLine().trim();

        scanner.close();
        // Construct the JDBC URL
        String url = String.format("jdbc:mysql://%s:%s/%s?useSSL=false&serverTimezone=UTC", host, port, dbName);

        try {
            connection = DriverManager.getConnection(url, user, password);
            System.out.println("✅ Connected to database successfully.");
        } catch (SQLException e) {
            System.err.println("Failed to connect: " + e.getMessage());
            throw e;
        }

        return connection;
    }
}
