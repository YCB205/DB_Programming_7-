package com.Sales_manage.Sales_manage.user.service;

import com.Sales_manage.Sales_manage.manager.entity.ManagerEntity;
import com.Sales_manage.Sales_manage.manager.repository.ManagerRepository;
import com.Sales_manage.Sales_manage.store_manager.entity.Store_managerEntity;
import com.Sales_manage.Sales_manage.store_manager.repository.Store_managerRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class UserService {

    private final Store_managerRepository store_managerRepository;
    private final ManagerRepository managerRepository;

    @Autowired
    public UserService(Store_managerRepository store_managerRepository, ManagerRepository managerRepository) {
        this.store_managerRepository = store_managerRepository;
        this.managerRepository = managerRepository;
    }

    public boolean login(String id, String password, HttpSession session) {
        Store_managerEntity storeManager = store_managerRepository.findById(id).orElse(null);
        Optional<ManagerEntity> manager = managerRepository.findById(id);

        if (storeManager != null && storeManager.getPasswd().equals(password)) {
            session.setAttribute("loggedInUserId", id);
            session.setAttribute("loggedInUserRole", "store_manager");
            return true;
        } else if (manager.map(m -> m.getPasswd().equals(password)).orElse(false)) {
            session.setAttribute("loggedInUserId", id);
            session.setAttribute("loggedInUserRole", "manager");
            return true;
        } else {
            return false;
        }
    }


}
