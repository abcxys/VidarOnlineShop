package vidar.websystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import vidar.websystem.domain.PackingSlip;

import java.util.Date;
import java.util.List;

/**
 * @author yishi.xing
 * create datetime 5/3/2024 2:03 PM
 * description
 */
public interface PackingSlipRepository extends JpaRepository<PackingSlip, Long> {

    @Query("SELECT slips FROM PackingSlip slips " +
            "WHERE (:dealerId IS NULL OR slips.dealer.id = :dealerId) " +
            "AND ((:statusIds) IS NULL OR slips.packingStatus.id IN (:statusIds)) " +
            "AND (coalesce(:startDate, NULL) IS NULL OR slips.createTime >= :startDate) " +
            "AND (coalesce(:endDate, NULL ) IS NULL OR slips.createTime <= :endDate) ")
    List<PackingSlip> findFilteredPackingSlips(Long dealerId,
                                               List<Long> statusIds,
                                               Date startDate,
                                               Date endDate);

}
