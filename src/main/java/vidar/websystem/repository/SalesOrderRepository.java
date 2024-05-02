package vidar.websystem.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import vidar.websystem.domain.SalesOrder;

import java.util.Date;
import java.util.List;
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

    /*
    @Query(nativeQuery = true, value = "SELECT * FROM sales_orders orders")
    List<SalesOrder> findFilteredSalesOrders(Long dealerId, Date startDate, Date endDate);

     */

    @Query("SELECT orders FROM SalesOrder orders " +
            "WHERE (:dealerId IS NULL OR orders.dealer.id = :dealerId) " +
            "AND (coalesce(:startDate, NULL) IS NULL OR orders.date >= :startDate) " +
            "AND (coalesce(:endDate, NULL ) IS NULL OR orders.date <= :endDate)")
    List<SalesOrder> findFilteredSalesOrders(Long dealerId, Date startDate, Date endDate);

    // Show the filtered packable sales orders
    // The packable sales orders are orders with status 1 or 2.
    @Query("SELECT orders FROM SalesOrder orders " +
            "WHERE (:dealerId IS NULL OR orders.dealer.id = :dealerId) " +
            "AND (coalesce(:startDate, NULL) IS NULL OR orders.date >= :startDate) " +
            "AND (coalesce(:endDate, NULL ) IS NULL OR orders.date <= :endDate) " +
            "AND orders.status.id in (1, 2) ")
    List<SalesOrder> findFilteredPackableSalesOrders(Long dealerId, Date startDate, Date endDate);
}
