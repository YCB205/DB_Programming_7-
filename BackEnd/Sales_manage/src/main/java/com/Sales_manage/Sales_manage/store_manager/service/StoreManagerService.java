package com.Sales_manage.Sales_manage.store_manager.service;

import com.Sales_manage.Sales_manage.brand_office.entity.BrandOfficeEntity;
import com.Sales_manage.Sales_manage.brand_office.ropository.BrandOfficeRepository;
import com.Sales_manage.Sales_manage.store_manager.dto.StoreManagerDTO;
import com.Sales_manage.Sales_manage.store_manager.entity.StoreManagerEntity;
import com.Sales_manage.Sales_manage.store_manager.repository.StoreManagerRepository;
import com.Sales_manage.Sales_manage.user.dto.UserData;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StoreManagerService {
    private final StoreManagerRepository storeManagerRepository;
    private final BrandOfficeRepository brandOfficeRepository;

    public List<StoreManagerDTO> getAll() {
        List<StoreManagerEntity> storeManagerEntityList = storeManagerRepository.findAll();
        List<StoreManagerDTO> storeManagerDTOList = new ArrayList<>();
        for (StoreManagerEntity memberTestEntity: storeManagerEntityList){
            StoreManagerDTO storeManagerDTO = StoreManagerDTO.toStoreManagerDTONotInPasswd(memberTestEntity);
            storeManagerDTOList.add(storeManagerDTO);
        }
        return storeManagerDTOList;
    }


    public StoreManagerEntity getStoreManagerInfo(String id) {
        return storeManagerRepository.findById(id).orElse(null);
    }

    public BrandOfficeEntity getBrandOfficeInfo(StoreManagerEntity storeManagerEntity) {
        return brandOfficeRepository.findByIdStoremanger(storeManagerEntity);
    }

    public void updateStoreManager(UserData userData) {
        StoreManagerEntity storeManagerEntity = storeManagerRepository.findById(userData.getId()).orElse(null);

        if (storeManagerEntity != null) {

            storeManagerEntity.setEmail(userData.getEmail());
            if (userData.getPasswd().equals("")){
                storeManagerEntity.setPasswd(storeManagerEntity.getPasswd());
            } else {
                storeManagerEntity.setPasswd(userData.getPasswd());
            }
            storeManagerEntity.setName(userData.getName());
            storeManagerEntity.setPhoneNumber(userData.getPhoneNumber());

            storeManagerRepository.save(storeManagerEntity);
        }
    }

    public List<StoreManagerDTO> getFilterName(String name) {
        List<StoreManagerEntity> filteredStoreManagers = storeManagerRepository.findByNameContainingIgnoreCase(name);
        List<StoreManagerDTO> filteredStoreManagerDTOs = new ArrayList<>();

        for (StoreManagerEntity storeManagerEntity : filteredStoreManagers) {
            StoreManagerDTO storeManagerDTO = StoreManagerDTO.toStoreManagerDTONotInPasswd(storeManagerEntity);
            filteredStoreManagerDTOs.add(storeManagerDTO);
        }

        return filteredStoreManagerDTOs;
    }


}
