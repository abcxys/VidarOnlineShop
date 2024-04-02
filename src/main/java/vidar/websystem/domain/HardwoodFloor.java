package vidar.websystem.domain;

import lombok.*;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.math.BigDecimal;
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

    @Column(name = "price", precision = 10, scale = 2, nullable = false)
    private BigDecimal price;
    
    @Column(name = "plank_size_id", nullable = false)
    private Long sizeId;
    
    @Column(name = "plank_color_id", nullable = false)
    private Long colorId;
    
    @Column(name = "wood_species_id", nullable = false)
    private Long speciesId;
    
    @Column(name = "plank_type_id", nullable = false)
    private Long typeId;
    
    @Column(name = "grade_id", nullable = false)
    private Long gradeId;
    
    @Column(name = "batch_id", nullable = false)
    private String batchNumber;
    
    @Column(name = "cartons_per_skid", nullable = false)
    private Integer cartonsPerSkid;
    
    @Column(name = "carton_weight", nullable = true)
    private float cartonWeight = 60f;
    
    @Column(name = "wear_layer_thickness", nullable = false)
    private float wearThickness = 2f;
    
    @Column(name = "finish", nullable = true)
    private String finish;
    
    @Column(name = "active", nullable = false)
    private boolean active = true;
//    
//    @OneToMany(mappedBy = "hardwoodfloor")
//    private Set<FloorOrder> floorOrders = new HashSet<FloorOrder>();
}
