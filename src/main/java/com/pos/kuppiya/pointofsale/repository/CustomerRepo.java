package com.pos.kuppiya.pointofsale.repository;

import com.pos.kuppiya.pointofsale.dto.request.CustomerDetailsUpdateDTO;
import com.pos.kuppiya.pointofsale.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@EnableJpaRepositories
@Repository
@Transactional
public interface CustomerRepo extends JpaRepository<Customer,Integer> {

    List<Customer> findAllByCustomerNameEquals(String customerName);

    List<Customer> findAllByActiveStateEquals(boolean b);

    @Modifying
    @Query(value = "update customer set customer_name=?1, nic=?2 where customer_id=?3",nativeQuery = true)
    void updateCustomerByQuery(String customerName, String nic, int customerId);

    Customer findCustomerByNic(String nic);

//    @Modifying
//    @Query(value = "update customer set customer_name=?1 , customer_salary=?2, nic=?3 where customer_id=?4",nativeQuery = true)
//    void updateCustomerDetails(String customerName, double salary, String nic, int customerId);
}