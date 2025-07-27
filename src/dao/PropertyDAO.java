package dao;

import java.sql.*;
import java.util.*;

public class PropertyDAO {
    private final Connection conn;

    public PropertyDAO(Connection conn) {
        this.conn = conn;
    }
    
    public void addProperty(UUID id, String title, String description, String address, String neighborhood,
                            long price, String type, String status, Timestamp createdAt) {
        String sql = "INSERT INTO Properties (id, title, description, address, neighborhood, listing_price, type, status, created_at) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try ( PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, id.toString());
            stmt.setString(2, title);
            stmt.setString(3, description);
            stmt.setString(4, address);
            stmt.setString(5, neighborhood);
            stmt.setLong(6, price);
            stmt.setString(7, type);
            stmt.setString(8, status);
            stmt.setTimestamp(9, createdAt);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updatePropertyStatus(UUID propertyId, String status) {
        String sql = "UPDATE Properties SET status = ?, sold_date = CASE WHEN ? = 'sold' THEN NOW() ELSE NULL END WHERE id = ?";
        try ( PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, status);
            stmt.setString(2, status);
            stmt.setString(3, propertyId.toString());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void searchProperties(String keyword) {
        String sql = "SELECT id, title FROM Properties WHERE MATCH(description) AGAINST (? IN NATURAL LANGUAGE MODE)";
        try ( PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, keyword);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                System.out.println("Property: " + rs.getString("id") + " - " + rs.getString("title"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public double getAverageTimeOnMarket() {
        String sql = "SELECT AVG(DATEDIFF(sold_date, created_at)) AS avg_days FROM Properties WHERE status = 'sold'";
        try ( PreparedStatement stmt = conn.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) return rs.getDouble("avg_days");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
}