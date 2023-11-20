package com.Sales_manage.Sales_manage.brand.repository;

import com.Sales_manage.Sales_manage.brand.entity.BrandEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface BrandRepository extends JpaRepository<BrandEntity, Long> {

}
