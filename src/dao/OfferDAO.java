package dao;

import java.sql.*;
import java.util.*;

public class OfferDAO {
    private final Connection conn;

    public OfferDAO(Connection conn) {
        this.conn = conn;
    }
    
    public void submitOffer(UUID id, UUID buyerId, UUID agentId, UUID propertyId, boolean offeredByBuyer, String status, long price, Timestamp createdAt) {
        String sql = "INSERT INTO Offers (id, buyer_id, agent_id, property_id, offered_by_buyer, status, price, created_at) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try ( PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, id.toString());
            stmt.setString(2, buyerId.toString());
            stmt.setString(3, agentId.toString());
            stmt.setString(4, propertyId.toString());
            stmt.setBoolean(5, offeredByBuyer);
            stmt.setString(6, status);
            stmt.setLong(7, price);
            stmt.setTimestamp(8, createdAt);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void withdrawOffer(UUID offerId) {
        String sql = "UPDATE Offers SET status = 'rejected', final_date = NOW() WHERE id = ? AND status IN ('pending_response', 'pending_transaction')";
        try ( PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, offerId.toString());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void listPendingOffers() {
        String sql = "SELECT * FROM Offers WHERE status = 'pending_response'";
        try ( PreparedStatement stmt = conn.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                System.out.println("Pending offer: " + rs.getString("id") + " for property " + rs.getString("property_id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}