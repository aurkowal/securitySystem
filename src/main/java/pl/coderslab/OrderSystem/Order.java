package pl.coderslab.OrderSystem;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class Order {
    private String orderId;
    private String status;

    private LocalDateTime date;

}