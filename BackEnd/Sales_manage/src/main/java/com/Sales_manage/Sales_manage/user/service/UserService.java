package com.Sales_manage.Sales_manage.user.service;

import com.Sales_manage.Sales_manage.brand.entity.BrandEntity;
import com.Sales_manage.Sales_manage.brand.repository.BrandRepository;
import com.Sales_manage.Sales_manage.brand_office.entity.BrandOfficeEntity;
import com.Sales_manage.Sales_manage.brand_office.ropository.BrandOfficeRepository;
import com.Sales_manage.Sales_manage.manager.entity.ManagerEntity;
import com.Sales_manage.Sales_manage.manager.repository.ManagerRepository;
import com.Sales_manage.Sales_manage.store_manager.entity.StoreManagerEntity;
import com.Sales_manage.Sales_manage.store_manager.repository.StoreManagerRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;


@Service
public class UserService {

    private final StoreManagerRepository storeManagerRepository;
    private final ManagerRepository managerRepository;
    private final BrandOfficeRepository brandOfficeRepository;
    private final BrandRepository brandRepository;

    @Autowired
    public UserService(StoreManagerRepository storeManagerRepository,
                       ManagerRepository managerRepository,
                       BrandOfficeRepository brandOfficeRepository,
                       BrandRepository brandRepository) {
        this.storeManagerRepository = storeManagerRepository;
        this.managerRepository = managerRepository;
        this.brandOfficeRepository = brandOfficeRepository;
        this.brandRepository = brandRepository;
    }

    public String login(String id, String password, HttpSession session) {
        StoreManagerEntity storeManager = storeManagerRepository.findById(id).orElse(null);
        ManagerEntity manager = managerRepository.findById(id).orElse(null);

        if (storeManager != null && storeManager.getPasswd().equals(password)) {
            session.setAttribute("loggedInUserId", id);
            session.setAttribute("loggedInUserRole", "store_manager");
            return "store_manager";
        } else if (manager != null && manager.getPasswd().equals(password)) {
            session.setAttribute("loggedInUserId", id);
            session.setAttribute("loggedInUserRole", "manager");
            return "manager";
        } else {
            return null;
        }
    }

    public ManagerEntity getManagerInfo(String id) {
        return managerRepository.findById(id).orElse(null);
    }

    public StoreManagerEntity getStoreManagerInfo(String id) {
        return storeManagerRepository.findById(id).orElse(null);
    }

    public BrandOfficeEntity getBrandOfficeInfo(StoreManagerEntity storeManagerEntity) {
        return brandOfficeRepository.findByIdStoreManger(storeManagerEntity);
        //위 한줄은 하단의 두줄과 같음
//        BrandOfficeEntity brandOfficeEntity = brandOfficeRepository.findByIdStoreManger(storeManagerId);
//        return brandOfficeEntity;
    }




}
