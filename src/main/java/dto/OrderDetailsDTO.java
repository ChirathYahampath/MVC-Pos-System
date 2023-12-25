package dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class OrderDetailsDTO {
    private String orderId;
    private String itemCode;
    private int qty;
    private double unitPrice;
}
