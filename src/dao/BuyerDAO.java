package dao;

import java.sql.*;
import java.util.*;

public class BuyerDAO {
    public void registerBuyer(UUID id, String email, String phone, String password, Timestamp createdAt) {
        String sql = "INSERT INTO Buyers (id, email, phone_number, password, created_at) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, id.toString());
            stmt.setString(2, email);
            stmt.setString(3, phone);
            stmt.setString(4, password);
            stmt.setTimestamp(5, createdAt);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}