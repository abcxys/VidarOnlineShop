package vidar.websystem.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * @author yishi.xing
 * create datetime 5/15/2024 3:32 PM
 * description
 */
@Entity
@Table(name = "shipping_methods")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = {"id"})
public class ShippingMethod {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String shippingMethodName;

    @Column(name = "alias")
    private String shippingMethodAlias;

    @Column(name = "description")
    private String description;

    @Override
    public String toString() {
        return shippingMethodName;
    }

}
