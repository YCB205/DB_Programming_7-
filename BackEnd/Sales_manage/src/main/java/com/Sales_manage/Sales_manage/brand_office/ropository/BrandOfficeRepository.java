package com.Sales_manage.Sales_manage.brand_office.ropository;

import com.Sales_manage.Sales_manage.brand_office.entity.BrandOfficeEntity;
import com.Sales_manage.Sales_manage.store_manager.entity.StoreManagerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface BrandOfficeRepository extends JpaRepository<BrandOfficeEntity, Long> {
    BrandOfficeEntity findByIdStoremanger(StoreManagerEntity idStoremanger);
    List<BrandOfficeEntity> findByOfficeNameContaining(String officeName);

}
