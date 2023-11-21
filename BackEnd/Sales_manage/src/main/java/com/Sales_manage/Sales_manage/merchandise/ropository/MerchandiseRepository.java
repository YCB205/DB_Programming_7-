package com.Sales_manage.Sales_manage.merchandise.ropository;

import com.Sales_manage.Sales_manage.merchandise.entity.MerchandiseEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MerchandiseRepository extends JpaRepository<MerchandiseEntity, Long> {

}
