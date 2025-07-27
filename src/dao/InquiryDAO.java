package dao;

import java.sql.*;
import java.util.*;

public class InquiryDAO {

    private final Connection conn;

    public InquiryDAO(Connection conn) {
        this.conn = conn;
    }
    
    public void submitInquiry(UUID id, UUID agentId, UUID buyerId, UUID propertyId, String message, Timestamp createdAt) {
        String sql = "INSERT INTO Inquiries (id, agent_id, buyer_id, property_id, message, status, created_at) VALUES (?, ?, ?, ?, ?, 'new', ?)";
        try ( PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, id.toString());
            stmt.setString(2, agentId.toString());
            stmt.setString(3, buyerId.toString());
            stmt.setString(4, propertyId.toString());
            stmt.setString(5, message);
            stmt.setTimestamp(6, createdAt);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public double averageAgentResponseTime(UUID agentId) {
        String sql = "SELECT AVG(TIMESTAMPDIFF(SECOND, created_at, responded_at)) AS avg_seconds FROM Inquiries WHERE agent_id = ? AND responded_at IS NOT NULL";
        try ( PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, agentId.toString());
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) return rs.getDouble("avg_seconds");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public void inquiriesLastMonth(UUID propertyId) {
        String sql = "SELECT COUNT(*) AS count FROM Inquiries WHERE property_id = ? AND created_at >= DATE_SUB(NOW(), INTERVAL 1 MONTH)";
        try ( PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, propertyId.toString());
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) System.out.println("Inquiries last month: " + rs.getInt("count"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}