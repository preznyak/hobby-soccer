package hu.preznyak.hobby_soccer.user.repository;

import hu.preznyak.hobby_soccer.user.domain.Authority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorityRepository extends JpaRepository<Authority, Long> {
}
