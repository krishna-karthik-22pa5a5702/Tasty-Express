package registerationlogin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import registerationlogin.entity.User;
import java.util.List;
import registerationlogin.entity.Role;


public interface UserRepository extends JpaRepository<User,Long> {

    User findByEmail(String email);
    List<User> findByEmailAndRoles(String email, List<Role> roles);
}
