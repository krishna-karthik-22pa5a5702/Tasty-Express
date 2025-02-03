package registerationlogin.repository;

import org.springframework.stereotype.Repository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import registerationlogin.dto.CustomerInsightsDTO;

import java.util.ArrayList;
import java.util.List;

@Repository
public class CustomerInsightsRepository {

    private final EntityManager entityManager;

    public CustomerInsightsRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public List<CustomerInsightsDTO> getCustomerInsights(Long restaurantId, String periodType) {
        String sql = "SELECT u.name AS customerName, COUNT(o.id) AS totalOrders, SUM(o.total_price) AS totalSpent, " +
                     "MAX(o.order_date) AS lastOrderDate " +
                     "FROM food_order o " +
                     "JOIN users u ON o.user_id = u.id " +
                     "WHERE o.restaurant_id = :restaurantId ";

        if ("daily".equalsIgnoreCase(periodType)) {
            sql += "AND o.order_date >= CURDATE() ";
        } else if ("weekly".equalsIgnoreCase(periodType)) {
            sql += "AND o.order_date >= DATE_SUB(CURDATE(), INTERVAL 7 DAY) ";
        } else if ("monthly".equalsIgnoreCase(periodType)) {
            sql += "AND o.order_date >= DATE_SUB(CURDATE(), INTERVAL 1 MONTH) ";
        }

        sql += "GROUP BY o.user_id ORDER BY totalSpent DESC LIMIT 10";

        Query query = entityManager.createNativeQuery(sql);
        query.setParameter("restaurantId", restaurantId);

        List<Object[]> results = query.getResultList();
        List<CustomerInsightsDTO> reportList = new ArrayList<>();

        for (Object[] result : results) {
            String customerName = (String) result[0];
            Long totalOrders = ((Number) result[1]).longValue();
            Double totalSpent = ((Number) result[2]).doubleValue();
            String lastOrderDate = result[3].toString();

            reportList.add(new CustomerInsightsDTO(customerName, totalOrders, totalSpent, lastOrderDate));
        }

        return reportList;
    }
}
