import dao.*;
import java.sql.Timestamp;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        AgencyDAO agencyDAO = new AgencyDAO();
        AgentDAO agentDAO = new AgentDAO();
        BuyerDAO buyerDAO = new BuyerDAO();
        PropertyDAO propertyDAO = new PropertyDAO();
        InquiryDAO inquiryDAO = new InquiryDAO();
        OfferDAO offerDAO = new OfferDAO();

        UUID agencyId = UUID.randomUUID();
        UUID agentId = UUID.randomUUID();
        UUID buyerId = UUID.randomUUID();
        UUID propertyId = UUID.randomUUID();
        UUID inquiryId = UUID.randomUUID();
        UUID offerId = UUID.randomUUID();

        Timestamp now = new Timestamp(System.currentTimeMillis());

        agencyDAO.createAgency(agencyId, "Dream Homes", now);
        agentDAO.registerAgent(agentId, agencyId, "John Doe", "1234567890", "pass123", new Date());
        buyerDAO.registerBuyer(buyerId, "buyer@email.com", "0987654321", "buyerpass", now);
        propertyDAO.addProperty(propertyId, "Luxury Villa", "Spacious 4BHK", "Beachside", "Malibu", 1200000, "villa", "available", now);
        inquiryDAO.submitInquiry(inquiryId, agentId, buyerId, propertyId, "Interested, please contact me.", now);
        offerDAO.submitOffer(offerId, buyerId, agentId, propertyId, true, "pending_response", 1100000, now);

        System.out.println("Sample data inserted successfully.");
    }
}