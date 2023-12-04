package com.Sales_manage.Sales_manage.merchandise.ropository;

import com.Sales_manage.Sales_manage.merchandise.entity.MerchandiseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface MerchandiseRepository extends JpaRepository<MerchandiseEntity, Long> {

    List<MerchandiseEntity> findByMerchandiseNameContaining(String productName);
    //매니저 productName 없을 때 날짜 이용한 전체 데이터 조회
    @Query("SELECT m.categori, m.id_merchandise, m.merchandiseName, " +
            "CASE WHEN m.salesStatus = false THEN 'N' ELSE 'Y' END, " +
            "SUM(i.totalCost), SUM(i.sales) " +
            "FROM MerchandiseEntity m " +
            "JOIN IncludeEntity i ON m.id_merchandise = CAST(i.idMerchandise AS Long)" +
            "JOIN OrderSheetEntity o ON o.idOrdersheet = CAST(i.idOrdersheet AS Long)" +
            "WHERE o.orderTime BETWEEN :startDate AND :endDate " +
            "GROUP BY m.categori, m.id_merchandise, m.merchandiseName, m.salesStatus")
    List<Object[]> findAllSalesDataBetweenDates(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

    //페이지 불러올 때 매니저 전체 데이터 조회
    @Query("SELECT m.categori, m.id_merchandise, m.merchandiseName, " +
            "CASE WHEN m.salesStatus = false THEN 'N' ELSE 'Y' END, " +
            "SUM(i.totalCost), SUM(i.sales) " +
            "FROM MerchandiseEntity m " +
            "JOIN IncludeEntity i ON m.id_merchandise = CAST(i.idMerchandise AS Long)" +
            "JOIN OrderSheetEntity o ON o.idOrdersheet = CAST(i.idOrdersheet AS Long)" +
            "GROUP BY m.categori, m.id_merchandise, m.merchandiseName, m.salesStatus")
    List<Object[]> findAllSalesDataBetweenDates();

    //매니저 productName 있을 때 데이터 조회
    @Query("SELECT m.categori, m.id_merchandise, m.merchandiseName, " +
            "CASE WHEN m.salesStatus = false THEN 'N' ELSE 'Y' END, " +
            "SUM(i.totalCost), SUM(i.sales) " +
            "FROM MerchandiseEntity m " +
            "JOIN IncludeEntity i ON m.id_merchandise = CAST(i.idMerchandise AS Long)" +
            "JOIN OrderSheetEntity o ON o.idOrdersheet = CAST(i.idOrdersheet AS Long)" +
            "WHERE m.merchandiseName IN (:productNames)" +
            "GROUP BY m.categori, m.id_merchandise, m.merchandiseName, m.salesStatus")
    List<Object[]> findAllSalesDataBetweenDates(@Param("productNames") List<String> productNames);

    //매니저 productName 있을 때 날짜 이용한 데이터 조회
    @Query("SELECT m.categori, m.id_merchandise, m.merchandiseName, " +
            "CASE WHEN m.salesStatus = false THEN 'N' ELSE 'Y' END, " +
            "SUM(i.totalCost), SUM(i.sales) " +
            "FROM MerchandiseEntity m " +
            "JOIN IncludeEntity i ON m.id_merchandise = CAST(i.idMerchandise AS Long)" +
            "JOIN OrderSheetEntity o ON o.idOrdersheet = CAST(i.idOrdersheet AS Long)" +
            "WHERE m.merchandiseName IN (:productNames) AND o.orderTime BETWEEN :startDate AND :endDate " +
            "GROUP BY m.categori, m.id_merchandise, m.merchandiseName, m.salesStatus")
    List<Object[]> findAllSalesDataBetweenDates(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate, @Param("productNames") List<String> productNames);

    //지점 상품명, 날짜 이용한 검색
    @Query("SELECT m.categori, m.id_merchandise, m.merchandiseName, " +
            "CASE WHEN m.salesStatus = false THEN 'N' ELSE 'Y' END, " +
            "SUM(i.totalCost), SUM(i.sales) " +
            "FROM MerchandiseEntity m " +
            "JOIN IncludeEntity i ON m.id_merchandise = CAST(i.idMerchandise AS Long) " +
            "JOIN OrderSheetEntity o ON o.idOrdersheet = CAST(i.idOrdersheet AS Long)" +
            "WHERE o.idBrandoffice = :idBrandOffice AND m.merchandiseName IN (:productNames) AND o.orderTime BETWEEN :startDate AND :endDate " +
            "GROUP BY m.categori, m.id_merchandise, m.merchandiseName, m.salesStatus")
    List<Object[]> findAllSalesDataByBrandOfficeBetweenDates(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate, @Param("idBrandOffice") Long idBrandOffice, @Param("productNames") List<String> productNames);

    //지점 상품명 이용한 검색
    @Query("SELECT m.categori, m.id_merchandise, m.merchandiseName, " +
            "CASE WHEN m.salesStatus = false THEN 'N' ELSE 'Y' END, " +
            "SUM(i.totalCost), SUM(i.sales) " +
            "FROM MerchandiseEntity m " +
            "JOIN IncludeEntity i ON m.id_merchandise = CAST(i.idMerchandise AS Long) " +
            "JOIN OrderSheetEntity o ON o.idOrdersheet = CAST(i.idOrdersheet AS Long)" +
            "WHERE o.idBrandoffice = :idBrandOffice AND m.merchandiseName IN (:productNames)" +
            "GROUP BY m.categori, m.id_merchandise, m.merchandiseName, m.salesStatus")
    List<Object[]> findAllSalesDataByBrandOfficeBetweenDates(@Param("idBrandOffice") Long idBrandOffice, @Param("productNames") List<String> productNames);

    //상품명 없을 때 날짜 이용한 검색
    @Query("SELECT m.categori, m.id_merchandise, m.merchandiseName, " +
            "CASE WHEN m.salesStatus = false THEN 'N' ELSE 'Y' END, " +
            "SUM(i.totalCost), SUM(i.sales) " +
            "FROM MerchandiseEntity m " +
            "JOIN IncludeEntity i ON m.id_merchandise = CAST(i.idMerchandise AS Long) " +
            "JOIN OrderSheetEntity o ON o.idOrdersheet = CAST(i.idOrdersheet AS Long)" +
            "WHERE o.idBrandoffice = :idBrandOffice AND o.orderTime BETWEEN :startDate AND :endDate " +
            "GROUP BY m.categori, m.id_merchandise, m.merchandiseName, m.salesStatus")
    List<Object[]> findAllSalesDataByBrandOfficeBetweenDates(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate, @Param("idBrandOffice") Long idBrandOffice);
    //지점 페이지 불러올 때 검색
    @Query("SELECT m.categori, m.id_merchandise, m.merchandiseName, " +
            "CASE WHEN m.salesStatus = false THEN 'N' ELSE 'Y' END, " +
            "SUM(i.totalCost), SUM(i.sales) " +
            "FROM MerchandiseEntity m " +
            "JOIN IncludeEntity i ON m.id_merchandise = CAST(i.idMerchandise AS Long) " +
            "JOIN OrderSheetEntity o ON o.idOrdersheet = CAST(i.idOrdersheet AS Long)" +
            "WHERE o.idBrandoffice = :idBrandOffice " +
            "GROUP BY m.categori, m.id_merchandise, m.merchandiseName, m.salesStatus")
    List<Object[]> findAllSalesDataByBrandOfficeBetweenDates(@Param("idBrandOffice") Long idBrandOffice);
}
