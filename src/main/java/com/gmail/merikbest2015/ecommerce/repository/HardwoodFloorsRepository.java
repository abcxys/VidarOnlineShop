package com.gmail.merikbest2015.ecommerce.repository;

import com.gmail.merikbest2015.ecommerce.domain.HardwoodFloor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface HardwoodFloorsRepository extends JpaRepository<HardwoodFloor, Long> {

    List<HardwoodFloor> findByIdIn(List<Long> perfumesIds);

    Page<HardwoodFloor> findAllByOrderByPriceAsc(Pageable pageable);

    @Query("SELECT floor FROM HardwoodFloor floor WHERE " +
            "(CASE " +
            "   WHEN :searchType = 'perfumeTitle' THEN UPPER(floor.perfumeTitle) " +
            "   WHEN :searchType = 'country' THEN UPPER(floor.country) " +
            "   ELSE UPPER(floor.perfumer) " +
            "END) " +
            "LIKE UPPER(CONCAT('%',:text,'%')) " +
            "ORDER BY floor.price ASC")
    Page<HardwoodFloor> searchPerfumes(String searchType, String text, Pageable pageable);

    @Query("SELECT perfume FROM HardwoodFloor perfume " +
            "WHERE (coalesce(:perfumers, null) IS NULL OR perfume.perfumer IN :perfumers) " +
            "AND (coalesce(:genders, null) IS NULL OR perfume.perfumeGender IN :genders) " +
            "AND (coalesce(:priceStart, null) IS NULL OR perfume.price BETWEEN :priceStart AND :priceEnd) " +
            "ORDER BY perfume.price ASC")
    Page<HardwoodFloor> getPerfumesByFilterParams(
            List<String> perfumers,
            List<String> genders,
            Integer priceStart,
            Integer priceEnd,
            Pageable pageable);
    
    @Query("SELECT plank_size_id FROM HardwoodFloor floor " +
            "WHERE (coalesce(:ids, null) IS NULL OR floor.id IN :ids) ")
    List<Long> getPlankSizeIdsByIds(
    		List<Long> ids,
    		Pageable pageable);
    
    @Query("SELECT plank_color_id FROM HardwoodFloor floor " +
            "WHERE (coalesce(:ids, null) IS NULL OR floor.id IN :ids) ")
    List<Long> getPlankColorIdsByIds(
    		List<Long> ids,
    		Pageable pageable);
}
