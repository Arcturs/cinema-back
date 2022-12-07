package ru.vsu.csf.asashina.cinemaback.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.vsu.csf.asashina.cinemaback.models.entities.TicketEntity;

@Repository
public interface TicketRepository extends JpaRepository<TicketEntity, Long> {

    @Query(value = "SELECT order_count FROM order_number FOR UPDATE", nativeQuery = true)
    Integer findOrderNumberForUpdate();

    @Modifying
    @Query(value = "UPDATE order_number SET order_count = order_count + :incr", nativeQuery = true)
    void updateOrderNumber(@Param("incr") int increment);
}
