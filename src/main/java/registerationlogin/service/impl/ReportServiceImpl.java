package registerationlogin.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import registerationlogin.dto.SalesReportDTO;
import registerationlogin.repository.SalesReportRepository;
import registerationlogin.service.ReportService;

@Service
public class ReportServiceImpl implements ReportService {

    private SalesReportRepository salesReportRepository;

    public ReportServiceImpl(SalesReportRepository salesReportRepository) {
        this.salesReportRepository = salesReportRepository;
    }

    @Override
    public List<SalesReportDTO> getSalesReport(Long restaurantId, String periodType) {
        return salesReportRepository.getSalesReport(restaurantId, periodType);
    }
}
