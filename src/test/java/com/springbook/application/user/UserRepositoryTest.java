package com.springbook.application.user;

import com.springbook.application.orm.jpa.InMemoryUniqueIdGenerator;
import com.springbook.application.orm.jpa.UniqueIdGenerator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashSet;
import java.util.Locale;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@RunWith(SpringRunner.class)
public class UserRepositoryTest {

    @Autowired
    private UserRepository repository;

    @Test
    public void testStoreUser() {
        HashSet<UserRole> roles = new HashSet<>();
        roles.add(UserRole.OFFICER);
        User user = repository.save(new User(repository.nextId(),
                "alex.foley@beverly-hills.com",
                "my-secret-pwd",
                roles));

        assertThat(user).isNotNull();

        assertThat(repository.count()).isEqualTo(1L);
    }

    @Test
    public void testFindByEmail() {
        User user = Users.newRandomOfficer();
        repository.save(user);
        Optional<User> optional = repository.findByEmailIgnoreCase(user.getEmail());

        assertThat(optional).isNotEmpty().contains(user);
    }

    @TestConfiguration
    static class TestConfig {
        @Bean
        public UniqueIdGenerator<UUID> generator() {
            return new InMemoryUniqueIdGenerator();
        }
    }

    @Test
    public void testFindByEmailIgnoringCase() {
        User user = Users.newRandomOfficer();
        repository.save(user);
        Optional<User> optional = repository.findByEmailIgnoreCase(user.getEmail().toUpperCase(Locale.US));

        assertThat(optional).isNotEmpty().contains(user);
    }

    @Test
    public void testFindByEmail_unknownEmail() {
        User user = Users.newRandomOfficer();
        repository.save(user);
        Optional<User> optional = repository.findByEmailIgnoreCase("will.not@find.me");

        assertThat(optional).isEmpty();
    }
}
