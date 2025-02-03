package registerationlogin.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CustomerInsightsDTO {
    private String customerName;
    private Long totalOrders;
    private Double totalSpent;
    private String lastOrderDate;
}
