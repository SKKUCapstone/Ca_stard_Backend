package com.skkucapstone.Castardbackend.repository;

import com.skkucapstone.Castardbackend.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // 추가 메서드 필요시 정의.

    /** email로 회원 찾기 **/
    Optional<User> findByEmail(String email);

    /** username과 email로 회원 찾기 **/
    Optional<User> findByUserNameAndEmail(String userName, String email);
}
