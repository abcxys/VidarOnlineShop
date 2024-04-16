package vidar.websystem.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import vidar.websystem.domain.Container;

/**
 * @author yishi.xing
 * created Apr 13, 2024 - 11:21:01 AM
 */
public interface ContainerRepository extends JpaRepository<Container, Long> {
    @Query(nativeQuery = true, value = "SELECT * FROM Container container WHERE " +
            "(CASE " +
            "   WHEN :searchType = 'containerNumber' THEN UPPER(container.container_number) " +
            "   WHEN :searchType = 'bln' THEN UPPER(container.billoflanding_number) " +
            "END) " +
            "LIKE UPPER(CONCAT('%',:searchValue,'%')) ")
    Page<Container> searchContainers(String searchType, String searchValue, Pageable pageable);
}
