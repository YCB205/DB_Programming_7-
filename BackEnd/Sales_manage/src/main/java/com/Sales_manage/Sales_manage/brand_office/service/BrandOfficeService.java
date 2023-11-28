package com.Sales_manage.Sales_manage.brand_office.service;

import com.Sales_manage.Sales_manage.brand_office.entity.BrandOfficeEntity;
import com.Sales_manage.Sales_manage.brand_office.ropository.BrandOfficeRepository;
import com.Sales_manage.Sales_manage.brand_office.dto.BrandOfficeDTO;
import com.Sales_manage.Sales_manage.store_manager.dto.StoreManagerDTO;
import com.Sales_manage.Sales_manage.store_manager.entity.StoreManagerEntity;
import com.Sales_manage.Sales_manage.store_manager.repository.StoreManagerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
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
        List<BrandOfficeEntity> brandOfficeEntities = brandOfficeRepository.findByOfficeNameContaining(officeName);

        // 결과를 저장할 리스트
        List<List<Object>> result = new ArrayList<>();

        // 각 브랜드 사무실과 관련된 정보를 리스트에 추가
        for (BrandOfficeEntity brandOfficeEntity : brandOfficeEntities) {
            List<Object> brandAndStoreManagers = new ArrayList<>();
            BrandOfficeDTO brandOfficeDTO = BrandOfficeDTO.toBrandOfficeDTO(brandOfficeEntity);
            brandAndStoreManagers.add(brandOfficeDTO);

            // 브랜드 사무실에 속한 상점 관리자의 ID를 추출
            String storeManagerId = brandOfficeEntity.getIdStoremanger().getIdStoremanager();

            // 상점 관리자의 ID를 이용하여 이름이 포함되어 있는 상점 관리자들을 조회
            List<StoreManagerEntity> filteredStoreManagers = storeManagerRepository.findByIdStoremanagerInAndNameContaining(List.of(storeManagerId), name);

            // 각 상점 관리자 정보를 리스트에 추가
            for (StoreManagerEntity storeManagerEntity : filteredStoreManagers) {
                if (storeManagerEntity.getIdStoremanager().equals(storeManagerId)) {
                    brandAndStoreManagers.add(StoreManagerDTO.toStoreManagerDTO(storeManagerEntity));
                    break;  // 한 번 추가한 후 바로 반복문 종료
                }
            }

            // 결과가 있을 경우에만 최종 결과 리스트에 추가
            if (brandAndStoreManagers.size() > 1) {
                result.add(brandAndStoreManagers);
            }
        }

        return result;

    }
}
