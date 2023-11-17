package com.Sales_manage.Sales_manage.user.ropository;

import com.Sales_manage.Sales_manage.store_manager.entity.Store_managerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<Store_managerEntity, String> {
}
