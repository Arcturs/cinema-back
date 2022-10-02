package ru.vsu.csf.asashina.cinemaback.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.vsu.csf.asashina.cinemaback.models.entities.RoleEntity;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, Long> {

    @Query(value = """
            SELECT *
            FROM role
            WHERE role_id = 1""", nativeQuery = true)
    RoleEntity findUserRole();
}
