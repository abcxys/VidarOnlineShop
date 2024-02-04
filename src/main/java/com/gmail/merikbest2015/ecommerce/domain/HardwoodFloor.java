package com.gmail.merikbest2015.ecommerce.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "hardwoodfloors")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = {"id"})
public class HardwoodFloor {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "hardwoodfloor_id_seq")
    @SequenceGenerator(name = "hardwoodfloor_id_seq", sequenceName = "hardwoodfloor_id_seq", initialValue = 109, allocationSize = 1)
    private Long id;

    @Column(name = "perfume_title", nullable = false)
    private String perfumeTitle;

    @Column(name = "perfumer", nullable = false)
    private String perfumer;

    @Column(name = "year", nullable = false)
    private Integer year;

    @Column(name = "country", nullable = false)
    private String country;

    @Column(name = "perfume_gender", nullable = false)
    private String perfumeGender;

    @Column(name = "fragrance_top_notes", nullable = false)
    private String fragranceTopNotes;

    @Column(name = "fragrance_middle_notes", nullable = false)
    private String fragranceMiddleNotes;

    @Column(name = "fragrance_base_notes", nullable = false)
    private String fragranceBaseNotes;

    @Column(name = "description")
    private String description;

    @Column(name = "filename")
    private String filename;

    @Column(name = "price", nullable = false)
    private Integer price;

    @Column(name = "volume", nullable = false)
    private String volume;

    @Column(name = "type", nullable = false)
    private String type;
    
    @Column(name = "plank_size_id", nullable = false)
    private Long plank_size_id;
    
    @Column(name = "plank_color_id", nullable = false)
    private Long plank_color_id;
}
