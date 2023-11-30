package com.Sales_manage.Sales_manage.orderSheet.ropository;

import com.Sales_manage.Sales_manage.orderSheet.entity.IncludeEntity;
import com.Sales_manage.Sales_manage.orderSheet.entity.IncludeId;
import com.Sales_manage.Sales_manage.orderSheet.entity.OrderSheetEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IncludeRepository extends JpaRepository<IncludeEntity, IncludeId> {

    @Transactional
    void removeIncludeEntityByIdOrdersheet(OrderSheetEntity idOrdersheet);

    default void saveIncludeEntity(IncludeEntity includeEntity) {
        includeEntity = save(includeEntity);
        flush();
    }
}
