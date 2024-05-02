package vidar.websystem.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * @author yishi.xing
 * create datetime 5/2/2024 1:54 PM
 * description
 */
@Entity
@Table(name = "packing_status")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = {"id"})
public class PackingStatus {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String statusName;

    @Column(name = "alias")
    private String statusAlias;

    @Column(name = "description")
    private String description;

    @Override
    public String toString() {
        return statusAlias;
    }
}
