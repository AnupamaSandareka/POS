package com.pos.kuppiya.pointofsale.service;

import com.pos.kuppiya.pointofsale.dto.CustomerDTO;
import com.pos.kuppiya.pointofsale.dto.request.CustomerDetailsUpdateDTO;
import com.pos.kuppiya.pointofsale.dto.request.CustomerSaveRequestDTO;
import com.pos.kuppiya.pointofsale.dto.request.CustomerUpdateQueryRequestDTO;
import com.pos.kuppiya.pointofsale.dto.request.CustomerUpdateRequestDTO;
import com.pos.kuppiya.pointofsale.dto.response.ResponseActiveCustomerDTO;
import com.pos.kuppiya.pointofsale.dto.response.ResponseByIdDTO;
import javassist.NotFoundException;

import java.util.List;

public interface CustomerService {

    String addCustomer(CustomerSaveRequestDTO customerSaveRequestDTO);

    String updateCustomer(CustomerUpdateRequestDTO customerUpdateRequestDTO);

    CustomerDTO getCustomerById(int customerId);

    List<CustomerDTO> getAllCustomers();

    Boolean deleteCustomer(int customerId) throws NotFoundException;

    List<CustomerDTO> getCustomerByName(String customerName) throws NotFoundException;

    List<CustomerDTO> getCustomerByActiveState() throws NotFoundException;

    List<ResponseActiveCustomerDTO> getOnlyCustomerNameByActiveState() throws NotFoundException;

    String updateCustomerByQuery(CustomerUpdateQueryRequestDTO customerUpdateQueryRequestDTO, int customerId);

    CustomerDTO getCustomerByNic(String nic) throws NotFoundException;

    ResponseByIdDTO getSalaryAndAddressById(int customerId) throws NotFoundException;

    String updateCustomerDetails(CustomerDetailsUpdateDTO customerDetailsUpdateDTO, int customerId);

    CustomerDTO getOnlyActiveCustomerDetails(int customerId) throws NotFoundException;

    List<CustomerDTO> getCustomersByState(String state);
}
