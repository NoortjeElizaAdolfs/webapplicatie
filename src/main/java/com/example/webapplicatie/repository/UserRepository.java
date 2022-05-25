//package com.example.webapplicatie.repository;
//import com.example.webapplicatie.models.User;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.stereotype.Repository;
//
//@Repository
//public interface UserRepository extends JpaRepository<User, Long>{
//
//    @Query ("SELECT u FROM User u WHERE u.email=?1")
//    User findByEmail(String email);
//}
package com.example.webapplicatie.repository;

import com.example.webapplicatie.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

// Brug tussen JPA repository functies en user entiteit
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);
}
