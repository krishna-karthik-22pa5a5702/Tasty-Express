package registerationlogin.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class SalesReportDTO {

    private String period;
    private Long totalOrders;
    private Double totalSales;

}
