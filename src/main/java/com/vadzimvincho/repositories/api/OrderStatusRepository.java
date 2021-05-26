package com.vadzimvincho.repositories.api;

import com.vadzimvincho.models.entity.OrderStatus;
import com.vadzimvincho.models.enums.EnumOrderStatus;

public interface OrderStatusRepository extends GenericRepository<OrderStatus>{
    OrderStatus getByEnumName(EnumOrderStatus enumOrderStatus);
}
