package com.pos.kuppiya.pointofsale.controller;
import com.pos.kuppiya.pointofsale.dto.CustomerDTO;
import com.pos.kuppiya.pointofsale.dto.request.CustomerDetailsUpdateDTO;
import com.pos.kuppiya.pointofsale.dto.request.CustomerSaveRequestDTO;
import com.pos.kuppiya.pointofsale.dto.request.CustomerUpdateQueryRequestDTO;
import com.pos.kuppiya.pointofsale.dto.request.CustomerUpdateRequestDTO;
import com.pos.kuppiya.pointofsale.dto.response.ResponseActiveCustomerDTO;
import com.pos.kuppiya.pointofsale.dto.response.ResponseByIdDTO;
import com.pos.kuppiya.pointofsale.service.CustomerService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("api/v1/customer")
@CrossOrigin
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @PostMapping(path = "/save")
    public String saveCustomer(@RequestBody CustomerSaveRequestDTO customerSaveRequestDTO){
        String result = customerService.addCustomer(customerSaveRequestDTO);
        return result;
    }

    @PutMapping(path = "/update")
    public String updateCustomer(@RequestBody CustomerUpdateRequestDTO customerUpdateRequestDTO){
        String result = customerService.updateCustomer(customerUpdateRequestDTO);
        return result;
    }

    @GetMapping(
            path = "/get-by-id",
            params = "id"
    )
    public CustomerDTO getCustomerById(@RequestParam(value = "id") int customerId){
        CustomerDTO customerDTO = customerService.getCustomerById(customerId);
        return customerDTO;
    }

    @GetMapping(
            path = {"get-all-customers"}
    )
    public List<CustomerDTO> getAllCustomers(){
        List<CustomerDTO> customerDTOList = customerService.getAllCustomers();
        return customerDTOList;
    }

    @DeleteMapping(
            path = {"/delete-by-id/{id}"}
    )
    public String deleteCustomer(@PathVariable (value="id") int customerId) throws NotFoundException {
        Boolean deletedCustomer = customerService.deleteCustomer(customerId);
        return "deleted";
    }

    @GetMapping(
            path = {"/get-by-name"},
            params = {"name"}
    )
    public List<CustomerDTO> getCustomerByName(@RequestParam(value = "name") String customerName) throws NotFoundException {
        List<CustomerDTO> customerDTOList = customerService.getCustomerByName(customerName);
        return customerDTOList;
    }

    @GetMapping(
            path = {"/get-by-active-state"}
    )
    public List<CustomerDTO> getCustomerByActiveState() throws NotFoundException{
        List<CustomerDTO> customerDTOList = customerService.getCustomerByActiveState();
        return customerDTOList;
    }

    @GetMapping(
            path = {"/get-only-name-by-active-state"}
    )
    public List<ResponseActiveCustomerDTO> getOnlyCustomerNameByActiveState() throws NotFoundException{
        List<ResponseActiveCustomerDTO> getCustomers = customerService.getOnlyCustomerNameByActiveState();
        return getCustomers;
    }

    @PutMapping(path = "/update-query/{id}")
    public String updateCustomerByQuery(@RequestBody CustomerUpdateQueryRequestDTO customerUpdateQueryRequestDTO,
                                        @PathVariable(value = "id") int customerId){
        String result = customerService.updateCustomerByQuery(customerUpdateQueryRequestDTO,customerId);
        return result;
    }

    @GetMapping(
            path = {"/get-by-nic"},
            params = {"nic"}
    )
    public CustomerDTO getCustomerByNic(@RequestParam(value = "nic") String nic) throws NotFoundException {
        CustomerDTO customerDTO = customerService.getCustomerByNic(nic);
        return customerDTO;
    }

    @GetMapping(
            path = {"/get-salary-and-address-by-id/{id}"}
    )
    public ResponseByIdDTO getSalaryAndAddressById(@PathVariable(value = "id") int customerId) throws NotFoundException {
        ResponseByIdDTO responseByIdDTO = customerService.getSalaryAndAddressById(customerId);
        return responseByIdDTO;
    }

    @PutMapping(
            path = {"/update-details"},
            params ={"id"}
    )
    public String updateCustomerDetails(@RequestBody CustomerDetailsUpdateDTO customerDetailsUpdateDTO,
                                        @RequestParam(value = "id") int customerId){
        String result = customerService.updateCustomerDetails(customerDetailsUpdateDTO,customerId);
        return result;
    }

    @GetMapping(
            path = {"/get-only-active-customer-details/{id}"}
    )
    public CustomerDTO getOnlyActiveCustomerDetails(@PathVariable(value = "id") int customerId) throws NotFoundException {
        CustomerDTO customerDTO = customerService.getOnlyActiveCustomerDetails(customerId);
        return customerDTO;
    }

    @GetMapping(
            path = {"/get-only-active-customer-details"},
            params = {"state"}
    )
    public List<CustomerDTO> getCustomersByState(@RequestParam(value = "state") String state){
        List<CustomerDTO> customerDTOList = customerService.getCustomersByState(state);
        return customerDTOList;
    }
}