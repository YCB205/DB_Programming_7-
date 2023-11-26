package com.Sales_manage.Sales_manage.orderSheet.service;

import com.Sales_manage.Sales_manage.brand_office.entity.BrandOfficeEntity;
import com.Sales_manage.Sales_manage.brand_office.ropository.BrandOfficeRepository;
import com.Sales_manage.Sales_manage.merchandise.entity.MerchandiseEntity;
import com.Sales_manage.Sales_manage.merchandise.ropository.MerchandiseRepository;
import com.Sales_manage.Sales_manage.orderSheet.dto.MerchandiseRequest;
import com.Sales_manage.Sales_manage.orderSheet.dto.MerchandiseResponse;
import com.Sales_manage.Sales_manage.orderSheet.dto.OrderSheetResponse;
import com.Sales_manage.Sales_manage.orderSheet.entity.IncludeEntity;
import com.Sales_manage.Sales_manage.orderSheet.entity.OrderSheetEntity;
import com.Sales_manage.Sales_manage.orderSheet.ropository.IncludeRepository;
import com.Sales_manage.Sales_manage.orderSheet.ropository.OrderSheetRepository;
import com.Sales_manage.Sales_manage.store_manager.entity.StoreManagerEntity;
import com.Sales_manage.Sales_manage.store_manager.repository.StoreManagerRepository;
import jakarta.transaction.Transactional;
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
    private final IncludeRepository includeRepository;

    @Autowired
    public OrderSheetService(
            BrandOfficeRepository brandOfficeRepository,
            OrderSheetRepository orderSheetRepository,
            MerchandiseRepository merchandiseRepository,
            StoreManagerRepository storeManagerRepository,
            IncludeRepository includeRepository
    ){
        this.brandOfficeRepository = brandOfficeRepository;
        this.orderSheetRepository = orderSheetRepository;
        this.merchandiseRepository = merchandiseRepository;
        this.storeManagerRepository= storeManagerRepository;
        this.includeRepository = includeRepository;
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

    @Transactional
    public void updateOrderSheet(MerchandiseRequest merchandiseRequest) {
        Optional<OrderSheetEntity> orderSheetOptional = orderSheetRepository.findById(merchandiseRequest.getIdOrdersheet());

        if (orderSheetOptional.isPresent()) {
            OrderSheetEntity orderSheet = orderSheetOptional.get();

            // 기존의 IncludeEntity 삭제
            includeRepository.removeIncludeEntityByIdOrdersheet(orderSheet);

            // merchandise 업데이트
//            List<IncludeEntity> updatedIncludes = new ArrayList<>();
            for (MerchandiseRequest.Merchandise merchandise : merchandiseRequest.getMerchandise()) {
                // MerchandiseEntity 찾기
                Long merchandiseId = merchandise.getId_merchandise();
                MerchandiseEntity merchandiseEntity = merchandiseRepository.findById(merchandiseId)
                        .orElseThrow(() -> new IllegalArgumentException("Merchandise not found"));

                // Include 엔티티 생성 및 추가
                IncludeEntity newInclude = new IncludeEntity();
                newInclude.setIdMerchandise(merchandiseEntity);
                newInclude.setOrderCount(merchandise.getOrderCount());
                newInclude.setSales(merchandise.getSales());
                newInclude.setTotalCost(merchandise.getTotalCost());
                newInclude.setIdOrdersheet(orderSheet);
                includeRepository.save(newInclude);

//                updatedIncludes.add(newInclude);
            }

            // 기존의 Includes를 새로운 목록으로 교체
//            orderSheet.setIncludes(updatedIncludes);
            // 주문서의 변경 내용 저장
//            orderSheetRepository.save(orderSheet);
        }
    }

    @Transactional
    public void deleteOrderSheet(Long idOrdersheet) {
        // 주문서 ID에 해당하는 주문서 엔터티 조회
        OrderSheetEntity orderSheet = orderSheetRepository.findById(idOrdersheet)
                .orElseThrow(() -> new IllegalArgumentException("Order sheet not found"));

        // 주문서에 속한 IncludeEntity 삭제
        includeRepository.removeIncludeEntityByIdOrdersheet(orderSheet);

        // 주문서 삭제
        orderSheetRepository.delete(orderSheet);
    }





}



