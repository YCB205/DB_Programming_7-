package com.Sales_manage.Sales_manage.merchandise.service;
import com.Sales_manage.Sales_manage.brand.entity.BrandEntity;
import com.Sales_manage.Sales_manage.brand_office.entity.BrandOfficeEntity;
import com.Sales_manage.Sales_manage.brand_office.ropository.BrandOfficeRepository;
import com.Sales_manage.Sales_manage.manager.entity.ManagerEntity;
import com.Sales_manage.Sales_manage.manager.repository.ManagerRepository;
import com.Sales_manage.Sales_manage.merchandise.dto.MerchandiseRequestDTO;
import com.Sales_manage.Sales_manage.merchandise.entity.MerchandiseEntity;
import com.Sales_manage.Sales_manage.merchandise.ropository.MerchandiseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MerchandiseService {

    private final MerchandiseRepository merchandiseRepository;
    private final ManagerRepository managerRepository;
    private final BrandOfficeRepository brandOfficeRepository;
    @Autowired
    public MerchandiseService(MerchandiseRepository merchandiseRepository,
                              ManagerRepository managerRepository,
                              BrandOfficeRepository brandOfficeRepository) {
        this.merchandiseRepository = merchandiseRepository;
        this.managerRepository = managerRepository;
        this.brandOfficeRepository = brandOfficeRepository;
    }

    public String updateMerchandise(Long id, MerchandiseRequestDTO merchandiseRequestDTO) {
        Optional<MerchandiseEntity> merchandiseEntityOptional = merchandiseRepository.findById(id);
        if (merchandiseEntityOptional.isPresent()) {
            MerchandiseEntity merchandiseEntity = merchandiseEntityOptional.get();

            // Update properties based on DTO values
            merchandiseEntity.setCategori(merchandiseRequestDTO.getCategori());
            merchandiseEntity.setMerchandiseName(merchandiseRequestDTO.getMerchandiseName());
            merchandiseEntity.setCost(merchandiseRequestDTO.getCost());
            merchandiseEntity.setPrice(merchandiseRequestDTO.getPrice());
            merchandiseEntity.setSalesStatus(merchandiseRequestDTO.isSalesStatus());

            merchandiseRepository.save(merchandiseEntity);
            return "Merchandise with ID " + id + " updated successfully.";
        } else {
            return "Merchandise with ID " + id + " not found.";
        }
    }

    public void createMerchandise(MerchandiseRequestDTO merchandiseRequest, String loggedInUserId, String loggedInUserRole) {
        if (loggedInUserId == null || loggedInUserId.isEmpty()) {
            throw new RuntimeException("로그인이 필요합니다.");
        }

        MerchandiseEntity newMerchandise = new MerchandiseEntity();
        newMerchandise.setCategori(merchandiseRequest.getCategori());
        newMerchandise.setMerchandiseName(merchandiseRequest.getMerchandiseName());
        newMerchandise.setCost(merchandiseRequest.getCost());
        newMerchandise.setPrice(merchandiseRequest.getPrice());
        newMerchandise.setSalesStatus(merchandiseRequest.isSalesStatus());

        if ("store_manager".equals(loggedInUserRole)) {
            ManagerEntity manager = managerRepository.findByIdManager(loggedInUserId);
            if (manager != null) {
                newMerchandise.setIdBrand(manager.getIdBrand());
            } else {
                throw new RuntimeException("해당 매니저를 찾을 수 없습니다.");
            }
        } else {
            // "store_manager"가 아닐 경우
            BrandOfficeEntity brandOffice = brandOfficeRepository.findByStoreManagerId(loggedInUserId);
            if (brandOffice != null) {
                newMerchandise.setIdBrand(brandOffice.getIdBrand());
            } else {
                throw new RuntimeException("해당 스토어 매니저를 찾을 수 없습니다.");
            }
        }
        // 저장
        merchandiseRepository.save(newMerchandise);
    }

    public Map<String, List<Map<String, Object>>> getProductsOrder(String productName, List<String> categories, String loggedInUserId) {
        // 세션에서 로그인한 사용자의 ID 가져오기
        if (loggedInUserId == null || loggedInUserId.isEmpty()) {
            throw new RuntimeException("로그인이 필요합니다.");
        }
        List<MerchandiseEntity> filteredList;

        if (productName.isEmpty()) {
            // productName이 비어 있을 경우 모든 상품 데이터 가져오기
            filteredList = merchandiseRepository.findAll();
        }

        else {
            // productName에 해당하는 상품 데이터만 가져오기
            filteredList = merchandiseRepository.findByMerchandiseNameContaining(productName);
        }

        if (categories == null || categories.isEmpty()) {
            categories = filteredList.stream()
                    .map(MerchandiseEntity::getCategori)
                    .distinct()
                    .collect(Collectors.toList());
        }

        // 상품 데이터에서 salesStatus가 true(1)인 데이터만 필터링하여 재할당
        filteredList = filteredList.stream()
                .filter(MerchandiseEntity::isSalesStatus)
                .collect(Collectors.toList());

        // 결과를 JSON으로 반환하기 위한 Map 구성
        Map<String, List<Map<String, Object>>> response = new HashMap<>();

        // 카테고리 별로 상품 리스트를 구성하여 Map에 추가
        for (String category : categories) {
            List<Map<String, Object>> categoryList = filteredList.stream()
                    .filter(item -> item.getCategori().equals(category))
                    .map(this::mapMerchandiseEntity)
                    .collect(Collectors.toList());

            response.put(category, categoryList);
        }

        return response;
    }


    public Map<String, List<Map<String, Object>>> getAllProducts(String productName, List<String> categories, String loggedInUserId) {
        // 세션에서 로그인한 사용자의 ID 가져오기
        if (loggedInUserId == null || loggedInUserId.isEmpty()) {
            throw new RuntimeException("로그인이 필요합니다.");
        }
        List<MerchandiseEntity> filteredList;

        if (productName.isEmpty()) {
            // productName이 비어 있을 경우 모든 상품 데이터 가져오기
            filteredList = merchandiseRepository.findAll();
        }

        else {
            // productName에 해당하는 상품 데이터만 가져오기
            filteredList = merchandiseRepository.findByMerchandiseNameContaining(productName);
        }

        if (categories == null || categories.isEmpty()) {
            categories = filteredList.stream()
                    .map(MerchandiseEntity::getCategori)
                    .distinct()
                    .collect(Collectors.toList());
        }

        // 결과를 JSON으로 반환하기 위한 Map 구성
        Map<String, List<Map<String, Object>>> response = new HashMap<>();

        // 카테고리 별로 상품 리스트를 구성하여 Map에 추가
        for (String category : categories) {
            List<Map<String, Object>> categoryList = filteredList.stream()
                    .filter(item -> item.getCategori().equals(category))
                    .map(this::mapMerchandiseEntityAddSalesStatus)
                    .collect(Collectors.toList());

            response.put(category, categoryList);
        }

        return response;
    }

/*    public List<MerchandiseEntity> getProducts(String productName, List<String> categories, String loggedInUserId) {
        if (loggedInUserId == null || loggedInUserId.isEmpty()) {
            throw new RuntimeException("로그인이 필요합니다.");
        }
        List<MerchandiseEntity> filteredList;

        if (productName.isEmpty()) {
            filteredList = merchandiseRepository.findAll();
        } else {
            filteredList = merchandiseRepository.findByMerchandiseNameContaining(productName);
        }
        // categories가 비어있지 않고, 상품의 categori와 categories 리스트의 값이 일치하는 상품만 필터링
        filteredList = filteredList.stream()
                .filter(product -> categories.contains(product.getCategori()))
                .collect(Collectors.toList());


        return  filteredList;

    }
*/


    // MerchandiseEntity를 Map<String, Object>으로 매핑하는 메서드
    private Map<String, Object> mapMerchandiseEntity(MerchandiseEntity entity) {
        Map<String, Object> mapped = new HashMap<>();
        mapped.put("id_merchandise", entity.getId_merchandise());
        mapped.put("merchandiseName", entity.getMerchandiseName());
        mapped.put("cost", entity.getCost());
        mapped.put("price", entity.getPrice());
        return mapped;
    }

    // MerchandiseEntity를 Map<String, Object>으로 매핑하는 메서드
    private Map<String, Object> mapMerchandiseEntityAddSalesStatus(MerchandiseEntity entity) {
        Map<String, Object> mapped = new HashMap<>();
        mapped.put("id_merchandise", entity.getId_merchandise());
        mapped.put("merchandiseName", entity.getMerchandiseName());
        mapped.put("cost", entity.getCost());
        mapped.put("price", entity.getPrice());
        mapped.put("sales_status", entity.isSalesStatus());
        return mapped;
    }
}
