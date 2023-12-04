package com.Sales_manage.Sales_manage.user.service;

import com.Sales_manage.Sales_manage.brand.repository.BrandRepository;
import com.Sales_manage.Sales_manage.brand_office.entity.BrandOfficeEntity;
import com.Sales_manage.Sales_manage.brand_office.ropository.BrandOfficeRepository;
import com.Sales_manage.Sales_manage.manager.entity.ManagerEntity;
import com.Sales_manage.Sales_manage.manager.repository.ManagerRepository;
import com.Sales_manage.Sales_manage.store_manager.entity.StoreManagerEntity;
import com.Sales_manage.Sales_manage.store_manager.repository.StoreManagerRepository;
import com.Sales_manage.Sales_manage.user.dto.UserData;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


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
        return brandOfficeRepository.findByIdStoremanger(storeManagerEntity);
    }

    public void updateStoreManager(UserData userData) {
        StoreManagerEntity storeManagerEntity = storeManagerRepository.findById(userData.getId()).orElse(null);

        if (storeManagerEntity != null) {

            storeManagerEntity.setEmail(userData.getEmail());
            if (userData.getPasswd().equals("")){
                storeManagerEntity.setPasswd(storeManagerEntity.getPasswd());
            } else {
                storeManagerEntity.setPasswd(userData.getPasswd());
            }
            storeManagerEntity.setName(userData.getName());
            storeManagerEntity.setPhoneNumber(userData.getPhoneNumber());

            storeManagerRepository.save(storeManagerEntity);
        }
    }

    public void updateManager(UserData userData) {
        ManagerEntity managerEntity = managerRepository.findById(userData.getId()).orElse(null);

        if (managerEntity != null) {

            managerEntity.setEmail(userData.getEmail());
            if (userData.getPasswd().equals("")){
                managerEntity.setPasswd(managerEntity.getPasswd());
            } else {
                managerEntity.setPasswd(userData.getPasswd());
            }
            managerEntity.setName(userData.getName());
            managerEntity.setPhoneNumber(userData.getPhoneNumber());

            managerRepository.save(managerEntity);
        }
    }


    public boolean checkPasswd(String loggedInUserId, String passwd) {
        ManagerEntity manager = managerRepository.findById(loggedInUserId).orElse(null);
        if (manager != null && manager.getPasswd().equals(passwd)) {
            return true;
        } else {
            return false;
        }
    }
}
