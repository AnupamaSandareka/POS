package com.pos.kuppiya.pointofsale.service.impl;

import com.pos.kuppiya.pointofsale.dto.request.RequestOrderSaveDTO;
import com.pos.kuppiya.pointofsale.entity.Order;
import com.pos.kuppiya.pointofsale.entity.OrderDetails;
import com.pos.kuppiya.pointofsale.exception.NotFoundException;
import com.pos.kuppiya.pointofsale.repository.CustomerRepo;
import com.pos.kuppiya.pointofsale.repository.OrderDetailsRepo;
import com.pos.kuppiya.pointofsale.repository.OrderRepo;
import com.pos.kuppiya.pointofsale.service.OrderService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class OrderServiceIMPL implements OrderService {

    @Autowired
    private OrderRepo orderRepo;

    @Autowired
    private CustomerRepo customerRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private OrderDetailsRepo orderDetailsRepo;

    @Override
    @Transactional
    public String addOrder(RequestOrderSaveDTO requestOrderSaveDTO) {
        Order order = new Order(
                customerRepo.getById(requestOrderSaveDTO.getCustomers()),
                requestOrderSaveDTO.getDate(),
                requestOrderSaveDTO.getTotal()
        );
        orderRepo.save(order);

        if(orderRepo.existsById(order.getOrderId())){
            List<OrderDetails> orderDetails = modelMapper.
                    map(requestOrderSaveDTO.getRequestOrderDetailsSaveDTOS(),new TypeToken<List<OrderDetails>>(){
                    }.getType());

           if(orderDetails.size()>0){
               orderDetailsRepo.saveAll(orderDetails);
           }
           return "saved";
        }
        throw new NotFoundException("no result");
    }
}
