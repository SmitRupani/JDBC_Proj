package dao;

import java.sql.*;
import java.util.*;

public class AgentDAO {
    public void registerAgent(UUID id, UUID agencyId, String name, String phone, String password, java.util.Date hireDate) {
        String sql = "INSERT INTO Agents (id, agency_id, name, phone_number, password, hire_date, properties_sold) VALUES (?, ?, ?, ?, ?, ?, 0)";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, id.toString());
            stmt.setString(2, agencyId.toString());
            stmt.setString(3, name);
            stmt.setString(4, phone);
            stmt.setString(5, password);
            stmt.setDate(6, new java.sql.Date(hireDate.getTime()));
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeAgent(UUID agentId) {
        String sql = "DELETE FROM Agents WHERE id = ?";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, agentId.toString());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int getClosedDeals(UUID agentId) {
        String sql = "SELECT COUNT(*) AS deals FROM Properties WHERE id IN (SELECT property_id FROM Agents_Properties WHERE agent_id = ?) AND status = 'sold'";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, agentId.toString());
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) return rs.getInt("deals");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
}