package com.Sales_manage.Sales_manage.user.dto;

import com.Sales_manage.Sales_manage.store_manager.entity.Store_managerEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class UserDTO {
    private String id_store_manager;
    private String passwd;
    private String name;
    private String email;
    private String number;


    public static UserDTO toMemberTestDTO(Store_managerEntity storeManagerEntity) {
        UserDTO storeManagerDTO = new UserDTO();
        storeManagerDTO.setId_store_manager(storeManagerEntity.getId_store_manager());
        storeManagerDTO.setPasswd(storeManagerEntity.getPasswd());
        storeManagerDTO.setName(storeManagerEntity.getName());
        storeManagerDTO.setEmail(storeManagerEntity.getEmail());
        storeManagerDTO.setNumber(storeManagerEntity.getNumber());
        return storeManagerDTO;
    }
}
