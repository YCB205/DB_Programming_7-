package com.Sales_manage.Sales_manage.store_manager.repository;

import com.Sales_manage.Sales_manage.store_manager.entity.Store_managerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Store_managerRepository extends JpaRepository<Store_managerEntity, String> {
}
