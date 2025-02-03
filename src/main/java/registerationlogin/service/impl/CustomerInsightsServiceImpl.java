package registerationlogin.service.impl;

import org.springframework.stereotype.Service;
import registerationlogin.dto.CustomerInsightsDTO;
import registerationlogin.repository.CustomerInsightsRepository;
import java.util.List;

@Service
public class CustomerInsightsServiceImpl implements CustomerInsightsService {
    private final CustomerInsightsRepository customerInsightsRepository;

    public CustomerInsightsServiceImpl(CustomerInsightsRepository customerInsightsRepository) {
        this.customerInsightsRepository = customerInsightsRepository;
    }

    @Override
    public List<CustomerInsightsDTO> getCustomerInsights(Long restaurantId, String periodType) {
        return customerInsightsRepository.getCustomerInsights(restaurantId, periodType);
    }
}
