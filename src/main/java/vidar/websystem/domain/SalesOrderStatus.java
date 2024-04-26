package vidar.websystem.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * @author yishi.xing
 * create datetime 4/26/2024 3:04 PM
 * description
 */
@Entity
@Table(name = "sales_order_status")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = {"id"})
public class SalesOrderStatus {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "status_name")
    private String statusName;

    @Column(name = "status_alias")
    private String statusAlias;

    @Column(name = "description")
    private String description;

    @Override
    public String toString(){
        return statusAlias;
    }
}
