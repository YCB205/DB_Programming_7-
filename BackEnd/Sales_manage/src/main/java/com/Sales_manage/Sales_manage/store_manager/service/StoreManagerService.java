package com.Sales_manage.Sales_manage.store_manager.service;

import com.Sales_manage.Sales_manage.store_manager.dto.StoreManagerDTO;
import com.Sales_manage.Sales_manage.store_manager.entity.StoreManagerEntity;
import com.Sales_manage.Sales_manage.store_manager.repository.StoreManagerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StoreManagerService {
    private final StoreManagerRepository storeManagerRepository;

    public List<StoreManagerDTO> getAll() {
        List<StoreManagerEntity> storeManagerEntityList = storeManagerRepository.findAll();
        List<StoreManagerDTO> storeManagerDTOList = new ArrayList<>();
        for (StoreManagerEntity memberTestEntity: storeManagerEntityList){
            StoreManagerDTO storeManagerDTO = StoreManagerDTO.toStoreManagerDTO(memberTestEntity);
            storeManagerDTOList.add(storeManagerDTO);
        }
        return storeManagerDTOList;
    }

}
