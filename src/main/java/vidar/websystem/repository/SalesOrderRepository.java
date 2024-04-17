package vidar.websystem.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import vidar.websystem.domain.SalesOrder;

import java.util.Optional;

public interface SalesOrderRepository extends JpaRepository<SalesOrder, Long> {

    @EntityGraph(attributePaths = {"hardwoodfloors", "user", "user.roles"})
    Page<SalesOrder> findAll(Pageable pageable);

    @EntityGraph(attributePaths = {"hardwoodfloors", "user", "user.roles"})
    Optional<SalesOrder> getById(Long orderId);

    @EntityGraph(attributePaths = {"hardwoodfloors"})
    Optional<SalesOrder> getByIdAndUserId(Long orderId, Long userId);

    @EntityGraph(attributePaths = {"hardwoodfloors", "user", "user.roles"})
    Page<SalesOrder> findOrderByUserId(Long userId, Pageable pageable);

    @EntityGraph(attributePaths = {"hardwoodfloors", "user", "user.roles"})
    @Query("SELECT orders FROM SalesOrder orders WHERE " +
            "(CASE " +
            "   WHEN :searchType = 'firstName' THEN UPPER(orders.firstName) " +
            "   WHEN :searchType = 'lastName' THEN UPPER(orders.lastName) " +
            "   ELSE UPPER(orders.email) " +
            "END) " +
            "LIKE UPPER(CONCAT('%',:text,'%'))")
    Page<SalesOrder> searchOrders(String searchType, String text, Pageable pageable);

    @EntityGraph(attributePaths = {"hardwoodfloors", "user", "user.roles"})
    @Query("SELECT orders FROM SalesOrder orders " +
            "LEFT JOIN orders.user user " +
            "WHERE user.id = :userId " +
            "AND (CASE " +
            "   WHEN :searchType = 'firstName' THEN UPPER(orders.firstName) " +
            "   WHEN :searchType = 'lastName' THEN UPPER(orders.lastName) " +
            "   ELSE UPPER(orders.email) " +
            "END) " +
            "LIKE UPPER(CONCAT('%',:text,'%'))")
    Page<SalesOrder> searchUserOrders(Long userId, String searchType, String text, Pageable pageable);
}
