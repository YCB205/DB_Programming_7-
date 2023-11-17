package com.Sales_manage.Sales_manage.store_manager.dto;

import com.Sales_manage.Sales_manage.store_manager.entity.Store_managerEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class Store_managerDTO {
    private String id_store_manager;
    private String passwd;
    private String name;
    private String email;
    private String number;


    public static Store_managerDTO toMemberTestDTO(Store_managerEntity storeManagerEntity) {
        Store_managerDTO storeManagerDTO = new Store_managerDTO();
        storeManagerDTO.setId_store_manager(storeManagerEntity.getId_store_manager());
        storeManagerDTO.setPasswd(storeManagerEntity.getPasswd());
        storeManagerDTO.setName(storeManagerEntity.getName());
        storeManagerDTO.setEmail(storeManagerEntity.getEmail());
        storeManagerDTO.setNumber(storeManagerEntity.getNumber());
        return storeManagerDTO;
    }
}
