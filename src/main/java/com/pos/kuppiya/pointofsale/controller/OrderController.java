package com.pos.kuppiya.pointofsale.controller;

import com.pos.kuppiya.pointofsale.dto.ItemDTO;
import com.pos.kuppiya.pointofsale.dto.request.ItemSaveRequestDTO;
import com.pos.kuppiya.pointofsale.dto.request.RequestOrderDetailsSaveDTO;
import com.pos.kuppiya.pointofsale.dto.request.RequestOrderSaveDTO;
import com.pos.kuppiya.pointofsale.service.OrderService;
import com.pos.kuppiya.pointofsale.util.StandardResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/order")
@CrossOrigin
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping(path = "/save")
    public ResponseEntity<StandardResponse> addOrder(@RequestBody RequestOrderSaveDTO requestOrderSaveDTO){
        String result = orderService.addOrder(requestOrderSaveDTO);
        return new ResponseEntity<StandardResponse>(
                new StandardResponse(201," item successfully saved",result),
                HttpStatus.CREATED
        );
    }
}
