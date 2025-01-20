package hu.preznyak.hobby_soccer.bootstrap;

import hu.preznyak.hobby_soccer.user.domain.Authority;
import hu.preznyak.hobby_soccer.user.domain.User;
import hu.preznyak.hobby_soccer.user.repository.AuthorityRepository;
import hu.preznyak.hobby_soccer.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class UserDataLoader implements CommandLineRunner {

    private final UserRepository userRepository;
    private final AuthorityRepository authorityRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        loadUserData();
    }

    private void loadUserData() {

        var userRole = authorityRepository.save(Authority.builder().role("ROLE_USER").build());
        var adminRole = authorityRepository.save(Authority.builder().role("ROLE_ADMIN").build());

        userRepository.save(User.builder()
                .username("spring")
                .password(passwordEncoder.encode("training"))
                .authority(userRole)
                .build());

        userRepository.save(User.builder()
                .username("admin")
                .password(passwordEncoder.encode("guru"))
                .authority(adminRole)
                .build());

        log.info("User data loaded: " + userRepository.count());

    }
}
