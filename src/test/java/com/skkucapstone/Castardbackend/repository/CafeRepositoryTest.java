package com.skkucapstone.Castardbackend.repository;

import com.skkucapstone.Castardbackend.domain.Cafe;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
public class CafeRepositoryTest {

    @Autowired
    private CafeRepository cafeRepository;

    @Test
    @Rollback(value = false)
    public void testSaveCafe() {
        // Given
        Cafe cafe = new Cafe();
        cafe.setName("Test Cafe");
        cafe.setAddress("Test Address");

        // When
        Cafe savedCafe = cafeRepository.save(cafe);

        // Then
        assertThat(savedCafe.getId()).isNotNull();
        assertThat(savedCafe.getName()).isEqualTo("Test Cafe");
        assertThat(savedCafe.getAddress()).isEqualTo("Test Address");
    }
}
