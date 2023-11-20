package com.Sales_manage.Sales_manage.manager.repository;

import com.Sales_manage.Sales_manage.manager.entity.ManagerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ManagerRepository extends JpaRepository<ManagerEntity, String> {
}
