package vidar.websystem.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import vidar.websystem.domain.ReturnSlip;

import java.util.Date;
import java.util.List;

/**
 * @author yishi.xing
 * create datetime 5/10/2024 9:21 AM
 * description
 */
public interface ReturnSlipRepository extends JpaRepository<ReturnSlip, Long> {
    Page<ReturnSlip> findAllByReturnStatusIdInAndCreateTimeBetween(
            List<Long> statusIds,
            Date startDate,
            Date endDate,
            Pageable pageable
    );

    @Query("SELECT return FROM ReturnSlip return " +
            "LEFT JOIN PackingSlip packing " +
            "ON return.packingSlip = packing " +
            "WHERE (:dealerId IS NULL OR packing.dealer.id = :dealerId) " +
            "AND ((:statusIds) IS NULL OR return.returnStatus.id IN (:statusIds)) " +
            "AND (coalesce(:startDate, NULL) IS NULL OR return.createTime >= :startDate) " +
            "AND (coalesce(:endDate, NULL) IS NULL OR return.createTime <= :endDate) ")
    Page<ReturnSlip> findAllByDealerIdAndReturnStatusIdInAndCreateTimeBetween(
            Long dealerId,
            List<Long> statusIds,
            Date startDate,
            Date endDate,
            Pageable pageable
    );
}
