package com.pos.kuppiya.pointofsale.service.impl;

import com.pos.kuppiya.pointofsale.dto.CustomerDTO;
import com.pos.kuppiya.pointofsale.dto.request.CustomerDetailsUpdateDTO;
import com.pos.kuppiya.pointofsale.dto.request.CustomerSaveRequestDTO;
import com.pos.kuppiya.pointofsale.dto.request.CustomerUpdateQueryRequestDTO;
import com.pos.kuppiya.pointofsale.dto.request.CustomerUpdateRequestDTO;
import com.pos.kuppiya.pointofsale.dto.response.ResponseActiveCustomerDTO;
import com.pos.kuppiya.pointofsale.dto.response.ResponseByIdDTO;
import com.pos.kuppiya.pointofsale.entity.Customer;
import com.pos.kuppiya.pointofsale.repository.CustomerRepo;
import com.pos.kuppiya.pointofsale.service.CustomerService;
import com.pos.kuppiya.pointofsale.util.mappers.CustomerMapper;
import javassist.NotFoundException;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerServiceIMPL implements CustomerService {

    @Autowired
    private CustomerRepo customerRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    CustomerMapper customerMapper;

    @Override
    public CustomerDTO getCustomerById(int customerId) {
        Optional<Customer> customer = customerRepo.findById(customerId);
        if(customer.isPresent()){

//            CustomerDTO customerDTO = new CustomerDTO(
//                    customer.get().getCustomerId(),
//                    customer.get().getCustomerName(),
//                    customer.get().getCustomerAddress(),
//                    customer.get().getSalary(),
//                    customer.get().getContactNumbers(),
//                    customer.get().getNic(),
//                    customer.get().isActiveState()
//            );
//            return customerDTO;

            //CustomerDTO customerDTO = modelMapper.map(customer.get(),CustomerDTO.class);
            CustomerDTO customerDTO = customerMapper.entityToDto(customer.get());
            return customerDTO;
        }
        else{
            return null;
        }
    }

    @Override
    public List<CustomerDTO> getAllCustomers() {
        List<Customer> customerList = customerRepo.findAll();

//        List<CustomerDTO> customerDTOList = new ArrayList<>();
//        for(Customer c : customerList){
//            CustomerDTO customerDTO = new CustomerDTO(
//                    c.getCustomerId(),
//                    c.getCustomerName(),
//                    c.getCustomerAddress(),
//                    c.getSalary(),
//                    c.getContactNumbers(),
//                    c.getNic(),
//                    c.isActiveState()
//            );
//            customerDTOList.add(customerDTO);
//        }
//        return customerDTOList;

//        List<CustomerDTO> customerDTOList = modelMapper.
//                map(customerList,new TypeToken<List<CustomerDTO>>(){}.getType());
        List<CustomerDTO> customerDTOList = customerMapper.entityListToDtoList(customerList);
        return customerDTOList;
    }

    @Override
    public Boolean deleteCustomer(int customerId) throws NotFoundException {
        if (customerRepo.existsById(customerId)) {
            customerRepo.deleteById(customerId);
        }
        else{
            throw new NotFoundException("this id is not exist in database.");
        }
        return true;
    }

    @Override
    public List<CustomerDTO> getCustomerByName(String customerName) throws NotFoundException {
        List<Customer> customerList = customerRepo.findAllByCustomerNameEquals(customerName);
        if(customerList.size() != 0 ){
            List<CustomerDTO> customerDTOList = modelMapper.
                    map(customerList,new TypeToken<List<Customer>>(){}.getType());
            return customerDTOList;
        }
        else {
            throw new NotFoundException("no result.");
        }
    }

    @Override
    public List<CustomerDTO> getCustomerByActiveState() throws NotFoundException{
        List<Customer> customerList = customerRepo.findAllByActiveStateEquals(true);
        if(customerList.size() != 0){
            List<CustomerDTO> customerDTOList = customerMapper.entityListToDtoList(customerList);
            return customerDTOList;
        }
        else {
            throw new NotFoundException("no active customers found.");
        }
    }

    @Override
    public List<ResponseActiveCustomerDTO> getOnlyCustomerNameByActiveState() throws NotFoundException {
        List<Customer> customerList = customerRepo.findAllByActiveStateEquals(true);
        if(customerList.size() != 0){
            List<ResponseActiveCustomerDTO> responseActiveCustomerDTOList = customerMapper.entityListToDtoListOnlyName(customerList);
            return responseActiveCustomerDTOList;
        }
        else {
            throw new NotFoundException("no active customers found.");
        }
    }

    @Override
    public String updateCustomerByQuery(CustomerUpdateQueryRequestDTO customerUpdateQueryRequestDTO, int customerId) {
        if (customerRepo.existsById(customerId)) {
            customerRepo.updateCustomerByQuery(customerUpdateQueryRequestDTO.getCustomerName(),customerUpdateQueryRequestDTO.getNic(),customerId);
            return ("updated id "+customerId);
        }
        else {
            return "this id is not exist.";
        }
    }

    @Override
    public CustomerDTO getCustomerByNic(String nic) throws NotFoundException {
        Customer customer = customerRepo.findCustomerByNic(nic);
        if(customer!=null){
            CustomerDTO customerDTO = customerMapper.entityToDto(customer);
            return customerDTO;
        }
        else {
            throw new com.pos.kuppiya.pointofsale.exception.NotFoundException("not customer for this nic");
        }
    }

    @Override
    public ResponseByIdDTO getSalaryAndAddressById(int customerId) throws NotFoundException {
        Optional<Customer> customer = customerRepo.findById(customerId);
        if(customer.isPresent()){
            ResponseByIdDTO responseByIdDTO = customerMapper.entityToResponseByIdDto(customer.get());
            return responseByIdDTO;
        }
        else{
            throw new NotFoundException("not found");
        }
    }

    @Override
    public String updateCustomerDetails(CustomerDetailsUpdateDTO customerDetailsUpdateDTO, int customerId) {

        if(customerRepo.existsById(customerId)){
            Customer customer = customerRepo.getById(customerId);
            customer.setCustomerName(customerDetailsUpdateDTO.getCustomerName());
            customer.setNic(customerDetailsUpdateDTO.getNic());
            customer.setSalary(customerDetailsUpdateDTO.getSalary());
            return "update successful.";
        }
        else{
            return "no customer for this id.";
        }
    }

    @Override
    public CustomerDTO getOnlyActiveCustomerDetails(int customerId) throws NotFoundException {
        Optional<Customer> customer = customerRepo.findById(customerId);
        if (customer.isPresent()){
            if(customer.get().isActiveState()==true) {
                CustomerDTO customerDTO = customerMapper.entityToDto(customer.get());
                return customerDTO;
            }
            else{
                throw new NotFoundException("this is an inactive customer.");
            }
        }
        else{
            throw new NotFoundException("no customer for this id.");
        }
    }

    @Override
    public List<CustomerDTO> getCustomersByState(String state) {
        if(state.equalsIgnoreCase("active")){
            List<Customer> customerList = customerRepo.findAllByActiveStateEquals(true);
            if(customerList.size() != 0){
                List<CustomerDTO> customerDTOList = customerMapper.entityListToDtoList(customerList);
                return customerDTOList;
            }
        }
        else if(state.equalsIgnoreCase("inactive")){
            List<Customer> customerList = customerRepo.findAllByActiveStateEquals(false);
            if(customerList.size() != 0){
                List<CustomerDTO> customerDTOList = customerMapper.entityListToDtoList(customerList);
                return customerDTOList;
            }
        }
        else {
            List<Customer> customerList = customerRepo.findAll();
            if(customerList.size() != 0) {
                List<CustomerDTO> customerDTOList = customerMapper.entityListToDtoList(customerList);
                return customerDTOList;
            }
        }
        return null;
    }

    @Override
    public String addCustomer(CustomerSaveRequestDTO customerSaveRequestDTO) {

        Customer customer = new Customer(
                customerSaveRequestDTO.getCustomerName(),
                customerSaveRequestDTO.getCustomerAddress(),
                customerSaveRequestDTO.getSalary(),
                customerSaveRequestDTO.getContactNumbers(),
                customerSaveRequestDTO.getNic()
        );

        if (!customerRepo.existsById(customer.getCustomerId())) {
            customerRepo.save(customer);
            return customer.getCustomerName() + " saved";
        }
        else {
            System.out.println("customer id already exists");
            return "customer id already exists";
        }
    }

    @Override
    public String updateCustomer(CustomerUpdateRequestDTO customerUpdateRequestDTO) {

        if(customerRepo.existsById(customerUpdateRequestDTO.getCustomerId())){
            Customer customer = customerRepo.getById(customerUpdateRequestDTO.getCustomerId());
            customer.setCustomerName(customerUpdateRequestDTO.getCustomerName());
            customer.setCustomerAddress(customerUpdateRequestDTO.getCustomerAddress());
            customer.setSalary(customerUpdateRequestDTO.getSalary());
            customer.setContactNumbers(customerUpdateRequestDTO.getContactNumbers());
            customer.setNic(customerUpdateRequestDTO.getNic());
            customer.setActiveState(customerUpdateRequestDTO.isActiveState());

            customerRepo.save(customer);
            return customer.getCustomerName()+" is successfully updated.";
        }
        else{
            return "this customer is not in database.";
        }
    }
}
