package com.Sales_manage.Sales_manage.orderSheet.ropository;

import com.Sales_manage.Sales_manage.brand_office.entity.BrandOfficeEntity;
import com.Sales_manage.Sales_manage.orderSheet.entity.IncludeEntity;
import com.Sales_manage.Sales_manage.orderSheet.entity.OrderSheetEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.time.LocalDateTime;
import java.util.List;


public interface OrderSheetRepository extends JpaRepository<OrderSheetEntity, Long> {


    @Query("SELECT MAX(o.idOrdersheet) FROM OrderSheetEntity o")
    Long findLastOrderId();


    List<OrderSheetEntity>findByIdBrandofficeAndOrderTimeBetween(
            BrandOfficeEntity idBrandoffice, LocalDateTime startDate, LocalDateTime endDate
    );


    // IncludeEntity 저장을 위한 메서드


}
