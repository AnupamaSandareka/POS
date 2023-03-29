package com.pos.kuppiya.pointofsale.util.mappers;

import com.pos.kuppiya.pointofsale.dto.CustomerDTO;
import com.pos.kuppiya.pointofsale.dto.response.ResponseActiveCustomerDTO;
import com.pos.kuppiya.pointofsale.dto.response.ResponseByIdDTO;
import com.pos.kuppiya.pointofsale.entity.Customer;
import org.mapstruct.Mapper;
import java.util.List;

@Mapper(componentModel = "spring")
public interface CustomerMapper {
    CustomerDTO entityToDto(Customer customer);

    List<CustomerDTO> entityListToDtoList(List<Customer> customerList);

    List<ResponseActiveCustomerDTO> entityListToDtoListOnlyName(List<Customer> customerList);

    ResponseByIdDTO entityToResponseByIdDto(Customer customer);
}
