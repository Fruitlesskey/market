package com.example.manager.repository;

import com.example.manager.entity.SelmagUser;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

//@Repository
public interface SelmagUserRepository extends JpaRepository<SelmagUser, Integer> {

  Optional<SelmagUser> findByUsername(String username);

}
