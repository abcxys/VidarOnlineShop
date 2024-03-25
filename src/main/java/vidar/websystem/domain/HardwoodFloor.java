package vidar.websystem.domain;

import lombok.*;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.HashSet;
import java.util.Set;

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

    @Column(name = "perfume_title", nullable = true)
    private String perfumeTitle;

    @Column(name = "perfumer", nullable = true)
    private String perfumer;

    @Column(name = "year", nullable = true)
    private Integer year;

    @Column(name = "country", nullable = true)
    private String country;

    @Column(name = "perfume_gender", nullable = true)
    private String perfumeGender;

    @Column(name = "fragrance_top_notes", nullable = true)
    private String fragranceTopNotes;

    @Column(name = "fragrance_middle_notes", nullable = true)
    private String fragranceMiddleNotes;

    @Column(name = "fragrance_base_notes", nullable = true)
    private String fragranceBaseNotes;

    @Column(name = "description")
    private String description;

    @Column(name = "filename")
    private String filename;

    @Column(name = "price", nullable = true)
    private float price;

    @Column(name = "volume", nullable = true)
    private String volume;

    @Column(name = "type", nullable = true)
    private String type;
    
    @Column(name = "plank_size_id", nullable = false)
    private Long plank_size_id;
    
    @Column(name = "plank_color_id", nullable = false)
    private Long plank_color_id;
//    
//    @OneToMany(mappedBy = "hardwoodfloor")
//    private Set<FloorOrder> floorOrders = new HashSet<FloorOrder>();
}
