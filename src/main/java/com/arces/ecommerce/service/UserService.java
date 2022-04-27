package com.arces.ecommerce.service;

import java.util.List;
import javax.transaction.Transactional;

import com.arces.ecommerce.entity.User;
import com.arces.ecommerce.entity.UserAddress;
import com.arces.ecommerce.entity.UserCard;
import com.arces.ecommerce.entity.UserCategory;
import com.arces.ecommerce.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Transactional

public class UserService {

    @Autowired
    private UserCardService servicecard;
    @Autowired
    private UserAddressService serviceaddress;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository repo;

    public List<User> listAll() {
        return repo.findAll();
    }

    public void save(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        List<UserCard> user_cards = user.getUser_cards();
        List<UserAddress> user_address = user.getUser_address();
        UserCategory user_category = user.getCategory();
        user.setActive(1);
        user.setCreate_date(new java.sql.Timestamp(System.currentTimeMillis()));
        user.setLast_activity(user.getCreate_date());
        if (user_category == null) {
            user.setCategory(new UserCategory(1, "Customer"));
        }
        repo.save(user);
        Long user_id = user.getUser_id();

        if (user_cards != null) {
            if (!user_cards.isEmpty()) {
                for (UserCard userCard : user_cards) {
                    userCard.setUser_id(user_id);
                }
                servicecard.saveAll(user_cards);
            }
        }
        if (user_address != null) {
            if (!user_address.isEmpty()) {
                for (UserAddress userAddress : user_address) {
                    userAddress.setUser_id(user_id);
                }
                serviceaddress.saveAll(user_address);
            }
        }

    }

    public User get(Long id) {
        return repo.findById(id).get();
    }

    public void delete(Long id) {
        repo.deleteById(id);
    }

}
