package com.Sales_manage.Sales_manage.orderSheet.ropository;

import com.Sales_manage.Sales_manage.orderSheet.entity.IncludeEntity;
import com.Sales_manage.Sales_manage.orderSheet.entity.IncludeId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface IncludeRepository extends JpaRepository<IncludeEntity, IncludeId> {


    default void saveIncludeEntity(IncludeEntity includeEntity) {
        includeEntity = save(includeEntity);
        flush();
    }
}
