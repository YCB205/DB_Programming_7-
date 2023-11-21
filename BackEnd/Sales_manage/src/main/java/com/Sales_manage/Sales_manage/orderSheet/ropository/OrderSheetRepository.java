package com.Sales_manage.Sales_manage.orderSheet.ropository;

import com.Sales_manage.Sales_manage.orderSheet.entity.IncludeId;
import com.Sales_manage.Sales_manage.orderSheet.entity.OrderSheetEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderSheetRepository extends JpaRepository<OrderSheetEntity, IncludeId> {
}
