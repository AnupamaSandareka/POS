package com.pos.kuppiya.pointofsale.dto.request;

import com.pos.kuppiya.pointofsale.entity.Item;
import com.pos.kuppiya.pointofsale.entity.Order;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RequestOrderDetailsSaveDTO {
    private String itemName;
    private Double qty;
    private Double amount;
    private int orders;
    private int items;
}
