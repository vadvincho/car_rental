package com.vadzimvincho.repositories.api;

import com.vadzimvincho.models.entity.CarStatus;
import com.vadzimvincho.models.enums.EnumCarStatus;

public interface CarStatusRepository extends GenericRepository<CarStatus>{
    CarStatus getByEnumName(EnumCarStatus enumCarStatus);
}
