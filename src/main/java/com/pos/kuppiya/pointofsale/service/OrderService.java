package com.pos.kuppiya.pointofsale.service;

import com.pos.kuppiya.pointofsale.dto.request.RequestOrderSaveDTO;

public interface OrderService {
    String addOrder(RequestOrderSaveDTO requestOrderSaveDTO);
}
