package com.Sales_manage.Sales_manage.store_manager.dto;

import com.Sales_manage.Sales_manage.store_manager.entity.StoreManagerEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class StoreManagerDTO {
    private String idStoremanager;
    private String passwd;
    private String name;
    private String email;
    private String phoneNumber;


    public static StoreManagerDTO toStoreManagerDTO(StoreManagerEntity storeManagerEntity) {
        StoreManagerDTO storeManagerDTO = new StoreManagerDTO();
        storeManagerDTO.setIdStoremanager(storeManagerEntity.getIdStoremanager());
        storeManagerDTO.setPasswd(storeManagerEntity.getPasswd());
        storeManagerDTO.setName(storeManagerEntity.getName());
        storeManagerDTO.setEmail(storeManagerEntity.getEmail());
        storeManagerDTO.setPhoneNumber(storeManagerEntity.getPhoneNumber());
        return storeManagerDTO;
    }
}
