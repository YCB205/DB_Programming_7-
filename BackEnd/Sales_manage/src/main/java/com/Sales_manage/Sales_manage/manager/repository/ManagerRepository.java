package com.Sales_manage.Sales_manage.manager.repository;

import com.Sales_manage.Sales_manage.manager.entity.ManagerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ManagerRepository extends JpaRepository<ManagerEntity, String> {
    @Query("SELECT m.idBrand FROM Manager m WHERE m.idManager = :loggedInUserId")
    Long findBrandIdByManagerId(@Param("loggedInUserId") String loggedInUserId);

    ManagerEntity findByIdManager(String loggedInUserId);
}
