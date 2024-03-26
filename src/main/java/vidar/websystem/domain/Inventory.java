package vidar.websystem.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

/**
 * @author yishi.xing
 * @created Feb 20, 2024 - 11:17:38 PM
 */
@Data
@Entity
@Table(name = "inventory")
public class Inventory {
	@Id
    @Column(name = "id")
    private Long id;

    @Column(name = "floor_id", nullable = true)
    private Long floorId;

    @Column(name = "create_time", updatable = false, columnDefinition = "timestamp default current_timestamp")
    private Date create_time;

    @Column(name = "update_time", columnDefinition = "timestamp default current_timestamp")
    private Date update_time;
    
    @Column(name = "current_quantity", nullable = false)
    private double currentQuantity;

    @Column(name = "initial_quantity", nullable = false)
    private double initialQuantity;
}
