package vidar.websystem.domain;

import lombok.Data;

import javax.persistence.*;

/**
 * @author yishi.xing
 * create datetime 4/19/2024 2:22 PM
 * description
 */
@Data
@Entity
@Table(name = "warehouses")
public class Warehouse {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "city", nullable = false)
    private String city;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "description")
    private String description;

    @Override
    public String toString(){
        return city;
    }
}
