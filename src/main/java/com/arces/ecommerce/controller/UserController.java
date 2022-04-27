package com.arces.ecommerce.controller;

import java.util.List;
import java.util.NoSuchElementException;

import com.arces.ecommerce.entity.User;

import com.arces.ecommerce.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    private UserService service;

    @GetMapping("/users")
    public List<User> list() {
        return service.listAll();
    }

    @GetMapping("/users/{user_id}")
    public ResponseEntity<User> get(@PathVariable Long user_id) {

        try {
            User user = service.get(user_id);
            return new ResponseEntity<User>(user, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
        }

    }

    @PostMapping("/users")
    public ResponseEntity<?> add(@RequestBody User user) {
        try {
            service.save(user);

            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<String>(HttpStatus.FORBIDDEN);
        }
    }

    @DeleteMapping("/users/{user_id}")
    public ResponseEntity<?> delete(@PathVariable Long user_id) {
        try {

            service.delete(user_id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // @PostMapping("/Users")
    // public void addAll(@RequestBody User[] User) {

    // for (User p : User){
    // service.save(p);
    // }

    // }

    // @PutMapping("/Users/{User_id}")
    // public ResponseEntity<User> update(@RequestBody User User, @PathVariable Long
    // User_id) {
    // try {
    // User User_old = service.get(User_id);
    // copyNonNullProperties(User, User_old);
    // service.save(User_old);
    // return new ResponseEntity<>(HttpStatus.OK);

    // } catch (NoSuchElementException e) {
    // return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    // }
    // }

    // public static void copyNonNullProperties(Object src, Object target) {
    // BeanUtils.copyProperties(src, target, getNullPropertyNames(src));
    // }

    // public static String[] getNullPropertyNames(Object source) {
    // final BeanWrapper src = new BeanWrapperImpl(source);
    // java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();

    // Set<String> emptyNames = new HashSet<String>();
    // for (java.beans.PropertyDescriptor pd : pds) {
    // Object srcValue = src.getPropertyValue(pd.getName());
    // if (srcValue == null)
    // emptyNames.add(pd.getName());
    // }
    // String[] result = new String[emptyNames.size()];
    // return emptyNames.toArray(result);
    // }
}