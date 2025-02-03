package registerationlogin.repository;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import registerationlogin.dto.SalesReportDTO;
import jakarta.persistence.Query;

@Repository
public class SalesReportRepository {

    private EntityManager entityManager;

    public SalesReportRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public List<SalesReportDTO> getSalesReport(Long restaurantId, String periodType) {
        String sql = "";

        if ("daily".equalsIgnoreCase(periodType)) {
            sql = "SELECT DATE(order_date) AS period, COUNT(*) AS total_orders, SUM(total_price) AS total_sales " +
                    "FROM food_order WHERE restaurant_id = :restaurantId AND order_date >= CURDATE() " +
                    "GROUP BY period";
        } else if ("weekly".equalsIgnoreCase(periodType)) {
            sql = "SELECT YEARWEEK(order_date) AS period, COUNT(*) AS total_orders, SUM(total_price) AS total_sales " +
                    "FROM food_order WHERE restaurant_id = :restaurantId AND order_date >= DATE_SUB(CURDATE(), INTERVAL 7 DAY) "
                    +
                    "GROUP BY period";
        } else if ("monthly".equalsIgnoreCase(periodType)) {
            sql = "SELECT DATE_FORMAT(order_date, '%Y-%m') AS period, COUNT(*) AS total_orders, SUM(total_price) AS total_sales "
                    +
                    "FROM food_order WHERE restaurant_id = :restaurantId AND order_date >= DATE_SUB(CURDATE(), INTERVAL 1 MONTH) "
                    +
                    "GROUP BY period";
        }

        Query query = entityManager.createNativeQuery(sql);
        query.setParameter("restaurantId", restaurantId);

        // Now manually map the result
        List<Object[]> results = query.getResultList();
        List<SalesReportDTO> reportList = new ArrayList<>();

        // Date formatting
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        for (Object[] result : results) {
            // Check if the period is a Date type and convert it to String
            String period = "";
            if (result[0] instanceof java.sql.Date) {
                java.sql.Date date = (java.sql.Date) result[0];
                period = dateFormat.format(date); // Convert Date to String
            } else if (result[0] instanceof String) {
                period = (String) result[0]; // Already in string format
            }

            Long totalOrders = ((Number) result[1]).longValue(); // total orders
            Double totalSales = ((Number) result[2]).doubleValue(); // total sales

            reportList.add(new SalesReportDTO(period, totalOrders, totalSales));
        }

        return reportList;
    }
}
