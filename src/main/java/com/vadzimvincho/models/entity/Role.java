package com.vadzimvincho.models.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "role")
@Data
public class Role extends BaseEntity {

    @Column
    private String name;
}
