package com.Sales_manage.Sales_manage.manager.dto;

import com.Sales_manage.Sales_manage.manager.entity.ManagerEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class ManagerDTO {
    private String id_manager;
    private String passwd;
    private String name;
    private String email;
    private String phone_number;
    private Long id_brand;


    public static ManagerDTO toManagerDTO(ManagerEntity managerEntity) {
        ManagerDTO managerDTO = new ManagerDTO();
        managerDTO.setId_manager(managerEntity.getId_manager());
        managerDTO.setPasswd(managerEntity.getPasswd());
        managerDTO.setName(managerEntity.getName());
        managerDTO.setEmail(managerEntity.getEmail());
        managerDTO.setPhone_number(managerEntity.getPhone_number());
        managerDTO.setId_brand(managerEntity.getId_brand().getId_brand());
        return managerDTO;
    }
}
