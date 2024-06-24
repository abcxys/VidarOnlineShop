package vidar.websystem.domain;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.*;
import vidar.websystem.json.HardwoodFloorSerializer;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * @author yishi.xing
 * create datetime 6/23/2024 10:39 PM
 * description
 */
@Entity
@Table(name = "samples")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = {"id"})
public class Sample {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonSerialize(using = HardwoodFloorSerializer.class)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private HardwoodFloor product;

    @Column(name = "price", precision = 10, scale = 2, nullable = false)
    private BigDecimal price;

    @Column(name = "description")
    private String description;
}
