package com.pos.kuppiya.pointofsale.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ItemUpdateRequestDTO {
    private double balanceQty;
    private double supplierPrice;
    private double sellingPrice;
}