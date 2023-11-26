package com.Sales_manage.Sales_manage.merchandise.ropository;

import com.Sales_manage.Sales_manage.brand_office.entity.BrandOfficeEntity;
import com.Sales_manage.Sales_manage.merchandise.entity.MerchandiseEntity;
import com.Sales_manage.Sales_manage.store_manager.entity.StoreManagerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MerchandiseRepository extends JpaRepository<MerchandiseEntity, Long> {

    List<MerchandiseEntity> findByMerchandiseNameContaining(String productName);


}
