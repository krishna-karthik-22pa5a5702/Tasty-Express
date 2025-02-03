package registerationlogin.controller;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import registerationlogin.dto.CustomerInsightsDTO;
import registerationlogin.dto.SalesReportDTO;
import registerationlogin.service.ReportService;
import registerationlogin.service.RestaurantService;
import registerationlogin.service.impl.CustomerInsightsService;

@RestController
@RequestMapping("/restaurant/api/reports")
public class ReportController {

    private final ReportService reportService;
    private final RestaurantService restaurantService;
    private final CustomerInsightsService customerInsightsService;

    public ReportController(ReportService reportService, RestaurantService restaurantService,
            CustomerInsightsService customerInsightsService) {
        this.reportService = reportService;
        this.restaurantService = restaurantService;
        this.customerInsightsService = customerInsightsService;
    }

    @GetMapping("/sales")
    public ResponseEntity<List<SalesReportDTO>> getSalesReport(
            @RequestParam String period, @RequestParam Long restaurantId) {

        System.out.println(period);
        System.out.println(restaurantId);

        return ResponseEntity.ok(reportService.getSalesReport(restaurantId, period));
    }

    @GetMapping("/customer-insights")
    public ResponseEntity<List<CustomerInsightsDTO>> getCustomerInsights(
            @RequestParam Long restaurantId, @RequestParam String period) {
        System.out.println(restaurantId);

        return ResponseEntity.ok(customerInsightsService.getCustomerInsights(restaurantId, period));
    }

}
