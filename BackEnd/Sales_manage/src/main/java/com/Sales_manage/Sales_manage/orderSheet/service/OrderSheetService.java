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
import java.util.*;
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

    public List<Object[]> getAllSalesData(List<String> productNames, LocalDateTime startDate, LocalDateTime endDate, List<Long> brandofficeId, String loggedInUserRole, String loggedInUserId) {
        List<Object[]> salesData;
        List<Object[]> allSalesData;
        int totalProfit = 0;
        int totalSales = 0;
        if (loggedInUserId == null || loggedInUserId.isEmpty()) {
            throw new RuntimeException("로그인이 필요합니다.");
        }



        if ("manager".equals(loggedInUserRole)) {
            List<BrandOfficeEntity> BrandOffice = brandOfficeRepository.findAllById(brandofficeId);
            // 'manager' 권한이라면 모든 상품 데이터의 매출 정보 가져오기
            if(productNames == null || productNames.isEmpty() && startDate == null && endDate == null){
                if(brandofficeId == null || brandofficeId.isEmpty()){
                    salesData = merchandiseRepository.findAllSalesDataBetweenDates();
                    allSalesData = merchandiseRepository.findAllChartDataBetweenDates();
                }else{
                    salesData = merchandiseRepository.findAllSalesDataManagerBetweenDates(BrandOffice);
                    allSalesData = merchandiseRepository.findAllChartDataManagerBetweenDates(BrandOffice);
                }

            }else if(productNames == null || productNames.isEmpty() && startDate != null && endDate != null){
                if(brandofficeId == null || brandofficeId.isEmpty()){
                    salesData = merchandiseRepository.findAllSalesDataBetweenDates(startDate, endDate);
                    allSalesData = merchandiseRepository.findAllChartDataBetweenDates(startDate, endDate);
                }else{
                    salesData = merchandiseRepository.findAllSalesDataManagerBetweenDates(BrandOffice, startDate, endDate);
                    allSalesData = merchandiseRepository.findAllChartDataManagerBetweenDates(BrandOffice, startDate, endDate);
                }

            }else if(startDate == null && endDate == null){
                if(brandofficeId == null || brandofficeId.isEmpty()){
                    salesData = merchandiseRepository.findAllSalesDataBetweenDates(productNames);
                    allSalesData = merchandiseRepository.findAllChartDataBetweenDates();
                }else{
                    salesData = merchandiseRepository.findAllSalesDataManagerBetweenDates(BrandOffice, productNames);
                    allSalesData = merchandiseRepository.findAllChartDataManagerBetweenDates(BrandOffice);
                }

            }else if(startDate != null && endDate != null){
                if(brandofficeId == null || brandofficeId.isEmpty()){
                    salesData = merchandiseRepository.findAllSalesDataBetweenDates(startDate, endDate, productNames);
                    allSalesData = merchandiseRepository.findAllChartDataBetweenDates(startDate, endDate);
                }else{
                    salesData = merchandiseRepository.findAllSalesDataManagerBetweenDates(BrandOffice, startDate, endDate, productNames);
                    allSalesData = merchandiseRepository.findAllChartDataManagerBetweenDates(BrandOffice, startDate, endDate);
                }
            }else{
                throw new RuntimeException("날짜 설정을 잘못 하였습니다.");
            }

        } else {
            // 'store_manager' 권한이라면 해당 유저의 id_brandoffice 값을 이용하여 매출 정보 가져오기
            StoreManagerEntity storeManager = storeManagerRepository.findById(loggedInUserId).orElse(null);
            BrandOfficeEntity BrandOffice = brandOfficeRepository.findByIdStoremanger(storeManager);
            if(productNames == null || productNames.isEmpty() && startDate == null && endDate == null){
                salesData = merchandiseRepository.findAllSalesDataByBrandOfficeBetweenDates(BrandOffice);
                allSalesData = merchandiseRepository.findAllChartDataByBrandOfficeBetweenDates(BrandOffice);
            }else if(productNames == null || productNames.isEmpty() && startDate != null && endDate != null){
                salesData = merchandiseRepository.findAllSalesDataByBrandOfficeBetweenDates(startDate, endDate, BrandOffice);
                allSalesData = merchandiseRepository.findAllChartDataByBrandOfficeBetweenDates(startDate, endDate, BrandOffice);
            }else if(startDate == null && endDate == null){
                salesData = merchandiseRepository.findAllSalesDataByBrandOfficeBetweenDates(BrandOffice, productNames);
                allSalesData = merchandiseRepository.findAllChartDataByBrandOfficeBetweenDates(BrandOffice);
            }else if(startDate != null && endDate != null){
                salesData = merchandiseRepository.findAllSalesDataByBrandOfficeBetweenDates(startDate, endDate, BrandOffice, productNames);
                allSalesData = merchandiseRepository.findAllChartDataByBrandOfficeBetweenDates(startDate, endDate, BrandOffice);
            }else{
                throw new RuntimeException("날짜 설정을 잘못 하였습니다.");
            }
        }
        if(salesData.isEmpty()){
            return salesData;
        }else{

            for (Object[] data : allSalesData) {
                int profit = ((Number) data[1]).intValue();
                int sales = ((Number) data[2]).intValue();

                totalProfit += profit;
                totalSales += sales;
            }

            if ("manager".equals(loggedInUserRole)) {
                salesData.addAll(getAllSalesData(startDate, endDate));
            }


            Object[] totalData = new Object[]{"총매출", totalProfit, totalSales};
            salesData.add(totalData);
            return salesData;
        }


    }


    public List<Object[]> getAllSalesData(LocalDateTime startDate, LocalDateTime endDate) {
        // 여러 메서드 중에서 findAllSalesDataBetweenDates 메서드 활용
        List<Object[]> existingData = merchandiseRepository.findAllSalesDataBetweenDates(startDate, endDate);

        // idBrandoffice를 키로 하는 맵을 생성하여 데이터를 묶기
        Map<Long, List<Object[]>> groupedData = existingData.stream()
                .collect(Collectors.groupingBy(array -> (Long) array[0]));

        // 새로운 리스트에 묶은 데이터를 추가
        List<Object[]> resultData = new ArrayList<>();
        for (Map.Entry<Long, List<Object[]>> entry : groupedData.entrySet()) {
            Long idBrandoffice = entry.getKey();
            List<Object[]> salesDataList = entry.getValue();

            int totalProfit = salesDataList.stream()
                    .mapToInt(array -> Math.toIntExact((Long) array[6])) // 6은 영업이익에 해당하는 index
                    .sum();
            int totalSales = salesDataList.stream()
                    .mapToInt(array -> Math.toIntExact((Long) array[7])) // 7은 매출액에 해당하는 index
                    .sum();

            // 새로운 배열을 생성하여 결과 리스트에 추가
            Object[] resultArray = {idBrandoffice, "", "", "", totalProfit, totalSales};
            resultData.add(resultArray);
        }



        return resultData;
    }

    @Transactional
    public List<Object[]> getAllChartData(List<String> productNames, LocalDateTime startDate, LocalDateTime endDate, List<Long> brandofficeId, String loggedInUserRole, String loggedInUserId) {
        List<Object[]> chartData;
        List<Object[]> allSalesData;
        int totalProfit = 0;
        int totalSales = 0;
        if (loggedInUserId == null || loggedInUserId.isEmpty()) {
            throw new RuntimeException("로그인이 필요합니다.");
        }
        if ("manager".equals(loggedInUserRole)) {
            // 'manager' 권한이라면 모든 상품 데이터의 매출 정보 가져오기
            List<BrandOfficeEntity> BrandOffice = brandOfficeRepository.findAllById(brandofficeId);
            if(productNames == null || productNames.isEmpty() && startDate == null && endDate == null){
                if(brandofficeId == null || brandofficeId.isEmpty()){
                    chartData = merchandiseRepository.findAllChartDataBetweenDates();
                    allSalesData = merchandiseRepository.findAllChartDataBetweenDates();
                }else{
                    chartData = merchandiseRepository.findAllChartDataManagerBetweenDates(BrandOffice);
                    allSalesData = merchandiseRepository.findAllChartDataManagerBetweenDates(BrandOffice);
                }

            }else if(productNames == null || productNames.isEmpty() && startDate != null && endDate != null){
                if(brandofficeId == null || brandofficeId.isEmpty()){
                    chartData = merchandiseRepository.findAllChartDataBetweenDates(startDate, endDate);
                    allSalesData = merchandiseRepository.findAllChartDataBetweenDates(startDate, endDate);
                }else{
                    chartData = merchandiseRepository.findAllChartDataManagerBetweenDates(BrandOffice, startDate, endDate);
                    allSalesData = merchandiseRepository.findAllChartDataManagerBetweenDates(BrandOffice, startDate, endDate);
                }

            }else if(startDate == null && endDate == null){
                if(brandofficeId == null || brandofficeId.isEmpty()){
                    chartData = merchandiseRepository.findAllChartDataBetweenDates(productNames);
                    allSalesData = merchandiseRepository.findAllChartDataBetweenDates();
                }else{
                    chartData = merchandiseRepository.findAllChartDataManagerBetweenDates(BrandOffice, productNames);
                    allSalesData = merchandiseRepository.findAllChartDataManagerBetweenDates(BrandOffice);
                }

            }else if(startDate != null && endDate != null){
                if(brandofficeId == null || brandofficeId.isEmpty()){
                    chartData = merchandiseRepository.findAllChartDataBetweenDates(startDate, endDate, productNames);
                    allSalesData = merchandiseRepository.findAllChartDataBetweenDates(startDate, endDate);
                }else{
                    chartData = merchandiseRepository.findAllChartDataManagerBetweenDates(BrandOffice, startDate, endDate, productNames);
                    allSalesData = merchandiseRepository.findAllChartDataManagerBetweenDates(BrandOffice, startDate, endDate);
                }
            }else{
                throw new RuntimeException("날짜 설정을 잘못 하였습니다.");
            }

        } else {
            // 'store_manager' 권한이라면 해당 유저의 id_brandoffice 값을 이용하여 매출 정보 가져오기
            StoreManagerEntity storeManager = storeManagerRepository.findById(loggedInUserId).orElse(null);
            BrandOfficeEntity BrandOffice = brandOfficeRepository.findByIdStoremanger(storeManager);
            if(productNames == null || productNames.isEmpty() && startDate == null && endDate == null){
                chartData = merchandiseRepository.findAllChartDataByBrandOfficeBetweenDates(BrandOffice);
                allSalesData = merchandiseRepository.findAllChartDataByBrandOfficeBetweenDates(BrandOffice);
            }else if(productNames == null || productNames.isEmpty() && startDate != null && endDate != null){
                chartData = merchandiseRepository.findAllChartDataByBrandOfficeBetweenDates(startDate, endDate, BrandOffice);
                allSalesData = merchandiseRepository.findAllChartDataByBrandOfficeBetweenDates(startDate, endDate, BrandOffice);
            }else if(startDate == null && endDate == null){
                chartData = merchandiseRepository.findAllChartDataByBrandOfficeBetweenDates(BrandOffice, productNames);
                allSalesData = merchandiseRepository.findAllChartDataByBrandOfficeBetweenDates(BrandOffice);
            }else if(startDate != null && endDate != null){
                chartData = merchandiseRepository.findAllChartDataByBrandOfficeBetweenDates(startDate, endDate, BrandOffice, productNames);
                allSalesData = merchandiseRepository.findAllChartDataByBrandOfficeBetweenDates(startDate, endDate, BrandOffice);
            }else{
                throw new RuntimeException("날짜 설정을 잘못 하였습니다.");
            }
        }

        if(chartData.isEmpty()){
            return chartData;
        }else{

            for (Object[] data : allSalesData) {
                int profit = ((Number) data[1]).intValue();
                int sales = ((Number) data[2]).intValue();

                totalProfit += profit;
                totalSales += sales;
            }

            Object[] totalData = new Object[]{"총매출", totalProfit, totalSales};
            chartData.add(totalData);
            return chartData;
        }

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


    @Transactional
    public void createOrderSheet(List<Long> productIds, List<Integer> counts, LocalDateTime orderTime, String loggedInUserId) {
        // 새로운 주문서 생성
        // 세션에서 로그인한 사용자의 ID 가져오기
        if (loggedInUserId == null || loggedInUserId.isEmpty()) {
            throw new RuntimeException("로그인이 필요합니다.");
        }
        OrderSheetEntity orderSheet = new OrderSheetEntity();
        orderSheet.setOrderTime(orderTime);

        // 피라미터로 받은 값(1)으로 고정. 실제로는 세션 등에서 사용자 정보를 받아올 수 있음.
        BrandOfficeEntity brandOffice = new BrandOfficeEntity();
        brandOffice.setIdBrandoffice(brandOfficeRepository.findIdBrandOfficeByLoggedInUserId(loggedInUserId));
        orderSheet.setIdBrandoffice(brandOffice);

        // 마지막 id_ordersheet 값 가져오기
        Long lastOrderId = orderSheetRepository.findLastOrderId();
        Long newOrderId = (lastOrderId != null) ? lastOrderId + 1 : 1;
        orderSheet.setIdOrdersheet(newOrderId);

        // 주문서 저장
        orderSheetRepository.save(orderSheet);

        // IncludeEntity에 데이터 추가
        for (int i = 0; i < productIds.size(); i++) {
            IncludeEntity includeEntity = new IncludeEntity();
            includeEntity.setIdOrdersheet(orderSheet);
            includeEntity.setIdMerchandise(merchandiseRepository.findById(productIds.get(i)).orElseThrow());

            includeEntity.setOrderCount(counts.get(i).shortValue());

            // 상품 데이터 조회
            MerchandiseEntity merchandiseEntity = merchandiseRepository.findById(productIds.get(i)).orElseThrow();
            includeEntity.setTotalCost(merchandiseEntity.getCost() * counts.get(i));
            includeEntity.setSales(merchandiseEntity.getPrice() * counts.get(i));

            // IncludeEntity 저장
            includeRepository.saveIncludeEntity(includeEntity);
        }
    }
}



