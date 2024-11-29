package team08.oop.lms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import team08.oop.lms.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
    
    @Query("SELECT r FROM Role r WHERE r.name= :name")
    Role findByName(@Param("name") String name);
    
}
