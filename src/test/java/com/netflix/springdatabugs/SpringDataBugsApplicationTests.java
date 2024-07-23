package com.netflix.springdatabugs;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@Import(TestcontainersConfiguration.class)
@EnableJpaRepositories(basePackages = "com.netflix.springdatabugs")
@EntityScan("com.netflix.springdatabugs")
@AutoConfigureDataJpa
@AutoConfigureTestEntityManager
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@SpringBootTest
class SpringDataBugsApplicationTests {

    @Autowired
    ShowsRepository repository;

    @BeforeEach
    void setup() {
        repository.deleteAll();

        repository.saveAll(List.of(
                new Show("Stranger Things"),
                new Show("Extraction"),
                new Show("Rebel Moon"),
                new Show("The Witcher"),
                new Show("3 Body Problem")
        ));
    }

    @Test
    void testWithWorkaround() {
        var all = repository.showsNativeWithWorkaround(Sort.by("title"));
        assertThat(all).extracting("title").containsExactly("3 Body Problem", "Extraction", "Rebel Moon", "Stranger Things", "The Witcher");

    }

    @Test //This fails
    void testWithoutWorkaround() {
        var all = repository.showsNativeWithoutWorkaround(Sort.by("title"));
        assertThat(all).extracting("title").containsExactly("3 Body Problem", "Extraction", "Rebel Moon", "Stranger Things", "The Witcher");
    }

}
