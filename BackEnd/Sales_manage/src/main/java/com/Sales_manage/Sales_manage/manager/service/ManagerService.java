package com.Sales_manage.Sales_manage.manager.service;

import com.Sales_manage.Sales_manage.manager.dto.ManagerDTO;
import com.Sales_manage.Sales_manage.manager.entity.ManagerEntity;
import com.Sales_manage.Sales_manage.manager.repository.ManagerRepository;
import com.Sales_manage.Sales_manage.user.dto.UserData;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ManagerService {
    private final ManagerRepository ManagerRepository;

//    public List<ManagerDTO> getAll() {
//        List<ManagerEntity> ManagerEntityList = ManagerRepository.findAll();
//        List<ManagerDTO> ManagerDTOList = new ArrayList<>();
//        for (ManagerEntity memberTestEntity: ManagerEntityList){
//            ManagerDTO ManagerDTO = ManagerDTO.toStoreManagerDTO(memberTestEntity);
//            ManagerDTOList.add(ManagerDTO);
//        }
//        return ManagerDTOList;
//    }


    @Transactional
    public void updateManager(UserData userData) {
        ManagerEntity ManagerEntity = ManagerRepository.findById(userData.getId()).orElse(null);

        if (ManagerEntity != null) {

            ManagerEntity.setEmail(userData.getEmail());
            if (userData.getPasswd().equals("")){
                ManagerEntity.setPasswd(ManagerEntity.getPasswd());
            } else {
                ManagerEntity.setPasswd(userData.getPasswd());
            }
            ManagerEntity.setName(userData.getName());
            ManagerEntity.setPhoneNumber(userData.getPhoneNumber());

            ManagerRepository.save(ManagerEntity);
        }
    }

//    public List<ManagerDTO> getFilterName(String name) {
//        List<ManagerEntity> filteredManagers = ManagerRepository.findByNameContainingIgnoreCase(name);
//        List<ManagerDTO> filteredManagerDTOs = new ArrayList<>();
//
//        for (ManagerEntity ManagerEntity : filteredManagers) {
//            ManagerDTO ManagerDTO = ManagerDTO.toStoreManagerDTONotInPasswd(ManagerEntity);
//            filteredManagerDTOs.add(ManagerDTO);
//        }
//
//        return filteredManagerDTOs;
//    }


    public void deleteManager(String idManager) {
        ManagerEntity ManagerEntity = ManagerRepository.findById(idManager).orElse(null);
        assert ManagerEntity != null;
        ManagerRepository.delete(ManagerEntity);
    }

    public boolean checkManagerId(String idManager) {
        ManagerEntity ManagerEntity = ManagerRepository.findById(idManager).orElse(null);;
        if (ManagerEntity != null) {
            return false; // 아이디가 DB에 있음
        } else {return true;} // 아이디가 DB에 없음
    }

    public void createManager(ManagerDTO ManagerDTO) {
        ManagerEntity ManagerEntity = new ManagerEntity();

        ManagerEntity.setIdManager(ManagerDTO.getIdManager());
        ManagerEntity.setName(ManagerDTO.getName());
        ManagerEntity.setPasswd(ManagerDTO.getPasswd());
        ManagerEntity.setEmail(ManagerDTO.getEmail());
        ManagerEntity.setPhoneNumber(ManagerDTO.getPhoneNumber());
        ManagerRepository.save(ManagerEntity);
    }

}
