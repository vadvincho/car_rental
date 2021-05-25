package com.vadzimvincho.models.entity;

import com.vadzimvincho.models.enums.EnumOrderStatus;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

@Entity
@Table(name = "order_status")
public class OrderStatus extends BaseEntity {

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private EnumOrderStatus status;

    public EnumOrderStatus getStatus() {
        return status;
    }

    public void setStatus(EnumOrderStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "OrderStatus{" +
                "id=" + id +
                ", status=" + status +
                "}";
    }
}
