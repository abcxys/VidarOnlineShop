package vidar.websystem.domain;

import java.time.LocalDateTime;

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

    @Column(name = "created_at", columnDefinition = "timestamp default current_timestamp")
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "last_modified_at", columnDefinition = "timestamp default current_timestamp")
    private LocalDateTime lastModifiedAt = LocalDateTime.now();
    
    @Column(name = "current_quantity", nullable = false)
    private double currentQuantity;

    @Column(name = "initial_quantity", nullable = false)
    private double initialQuantity;
}
