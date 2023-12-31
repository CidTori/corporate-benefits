package com.example.backend.employee.infrastructure;

import com.example.backend.employee.domain.Employee;
import com.example.backend.employee.domain.deposit.EmployeeDeposit;
import com.example.backend.employee.domain.deposit.EmployeeGift;
import com.example.backend.employee.domain.deposit.EmployeeMeal;
import com.example.backend.employee.infrastructure.deposit.EmployeeDepositEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface EmployeeEntityMapper {
    @Mapping(target = "deposits", qualifiedByName = "toDeposit")
    Employee toDomain(EmployeeEntity employeeEntity);

    @Named("toDeposit")
    default EmployeeDeposit toDeposit(EmployeeDepositEntity employeeDepositEntity) {
        return switch (employeeDepositEntity.getType().getName()) {
            case "GIFT" -> toEmployeeGift(employeeDepositEntity);
            case "MEAL" -> toEmployeeMeal(employeeDepositEntity);
            default -> throw new IllegalArgumentException("Deposit type " + employeeDepositEntity.getType().getName() + " not supported");
        };
    }

    EmployeeGift toEmployeeGift(EmployeeDepositEntity employeeDepositEntity);
    EmployeeMeal toEmployeeMeal(EmployeeDepositEntity employeeDepositEntity);

}
