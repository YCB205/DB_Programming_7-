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
    private final ManagerRepository managerRepository;


    public String getPostion(String loggedInUserId) {
        ManagerEntity manager = managerRepository.findById(loggedInUserId).orElse(null);
        return manager.getPosition();
    }

    public List<ManagerDTO> getAll() {
        List<ManagerEntity> ManagerEntityList = managerRepository.findByPosition("manager");
        List<ManagerDTO> ManagerDTOList = new ArrayList<>();
        for (ManagerEntity memberTestEntity: ManagerEntityList){
            ManagerDTO managerDTO = ManagerDTO.toManagerDTONotInPasswdDTO(memberTestEntity);
            ManagerDTOList.add(managerDTO);
        }
        return ManagerDTOList;
    }

    public ManagerEntity getManagerInfo(String id) {
        return managerRepository.findById(id).orElse(null);
    }


    @Transactional
    public void updateManager(UserData userData) {
        ManagerEntity managerEntity = managerRepository.findById(userData.getId()).orElse(null);

        if (managerEntity != null) {

            managerEntity.setEmail(userData.getEmail());
            if (userData.getPasswd().equals("")){
                managerEntity.setPasswd(managerEntity.getPasswd());
            } else {
                managerEntity.setPasswd(userData.getPasswd());
            }
            managerEntity.setName(userData.getName());
            managerEntity.setPhoneNumber(userData.getPhoneNumber());

            managerRepository.save(managerEntity);
        }
    }

    public List<ManagerDTO> getFilterName(String name) {
        List<ManagerEntity> filteredManagers = managerRepository.findByNameContainingAndPosition(name, "manager");
        List<ManagerDTO> filteredManagerDTOs = new ArrayList<>();

        for (ManagerEntity ManagerEntity : filteredManagers) {
            ManagerDTO managerDTO = ManagerDTO.toManagerDTONotInPasswdDTO(ManagerEntity);
            filteredManagerDTOs.add(managerDTO);
        }

        return filteredManagerDTOs;
    }


    public void deleteManager(String idManager) {
        ManagerEntity ManagerEntity = managerRepository.findById(idManager).orElse(null);
        assert ManagerEntity != null;
        managerRepository.delete(ManagerEntity);
    }

    public boolean checkManagerId(String idManager) {
        ManagerEntity ManagerEntity = managerRepository.findById(idManager).orElse(null);;
        if (ManagerEntity != null) {
            return false; // 아이디가 DB에 있음
        } else {return true;} // 아이디가 DB에 없음
    }

    public void createManager(ManagerDTO managerDTO, String adminId) {
        ManagerEntity ManagerEntity = new ManagerEntity();

        ManagerEntity.setIdManager(managerDTO.getIdManager());
        ManagerEntity.setName(managerDTO.getName());
        ManagerEntity.setPasswd(managerDTO.getPasswd());
        ManagerEntity.setEmail(managerDTO.getEmail());
        ManagerEntity.setPhoneNumber(managerDTO.getPhoneNumber());

        ManagerEntity ForBrandId= managerRepository.findById(adminId).orElse(null);
        ManagerEntity.setIdBrand(ForBrandId.getIdBrand());
        ManagerEntity.setPosition("manager");
        managerRepository.save(ManagerEntity);
    }

}
