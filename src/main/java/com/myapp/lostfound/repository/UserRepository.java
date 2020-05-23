package com.myapp.lostfound.repository;

import com.myapp.lostfound.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User,Integer> {

    List<User> findAll();

    User findById(int id);

    User save(User user);

    User findByUsername(String username);

    void delete(User user);

    User findByPhone(String phone);

}
