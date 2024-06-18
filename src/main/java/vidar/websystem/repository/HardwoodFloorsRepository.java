package vidar.websystem.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import org.springframework.data.repository.query.Param;
import vidar.websystem.domain.*;

import java.math.BigDecimal;
import java.util.List;

public interface HardwoodFloorsRepository extends JpaRepository<HardwoodFloor, Long> {

	List<HardwoodFloor> findByActive(Boolean active);

    List<HardwoodFloor> findByIdIn(List<Long> perfumesIds);

	List<HardwoodFloor> findByColorAndSizeAndTypeAndGradeAndSpeciesAndBatchNumber(PlankColor color, PlankSize size, PlankType type, Grade grade, WoodSpecies species, String batchNumber);

    Page<HardwoodFloor> findAllByOrderByPriceAsc(Pageable pageable);

	Page<HardwoodFloor> findByColorNameInAndSizeWidthInInchInAndPriceBetween(
			List<String> colorNames,
			List<String> widthInInch,
			BigDecimal priceLow,
			BigDecimal priceHigh,
			Pageable pageable
	);

	Page<HardwoodFloor> findBySizeWidthInInchInAndPriceBetween(
			List<String> widthInInch,
			BigDecimal priceLow,
			BigDecimal priceHigh,
			Pageable pageable
	);

	Page<HardwoodFloor> findByColorNameInAndPriceBetween(
			List<String> colorNames,
			BigDecimal priceLow,
			BigDecimal priceHigh,
			Pageable pageable
	);

	Page<HardwoodFloor> findByColorNameInAndSizeWidthInInchIn(
			List<String> colorNames,
			List<String> widthInInch,
			Pageable pageable
	);

	Page<HardwoodFloor> findByColorNameIn(
			List<String> colorNames,
			Pageable pageable
	);

	Page<HardwoodFloor> findBySizeWidthInInchIn(
			List<String> widthInInch,
			Pageable pageable
	);

	Page<HardwoodFloor> findByPriceBetween(
			BigDecimal priceLow,
			BigDecimal priceHigh,
			Pageable pageable
	);

	default Page<HardwoodFloor> findByFilter(
			List<String> colorNames,
			List<String> widthInInch,
			BigDecimal priceLow,
			BigDecimal priceHigh,
			Pageable pageable
	) {
		boolean isColorFilterActive = colorNames != null && !colorNames.isEmpty();
		boolean isWidthFilterActive = widthInInch != null && !widthInInch.isEmpty();
		boolean isPriceFilterActive = priceLow != null && !priceLow.equals(BigDecimal.ZERO);

		if (isColorFilterActive && isWidthFilterActive && isPriceFilterActive) {
			// All filters are active
			return findByColorNameInAndSizeWidthInInchInAndPriceBetween(colorNames, widthInInch, priceLow, priceHigh, pageable);
		}

		if (isColorFilterActive && isWidthFilterActive) {
			// Color and Width filters are active
			return findByColorNameInAndSizeWidthInInchIn(colorNames, widthInInch, pageable);
		}

		if (isColorFilterActive && isPriceFilterActive) {
			// Color and Price filters are active
			return findByColorNameInAndPriceBetween(colorNames, priceLow, priceHigh, pageable);
		}

		if (isWidthFilterActive && isPriceFilterActive) {
			// Width and Price filters are active
			return findBySizeWidthInInchInAndPriceBetween(widthInInch, priceLow, priceHigh, pageable);
		}

		if (isColorFilterActive) {
			// Only Color filter is active
			return findByColorNameIn(colorNames, pageable);
		}

		if (isWidthFilterActive) {
			// Only Width filter is active
			return findBySizeWidthInInchIn(widthInInch, pageable);
		}

		if (isPriceFilterActive) {
			// Only Price filter is active
			return findByPriceBetween(priceLow, priceHigh, pageable);
		}

		// Default case, although it should never reach here due to prior checks
		return findAll(pageable);
	}

    /*
    @Query("SELECT floor FROM HardwoodFloor floor WHERE " +
            "(CASE " +
            "   WHEN :searchType = 'perfumeTitle' THEN UPPER(floor.perfumeTitle) " +
            "   WHEN :searchType = 'country' THEN UPPER(floor.country) " +
            "   ELSE UPPER(floor.perfumer) " +
            "END) " +
            "LIKE UPPER(CONCAT('%',:text,'%')) " +
            "ORDER BY floor.price ASC")
     */

	default Page<HardwoodFloor> searchByFilter(String searchType, String text, Pageable pageable) {
		if (text == null || text.isEmpty())
			return findAll(pageable);
		else {
			if (searchType.equals("colour")){
				return findByColorNameContainingIgnoreCase(text, pageable);
			} else if (searchType.equals("width")) {
				return findBySizeWidthInInchLike(text, pageable);
			} else {
				return findBySpeciesNameContainingIgnoreCase(text, pageable);
			}
		}
	}

	@Query(value = "SELECT h FROM HardwoodFloor h WHERE UPPER(h.color.name) LIKE CONCAT('%', UPPER(:text), '%')")
	Page<HardwoodFloor> findByColorNameContainingIgnoreCase(@Param("text") String text, Pageable pageable);

	Page<HardwoodFloor> findBySizeWidthInInchLike(
			String widthInInch,
			Pageable pageable
	);

	@Query(value = "SELECT h FROM HardwoodFloor h WHERE UPPER(h.species.name) LIKE CONCAT('%', UPPER(:text), '%')")
	Page<HardwoodFloor> findBySpeciesNameContainingIgnoreCase(@Param("text") String text, Pageable pageable);
    
    /*
    @Query("SELECT perfume FROM HardwoodFloor perfume " +
            "WHERE (coalesce(:perfumers, null) IS NULL OR perfume.perfumer IN :perfumers) " +
            "AND (coalesce(:genders, null) IS NULL OR perfume.perfumeGender IN :genders) " +
            "AND (coalesce(:priceStart, null) IS NULL OR perfume.price BETWEEN :priceStart AND :priceEnd) " +
            "ORDER BY perfume.price ASC")
    */
}
