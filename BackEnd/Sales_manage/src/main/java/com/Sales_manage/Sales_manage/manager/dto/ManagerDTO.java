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
    private String idManager;
    private String passwd;
    private String name;
    private String email;
    private String phoneNumber;
    private String position;
    private Long idBrand;


    public static ManagerDTO toManagerDTO(ManagerEntity managerEntity) {
        ManagerDTO managerDTO = new ManagerDTO();
        managerDTO.setIdManager(managerEntity.getIdManager());
        managerDTO.setPasswd(managerEntity.getPasswd());
        managerDTO.setName(managerEntity.getName());
        managerDTO.setEmail(managerEntity.getEmail());
        managerDTO.setPhoneNumber(managerEntity.getPhoneNumber());
        managerDTO.setIdBrand(managerEntity.getIdBrand().getIdBrand());
        return managerDTO;
    }
}
