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

    @Column(name = "year", nullable = true)
    private Integer year;

    @Column(name = "country", nullable = true)
    private String country;

    @Column(name = "description")
    private String description;

    @Column(name = "filename")
    private String filename;

    @Column(name = "price", nullable = false)
    private float price;
    
    @Column(name = "plank_size_id", nullable = false)
    private Long plank_size_id;
    
    @Column(name = "plank_color_id", nullable = false)
    private Long plank_color_id;
    
    @Column(name = "wood_species_id", nullable = false)
    private Long wood_species_id;
    
    @Column(name = "plank_type_id", nullable = false)
    private Long plank_type_id;
    
    @Column(name = "grade_id", nullable = false)
    private Long grade_id;
    
    @Column(name = "batch_id", nullable = false)
    private String batch_id;
    
    @Column(name = "cartons_per_skid", nullable = false)
    private Integer cartons_per_skid;
    
    @Column(name = "carton_weight", nullable = true)
    private float carton_weight = 60f;
    
    @Column(name = "wear_layer_thickness", nullable = false)
    private float wear_layer_thickness = 2f;
//    
//    @OneToMany(mappedBy = "hardwoodfloor")
//    private Set<FloorOrder> floorOrders = new HashSet<FloorOrder>();
}
