package vidar.websystem.domain;

import java.util.Date;

import javax.persistence.*;

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

    @Column(name = "floor_id", nullable = false)
    private Long floorId;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "create_time", updatable = false, columnDefinition = "timestamp default current_timestamp")
    private Date createTime;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "update_time", columnDefinition = "timestamp default current_timestamp")
    private Date updateTime;

    @Column(name = "create_user_id", updatable = false)
    private Long createUserId;

    @Column(name = "update_user_id")
    private Long updateUserId;
    
    @Column(name = "current_quantity", nullable = false)
    private double currentQuantity;

    @Column(name = "initial_quantity", nullable = false)
    private double initialQuantity;
}
