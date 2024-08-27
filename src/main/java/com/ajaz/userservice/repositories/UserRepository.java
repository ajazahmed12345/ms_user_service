package com.ajaz.userservice.repositories;

import com.ajaz.userservice.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Override
    Optional<User> findById(Long UserId);
    @Override
    void deleteById(Long userId);

    List<User> findAll();

//    @Query(value = "select * from user_table where email like %:regex", nativeQuery = true)
    List<User> findAllByEmailContaining(String regex);

}
