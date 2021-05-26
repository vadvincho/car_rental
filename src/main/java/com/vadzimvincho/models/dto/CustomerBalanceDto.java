package com.vadzimvincho.models.dto;

public class CustomerBalanceDto {
    private Long id;
    private Double money;

    public CustomerBalanceDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getMoney() {
        return money;
    }

    public void setMoney(Double money) {
        this.money = money;
    }
}
