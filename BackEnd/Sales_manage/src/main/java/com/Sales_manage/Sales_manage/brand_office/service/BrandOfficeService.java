package com.Sales_manage.Sales_manage.brand_office.service;

import com.Sales_manage.Sales_manage.brand_office.entity.BrandOfficeEntity;
import com.Sales_manage.Sales_manage.brand_office.ropository.BrandOfficeRepository;
import com.Sales_manage.Sales_manage.brand_office.dto.BrandOfficeDTO;
import com.Sales_manage.Sales_manage.store_manager.dto.StoreManagerDTO;
import com.Sales_manage.Sales_manage.store_manager.entity.StoreManagerEntity;
import com.Sales_manage.Sales_manage.store_manager.repository.StoreManagerRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BrandOfficeService {
    private final BrandOfficeRepository brandOfficeRepository;
    private final StoreManagerRepository storeManagerRepository;

    public List<BrandOfficeDTO> getAll() {
        List<BrandOfficeEntity> brandOfficeEntityList = brandOfficeRepository.findAll();
        List<BrandOfficeDTO> brandOfficeDTOList = new ArrayList<>();
        for (BrandOfficeEntity brandOfficeEntity : brandOfficeEntityList) {
            BrandOfficeDTO brandOfficeDTO = BrandOfficeDTO.toBrandOfficeDTO(brandOfficeEntity);
            brandOfficeDTOList.add(brandOfficeDTO);
        }
        return brandOfficeDTOList;
    }

    public List<List<Object>> findBranchStoreManagers(String officeName, String name) {
        // 브랜드 사무실과 상점 관리자 간의 관계를 조회
        List<BrandOfficeEntity> brandOfficeEntities = brandOfficeRepository.findByOfficeNameContainingAndOperationalStatusIsTrue(officeName);

        // 결과를 저장할 리스트
        List<List<Object>> result = new ArrayList<>();

        // 브랜드 사무실과 관련된 정보를 리스트에 추가
        for (BrandOfficeEntity brandOfficeEntity : brandOfficeEntities) {
            List<Object> brandAndStoreManagers = new ArrayList<>();
            BrandOfficeDTO brandOfficeDTO = BrandOfficeDTO.toBrandOfficeDTO(brandOfficeEntity);
            brandAndStoreManagers.add(brandOfficeDTO);

            // 브랜드 사무실에 속한 상점 관리자의 ID를 추출
            StoreManagerEntity storeManagerEntity = brandOfficeEntity.getIdStoremanger();

            // idStoremanger가 null이 아닌 경우에만 처리
            if (storeManagerEntity != null) {
                String storeManagerId = storeManagerEntity.getIdStoremanager();

                // 상점 관리자의 ID를 이용하여 이름이 포함되어 있는 상점 관리자들을 조회
                List<StoreManagerEntity> filteredStoreManagers = storeManagerRepository.findByIdStoremanagerInAndNameContaining(List.of(storeManagerId), name);

                // 각 상점 관리자 정보를 리스트에 추가
                for (StoreManagerEntity storeManager : filteredStoreManagers) {
                    if (storeManager.getIdStoremanager().equals(storeManagerId)) {
                        brandAndStoreManagers.add(StoreManagerDTO.toStoreManagerDTONotInPasswd(storeManager));
                        break;  // 한 번 추가한 후 바로 반복문 종료
                    }
                }
            } else {
                // idStoremanger가 null인 경우에도 placeholder 객체를 추가
                Map<String, Object> placeholderStoreManager = new HashMap<>();
                placeholderStoreManager.put("idStoremanager", "");
                placeholderStoreManager.put("passwd", "");
                placeholderStoreManager.put("name", "");
                placeholderStoreManager.put("email", "");
                placeholderStoreManager.put("phoneNumber", "");
                brandAndStoreManagers.add(placeholderStoreManager);
            }

            // 결과 리스트에 추가
            result.add(brandAndStoreManagers);
        }

        return result;
    }


    public List<BrandOfficeDTO> getClosedBranch(String officeName){
        List<BrandOfficeEntity> brandOfficeEntities = brandOfficeRepository.findByOfficeNameContainingAndOperationalStatusIsFalse(officeName);
        List<BrandOfficeDTO> brandOfficeDTOList = new ArrayList<>();
        for (BrandOfficeEntity brandOfficeEntity : brandOfficeEntities) {
            BrandOfficeDTO brandOfficeDTO = BrandOfficeDTO.toBrandOfficeDTO(brandOfficeEntity);
            brandOfficeDTOList.add(brandOfficeDTO);
        }
        return brandOfficeDTOList;
    }

    @Transactional
    public void updateBranch(BrandOfficeDTO brandOfficeDTO) {
        Optional<BrandOfficeEntity> brandOfficeEntity = brandOfficeRepository.findById(brandOfficeDTO.getIdBrandOffice());
        BrandOfficeEntity brandOffice = brandOfficeEntity.orElse(null);
        brandOffice.setOfficeName(brandOfficeDTO.getOfficeName());
        brandOffice.setAddress(brandOfficeDTO.getAddress());
        brandOffice.setOperationalStatus(brandOfficeDTO.isOperationalStatus());
        // Check if idStoreManager is null
        if (brandOfficeDTO.getIdStoreManager() == null) {
            brandOffice.setIdStoremanger(null);
        } else {
            Optional<StoreManagerEntity> storeManagerEntity = storeManagerRepository.findById(brandOfficeDTO.getIdStoreManager());
            StoreManagerEntity storeManager = storeManagerEntity.orElse(null);
            brandOffice.setIdStoremanger(storeManager);
        }
    }

    @Transactional
    public void deleteStoreManagerOnBranch(Long idBrandOffice) {
        Optional<BrandOfficeEntity> brandOfficeEntity = brandOfficeRepository.findById(idBrandOffice);
        BrandOfficeEntity brandOffice = brandOfficeEntity.orElse(null);
        assert brandOffice != null;
        brandOffice.setIdStoremanger(null);
    }

    public void deleteBrandOffice(Long idBrandOffice) {
        BrandOfficeEntity brandOfficeEntity = brandOfficeRepository.findById(idBrandOffice).orElse(null);
        assert brandOfficeEntity != null;
        brandOfficeRepository.delete(brandOfficeEntity);
    }
}
