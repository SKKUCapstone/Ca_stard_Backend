package com.skkucapstone.Castardbackend.service;

import com.skkucapstone.Castardbackend.domain.User;
import com.skkucapstone.Castardbackend.dto.CafeDto;
import com.skkucapstone.Castardbackend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    /** email 로 회원 찾기  **/
    public Optional<User> findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

//    /** username 과 email 로 회원 찾기  **/
//    public Optional<User> findUserByUserNameAndEmail(String userName, String email) {
//        return userRepository.findByUserNameAndEmail(userName, email);
//    }

    @Transactional
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    @Transactional
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
