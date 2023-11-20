package com.Sales_manage.Sales_manage.brand.service;

import com.Sales_manage.Sales_manage.brand.dto.BrandDTO;
import com.Sales_manage.Sales_manage.brand.entity.BrandEntity;
import com.Sales_manage.Sales_manage.brand.repository.BrandRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BrandService {
    private final BrandRepository brandRepository;

    public List<BrandDTO> getAll() {
        List<BrandEntity> brandEntityList = brandRepository.findAll();
        List<BrandDTO> brandDTOList = new ArrayList<>();
        for (BrandEntity brandEntity : brandEntityList){
            BrandDTO storemanagerDTO = BrandDTO.toBrandDTO(brandEntity);
            brandDTOList.add(storemanagerDTO);
        }
        return brandDTOList;
    }

}
