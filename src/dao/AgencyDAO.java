package dao;

import java.sql.*;
import java.util.*;

public class AgencyDAO {
    public void createAgency(UUID id, String name, Timestamp registeredAt) {
        String sql = "INSERT INTO Agencies (id, name, registered_at) VALUES (?, ?, ?)";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, id.toString());
            stmt.setString(2, name);
            stmt.setTimestamp(3, registeredAt);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeAgency(UUID agencyId) {
        String sql = "DELETE FROM Agencies WHERE id = ?";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, agencyId.toString());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}