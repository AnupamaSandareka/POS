package com.pos.kuppiya.pointofsale.dto.request;

import com.pos.kuppiya.pointofsale.entity.Customer;
import com.pos.kuppiya.pointofsale.entity.OrderDetails;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RequestOrderSaveDTO {
    private int customers;
    private Date date;
    private double total;
    private List<RequestOrderDetailsSaveDTO> requestOrderDetailsSaveDTOS;
}