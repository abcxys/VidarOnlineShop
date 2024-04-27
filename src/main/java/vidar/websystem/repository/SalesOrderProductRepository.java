package vidar.websystem.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import vidar.websystem.domain.SalesOrderProduct;

/**
 * @author yishi.xing
 * @created Feb 19, 2024 - 11:23:35 PM
 */
public interface SalesOrderProductRepository extends JpaRepository<SalesOrderProduct, Long> {
	List<SalesOrderProduct> findBySalesOrderId(Long id);
	List<SalesOrderProduct> findFloorOrdersByHardwoodfloorId(Long id);
}
