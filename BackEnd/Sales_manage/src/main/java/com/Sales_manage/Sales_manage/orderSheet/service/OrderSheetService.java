package com.Sales_manage.Sales_manage.orderSheet.service;

import com.Sales_manage.Sales_manage.brand_office.entity.BrandOfficeEntity;
import com.Sales_manage.Sales_manage.brand_office.ropository.BrandOfficeRepository;
import com.Sales_manage.Sales_manage.merchandise.entity.MerchandiseEntity;
import com.Sales_manage.Sales_manage.merchandise.ropository.MerchandiseRepository;
import com.Sales_manage.Sales_manage.orderSheet.dto.MerchandiseResponse;
import com.Sales_manage.Sales_manage.orderSheet.dto.OrderSheetResponse;
import com.Sales_manage.Sales_manage.orderSheet.entity.IncludeEntity;
import com.Sales_manage.Sales_manage.orderSheet.entity.OrderSheetEntity;
import com.Sales_manage.Sales_manage.orderSheet.ropository.IncludeRepository;
import com.Sales_manage.Sales_manage.orderSheet.ropository.OrderSheetRepository;
import com.Sales_manage.Sales_manage.store_manager.entity.StoreManagerEntity;
import com.Sales_manage.Sales_manage.store_manager.repository.StoreManagerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderSheetService {

    private final BrandOfficeRepository brandOfficeRepository;
    private final OrderSheetRepository orderSheetRepository;
    private final MerchandiseRepository merchandiseRepository;
    private final StoreManagerRepository storeManagerRepository;

    @Autowired
    public OrderSheetService(
            BrandOfficeRepository brandOfficeRepository,
            OrderSheetRepository orderSheetRepository,
            MerchandiseRepository merchandiseRepository,
            StoreManagerRepository storeManagerRepository
    ){
        this.brandOfficeRepository = brandOfficeRepository;
        this.orderSheetRepository = orderSheetRepository;
        this.merchandiseRepository = merchandiseRepository;
        this.storeManagerRepository= storeManagerRepository;
    }


    public List<OrderSheetResponse> getOrderSheetList(String loggedInUserId, LocalDateTime startDateTime, LocalDateTime endDateTime, String searchMerchandiseValue) {
        // loggedInUserId를 이용해 BrandOfficeEntity의 idBrandoffice 값을 찾음
        Optional<StoreManagerEntity> storeManagerEntity = storeManagerRepository.findById(loggedInUserId);
        StoreManagerEntity storeManager = storeManagerEntity.orElse(null);

        if (storeManager == null) {
            return Collections.emptyList();
        }

        BrandOfficeEntity brandOffice = brandOfficeRepository.findByIdStoremanger(storeManager);

        // idBrandoffice 값을 이용해 OrderSheetEntity 목록을 찾음
        List<OrderSheetEntity> orderSheets = orderSheetRepository.findByIdBrandofficeAndOrderTimeBetween(
                brandOffice, startDateTime, endDateTime);

        // OrderSheetEntity 목록에서 startDateTime와 endDateTime 사이의 데이터를 찾음
        List<OrderSheetResponse> orderSheetResponses = new ArrayList<>();
        for (OrderSheetEntity orderSheet : orderSheets) {
            boolean orderSheetContainsMerchandise = orderSheet.getIncludes().stream()
                    .anyMatch(includeEntity -> {
                        String merchandiseName = includeEntity.getIdMerchandise().getMerchandiseName();
                        return searchMerchandiseValue.isBlank() || merchandiseName.contains(searchMerchandiseValue);
                    });

            if (orderSheetContainsMerchandise) {
                OrderSheetResponse orderSheetResponse = new OrderSheetResponse();
                orderSheetResponse.setIdOrdersheet(orderSheet.getIdOrdersheet());
                orderSheetResponse.setOrderTime(orderSheet.getOrderTime());

                List<MerchandiseResponse> merchandiseResponses = orderSheet.getIncludes().stream()
                        .map(includeEntity -> {
                            MerchandiseEntity merchandise = includeEntity.getIdMerchandise();
                            MerchandiseResponse merchandiseResponse = new MerchandiseResponse();
                            merchandiseResponse.setCategori(merchandise.getCategori());
                            merchandiseResponse.setId_merchandise(merchandise.getId_merchandise());
                            merchandiseResponse.setMerchandiseName(merchandise.getMerchandiseName());
                            merchandiseResponse.setCost(merchandise.getCost());
                            merchandiseResponse.setPrice(merchandise.getPrice());
                            merchandiseResponse.setOrderCount(includeEntity.getOrderCount());
                            return merchandiseResponse;
                        })
                        .collect(Collectors.toList());

                // 총 매출 및 이익 계산
                double totalSales = merchandiseResponses.stream()
                        .mapToDouble(merchandiseResponse -> merchandiseResponse.getPrice() * merchandiseResponse.getOrderCount())
                        .sum();
                double totalCost = merchandiseResponses.stream()
                        .mapToDouble(merchandiseResponse -> merchandiseResponse.getCost() * merchandiseResponse.getOrderCount())
                        .sum();

                orderSheetResponse.setSales((long) totalSales);
                orderSheetResponse.setProfit((long) (totalSales - totalCost));

                orderSheetResponse.setMerchandise(merchandiseResponses);
                orderSheetResponses.add(orderSheetResponse);
            }

        }

        return orderSheetResponses;
    }
}



