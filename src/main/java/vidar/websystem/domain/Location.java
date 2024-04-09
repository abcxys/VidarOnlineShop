package vidar.websystem.domain;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author yishi.xing
 * create datetime 4/9/2024 10:14 AM
 * description
 */
@Data
@Entity
@Table(name = "locations")
public class Location {
    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "warehouse_id", nullable = false)
    private Long warehouseId;

    @Column(name = "bay", nullable = false)
    private String bay;

    @Column(name = "description")
    private String description;
}
