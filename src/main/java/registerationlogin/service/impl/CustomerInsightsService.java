package registerationlogin.service.impl;

import java.util.List;

import registerationlogin.dto.CustomerInsightsDTO;

public interface CustomerInsightsService {
    List<CustomerInsightsDTO> getCustomerInsights(Long restaurantId, String periodType);
}
