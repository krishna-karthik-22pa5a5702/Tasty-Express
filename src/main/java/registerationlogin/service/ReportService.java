package registerationlogin.service;

import java.util.List;
import registerationlogin.dto.SalesReportDTO;

public interface ReportService {
    public List<SalesReportDTO> getSalesReport(Long restaurantId, String periodType);
    
}
