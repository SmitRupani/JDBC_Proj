import dao.*;
import java.sql.Timestamp;
import java.util.*;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static final AgencyDAO agencyDAO = new AgencyDAO();
    private static final AgentDAO agentDAO = new AgentDAO();
    private static final BuyerDAO buyerDAO = new BuyerDAO();
    private static final PropertyDAO propertyDAO = new PropertyDAO();
    private static final InquiryDAO inquiryDAO = new InquiryDAO();
    private static final OfferDAO offerDAO = new OfferDAO();

    public static void main(String[] args) {
        int choice;
        do {
            System.out.println("\n====== Real Estate Management System ======");
            System.out.println("1. Add Agency");
            System.out.println("2. Register Agent");
            System.out.println("3. Register Buyer");
            System.out.println("4. Add Property");
            System.out.println("5. Submit Inquiry");
            System.out.println("6. Submit Offer");
            System.out.println("7. Search Properties by Keyword");
            System.out.println("8. View Inquiries (Last Month) for Property");
            System.out.println("9. Average Agent Response Time");
            System.out.println("10. Average Time on Market");
            System.out.println("11. List Pending Offers");
            System.out.println("12. Withdraw Offer");
            System.out.println("0. Exit");
            System.out.print("Enter choice: ");
            choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            try {
                switch (choice) {
                    case 1 -> addAgency();
                    case 2 -> registerAgent();
                    case 3 -> registerBuyer();
                    case 4 -> addProperty();
                    case 5 -> submitInquiry();
                    case 6 -> submitOffer();
                    case 7 -> searchProperties();
                    case 8 -> inquiriesLastMonth();
                    case 9 -> averageResponseTime();
                    case 10 -> averageTimeOnMarket();
                    case 11 -> listPendingOffers();
                    case 12 -> withdrawOffer();
                    case 0 -> System.out.println("Goodbye!");
                    default -> System.out.println("Invalid choice.");
                }
            } catch (Exception e) {
                System.err.println(" Error: " + e.getMessage());
                e.printStackTrace();
            }
        } while (choice != 0);
    }

    private static void addAgency() {
        System.out.print("Enter agency name: ");
        String name = scanner.nextLine();
        agencyDAO.createAgency(UUID.randomUUID(), name, new Timestamp(System.currentTimeMillis()));
        System.out.println("✅ Agency created.");
    }

    private static void registerAgent() {
        System.out.print("Enter agency ID: ");
        UUID agencyId = UUID.fromString(scanner.nextLine());
        System.out.print("Enter agent name: ");
        String name = scanner.nextLine();
        System.out.print("Enter phone number: ");
        String phone = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();
        agentDAO.registerAgent(UUID.randomUUID(), agencyId, name, phone, password, new Date());
        System.out.println("✅ Agent registered.");
    }

    private static void registerBuyer() {
        System.out.print("Enter buyer email: ");
        String email = scanner.nextLine();
        System.out.print("Enter phone number: ");
        String phone = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();
        buyerDAO.registerBuyer(UUID.randomUUID(), email, phone, password, new Timestamp(System.currentTimeMillis()));
        System.out.println("✅ Buyer registered.");
    }

    private static void addProperty() {
        UUID propertyId = UUID.randomUUID();
        System.out.print("Enter title: ");
        String title = scanner.nextLine();
        System.out.print("Enter description: ");
        String description = scanner.nextLine();
        System.out.print("Enter address: ");
        String address = scanner.nextLine();
        System.out.print("Enter neighborhood: ");
        String neighborhood = scanner.nextLine();
        System.out.print("Enter price: ");
        long price = scanner.nextLong(); scanner.nextLine();
        System.out.print("Enter type (apartment/house/villa/commercial): ");
        String type = scanner.nextLine();
        propertyDAO.addProperty(propertyId, title, description, address, neighborhood, price, type, "available", new Timestamp(System.currentTimeMillis()));
        System.out.println("✅ Property added.");
    }

    private static void submitInquiry() {
        System.out.print("Enter agent ID: ");
        UUID agentId = UUID.fromString(scanner.nextLine());
        System.out.print("Enter buyer ID: ");
        UUID buyerId = UUID.fromString(scanner.nextLine());
        System.out.print("Enter property ID: ");
        UUID propertyId = UUID.fromString(scanner.nextLine());
        System.out.print("Enter message: ");
        String message = scanner.nextLine();
        inquiryDAO.submitInquiry(UUID.randomUUID(), agentId, buyerId, propertyId, message, new Timestamp(System.currentTimeMillis()));
        System.out.println("✅ Inquiry submitted.");
    }

    private static void submitOffer() {
        System.out.print("Enter buyer ID: ");
        UUID buyerId = UUID.fromString(scanner.nextLine());
        System.out.print("Enter agent ID: ");
        UUID agentId = UUID.fromString(scanner.nextLine());
        System.out.print("Enter property ID: ");
        UUID propertyId = UUID.fromString(scanner.nextLine());
        System.out.print("Enter offer price: ");
        long price = scanner.nextLong(); scanner.nextLine();
        offerDAO.submitOffer(UUID.randomUUID(), buyerId, agentId, propertyId, true, "pending_response", price, new Timestamp(System.currentTimeMillis()));
        System.out.println("✅ Offer submitted.");
    }

    private static void searchProperties() {
        System.out.print("Enter keyword: ");
        String keyword = scanner.nextLine();
        propertyDAO.searchProperties(keyword); // output handled in DAO
    }

    private static void inquiriesLastMonth() {
        System.out.print("Enter property ID: ");
        UUID propertyId = UUID.fromString(scanner.nextLine());
        inquiryDAO.inquiriesLastMonth(propertyId);
    }

    private static void averageResponseTime() {
        System.out.print("Enter agent ID: ");
        UUID agentId = UUID.fromString(scanner.nextLine());
        double avgSeconds = inquiryDAO.averageAgentResponseTime(agentId);
        System.out.println(" Average agent response time: " + avgSeconds + " seconds");
    }

    private static void averageTimeOnMarket() {
        double avgDays = propertyDAO.getAverageTimeOnMarket();
        System.out.println(" Average time on market: " + avgDays + " days");
    }

    private static void listPendingOffers() {
        offerDAO.listPendingOffers(); // output handled inside DAO
    }

    private static void withdrawOffer() {
        System.out.print("Enter offer ID to withdraw: ");
        UUID offerId = UUID.fromString(scanner.nextLine());
        offerDAO.withdrawOffer(offerId);
        System.out.println(" Offer withdrawn.");
    }
}
