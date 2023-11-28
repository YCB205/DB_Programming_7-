package com.Sales_manage.Sales_manage.store_manager.repository;

import com.Sales_manage.Sales_manage.store_manager.entity.StoreManagerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StoreManagerRepository extends JpaRepository<StoreManagerEntity, String> {
    List<StoreManagerEntity> findByIdStoremanagerInAndNameContaining(List<String> storeManagerIds, String name);
}
