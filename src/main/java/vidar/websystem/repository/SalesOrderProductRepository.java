package vidar.websystem.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import vidar.websystem.domain.SalesOrderProduct;

/**
 * @author yishi.xing
 * @created Feb 19, 2024 - 11:23:35 PM
 */
public interface SalesOrderProductRepository extends JpaRepository<SalesOrderProduct, Long> {
	List<SalesOrderProduct> findBySalesOrderId(Long id);
	List<SalesOrderProduct> findBySalesOrderIdIn(List<Long> ids);
	List<SalesOrderProduct> findFloorOrdersByHardwoodfloorId(Long id);
	void deleteBySalesOrderId(Long id);

	@Modifying
	@Query("UPDATE SalesOrderProduct sop SET sop.active = false, " +
			"sop.updateUserId = :userId, sop.updateTime = CURRENT_DATE WHERE sop.salesOrderId = :salesOrderId")
	void setInActiveForSoId(Long salesOrderId, Long userId);
}
