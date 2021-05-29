package com.vadzimvincho.services.api;

import com.vadzimvincho.models.dto.CustomerBalanceDto;
import com.vadzimvincho.models.entity.Customer;

public interface CustomerService extends GenericService<Customer> {
    void topUpBalance(CustomerBalanceDto money);
}
