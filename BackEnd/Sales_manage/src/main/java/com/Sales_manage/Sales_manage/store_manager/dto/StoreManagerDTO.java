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
    private String idStoreManager;
    private String passwd;
    private String name;
    private String email;
    private String number;


    public static StoreManagerDTO toMemberTestDTO(StoreManagerEntity storeManagerEntity) {
        StoreManagerDTO storeManagerDTO = new StoreManagerDTO();
        storeManagerDTO.setIdStoreManager(storeManagerEntity.getIdStoreManager());
        storeManagerDTO.setPasswd(storeManagerEntity.getPasswd());
        storeManagerDTO.setName(storeManagerEntity.getName());
        storeManagerDTO.setEmail(storeManagerEntity.getEmail());
        storeManagerDTO.setNumber(storeManagerEntity.getNumber());
        return storeManagerDTO;
    }
}
