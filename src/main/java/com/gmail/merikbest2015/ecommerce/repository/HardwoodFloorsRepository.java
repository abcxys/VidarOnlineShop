package com.gmail.merikbest2015.ecommerce.repository;

import com.gmail.merikbest2015.ecommerce.domain.FloorColorSize;
import com.gmail.merikbest2015.ecommerce.domain.FloorOrder;
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
    
    /*
    @Query("SELECT perfume FROM HardwoodFloor perfume " +
            "WHERE (coalesce(:perfumers, null) IS NULL OR perfume.perfumer IN :perfumers) " +
            "AND (coalesce(:genders, null) IS NULL OR perfume.perfumeGender IN :genders) " +
            "AND (coalesce(:priceStart, null) IS NULL OR perfume.price BETWEEN :priceStart AND :priceEnd) " +
            "ORDER BY perfume.price ASC")
    */
    @Query(nativeQuery = true, value = "SELECT * FROM (" +
    		"SELECT floor.id as id, floor.filename as filename, color.name as colorName, size.width_in_inch as width, size.length as length, " + 
    		"size.thickness_in_inch as thickness, size.squarefoot_per_carton as sqftPerCarton, species.name as woodSpeciesName FROM hardwoodfloors floor " +
    		"LEFT JOIN plank_colors color ON floor.plank_color_id = color.id " +
    		"LEFT JOIN plank_sizes size ON floor.plank_size_id = size.id " +
    		"LEFT JOIN wood_species species ON floor.wood_species_id = species.id " +
    		") t")
    Page<FloorColorSize> getPerfumesByFilterParams(
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
    
    @Query(nativeQuery = true, value = "SELECT floor.id as id, floor.filename as filename, color.name as colorName, size.width_in_inch as width, size.length as length, " + 
    		"size.thickness_in_inch as thickness, size.squarefoot_per_carton as sqftPerCarton, species.name as woodSpeciesName, grades.name as gradeName, grades.alias as gradeAlias FROM hardwoodfloors floor " +
    		"LEFT JOIN plank_colors color ON floor.plank_color_id = color.id " +
    		"LEFT JOIN plank_sizes size ON floor.plank_size_id = size.id " +
    		"LEFT JOIN wood_species species ON floor.wood_species_id = species.id " +
    		"LEFT JOIN grades grades ON floor.grade_id = grades.id " +
    		"WHERE floor.id = :id")
    FloorColorSize findFloorColorById(Long id);
    
    @Query(nativeQuery = true, value = "SELECT floor.id as id, floor.filename as filename, color.name as colorName, size.width_in_inch as width, size.length as length, " + 
    		"size.thickness_in_inch as thickness, size.squarefoot_per_carton as sqftPerCarton, species.name as woodSpeciesName, grades.name as gradeName, grades.alias as gradeAlias FROM hardwoodfloors floor " +
    		"LEFT JOIN plank_colors color ON floor.plank_color_id = color.id " +
    		"LEFT JOIN plank_sizes size ON floor.plank_size_id = size.id " +
    		"LEFT JOIN wood_species species ON floor.wood_species_id = species.id " +
    		"LEFT JOIN grades grades ON floor.grade_id = grades.id " +
    		"WHERE floor.id IN :ids")
    List<FloorColorSize> findFloorColorByIdIn(List<Long> ids);
}
