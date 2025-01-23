package com.example.userservice.repository;

import com.example.userservice.models.Token;
import com.example.userservice.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface TokenRepository extends JpaRepository<Token,Long> {
    @Override
    Token save(Token token);

    @Query(value = "update Tokens set deleted=true where id: =id",nativeQuery = true)
    void update(Long id);

    @Query(value = "select * from Tokens where user_id= :userId",nativeQuery = true)
    List<Token> getAllTokensByUserId(Long userId);

    //@Query(value = "select * from Tokens where value= :tokenValue and deleted=false and expiry_at >= :currentDate",nativeQuery = true)
    Optional<Token> findTokenByValueAndDeletedAndExpiryAtGreaterThan(String tokenValue,boolean deleted,Date currentDate);
}
