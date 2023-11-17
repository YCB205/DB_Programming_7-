package com.Sales_manage.Sales_manage.user.service;

import com.Sales_manage.Sales_manage.store_manager.dto.Store_managerDTO;
import com.Sales_manage.Sales_manage.store_manager.entity.Store_managerEntity;
import com.Sales_manage.Sales_manage.store_manager.repository.Store_managerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final Store_managerRepository storeManagerRepository;

    public boolean isValidLogin(String id, String password) {
        // 아이디와 비밀번호 검증 로직을 구현 (예: 데이터베이스에서 조회 후 확인)
        Optional<Store_managerEntity> user = storeManagerRepository.findById(id);
        return user.map(entity -> entity.getPasswd().equals(password)).orElse(false);
    }


    public List<Store_managerDTO> getAll() {
        List<Store_managerEntity> storeManagerEntityList = storeManagerRepository.findAll();
        List<Store_managerDTO> storeManagerDTOList = new ArrayList<>();
        for (Store_managerEntity memberTestEntity: storeManagerEntityList){
            Store_managerDTO storeManagerDTO = Store_managerDTO.toMemberTestDTO(memberTestEntity);
            storeManagerDTOList.add(storeManagerDTO);
        }
        return storeManagerDTOList;
    }

}
