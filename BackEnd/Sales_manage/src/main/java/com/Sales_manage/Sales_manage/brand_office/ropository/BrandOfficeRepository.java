package com.Sales_manage.Sales_manage.brand_office.ropository;

import com.Sales_manage.Sales_manage.brand_office.entity.BrandOfficeEntity;
import com.Sales_manage.Sales_manage.store_manager.entity.StoreManagerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface BrandOfficeRepository extends JpaRepository<BrandOfficeEntity, Long> {
    BrandOfficeEntity findByIdStoremanger(StoreManagerEntity idStoremanger);
    List<BrandOfficeEntity> findByOfficeNameContaining(String officeName);
    List<BrandOfficeEntity> findByOfficeNameContainingAndOperationalStatusIsTrue(String officeName);
    List<BrandOfficeEntity> findByOfficeNameContainingAndOperationalStatusIsFalse(String officeName);


    @Query("SELECT bo.idBrandoffice FROM BrandOfficeEntity bo JOIN bo.idStoremanger sm WHERE sm.idStoremanager = :loggedInUserId")
    Long findIdBrandOfficeByLoggedInUserId(@Param("loggedInUserId") String loggedInUserId);

    @Query("SELECT b.idBrand FROM BrandOffice b WHERE b.idStoreManager = :loggedInUserId")
    Long findBrandIdByStoreManagerId(@Param("loggedInUserId") String loggedInUserId);

    BrandOfficeEntity findByStoreManagerId(String loggedInUserId);
}
