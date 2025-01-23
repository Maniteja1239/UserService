package com.example.userservice.repository;

import com.example.userservice.models.User;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    @Override
    Optional<User> findById(Long id);

    @Override
    List<User> findAll();

    @Override
    User save(User user);

    //@Query(value="select * from users where email= :email",nativeQuery = true)
    Optional<User> findByEmail(String email);

}
