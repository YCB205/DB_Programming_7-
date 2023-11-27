package com.Sales_manage.Sales_manage.merchandise.service;

import com.Sales_manage.Sales_manage.merchandise.entity.MerchandiseEntity;
import com.Sales_manage.Sales_manage.merchandise.ropository.MerchandiseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class MerchandiseService {

    @Autowired
    private MerchandiseRepository merchandiseRepository; // MerchandiseEntity를 다루는 Repository

    public Map<String, List<Map<String, Object>>> getProductsOrder(String productName, List<String> categories, String loggedInUserId) {
        // 세션에서 로그인한 사용자의 ID 가져오기
        if (loggedInUserId == null || loggedInUserId.isEmpty()) {
            throw new RuntimeException("로그인이 필요합니다.");
        }
        List<MerchandiseEntity> filteredList;
        if (productName.isEmpty()) {
            // productName이 비어 있을 경우 모든 상품 데이터 가져오기
            filteredList = merchandiseRepository.findAll();
        } else {
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
                    .map(this::mapMerchandiseEntity)
                    .collect(Collectors.toList());

            response.put(category, categoryList);
        }

        return response;
    }

    public List<MerchandiseEntity> getProducts(String productName, List<String> categories, String loggedInUserId) {
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

    // MerchandiseEntity를 Map<String, Object>으로 매핑하는 메서드
    private Map<String, Object> mapMerchandiseEntity(MerchandiseEntity entity) {
        Map<String, Object> mapped = new HashMap<>();
        mapped.put("id_merchandise", entity.getId_merchandise());
        mapped.put("merchandiseName", entity.getMerchandiseName());
        mapped.put("cost", entity.getCost());
        mapped.put("price", entity.getPrice());
        return mapped;
    }
}
