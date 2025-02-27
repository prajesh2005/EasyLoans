package com.cognizant.loan_application.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cognizant.loan_application.entities.MyUser;


@Repository
public interface MyUserRepository extends JpaRepository<MyUser, String>
{
    Optional<MyUser> findByUsername(String username);

}
