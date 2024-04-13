package vidar.websystem.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * @author yishi.xing
 * create datetime 4/12/2024 9:53 AM
 * description
 */
@Entity
@Table(name = "container_floors")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = {"id"})
public class ProductContainerItem {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "floor_id")
    private Long productId;

    @Column(name = "skid")
    private Integer skid;

    @Column(name = "quantity")
    private BigDecimal box;
}
