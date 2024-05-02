package vidar.websystem.domain;

import lombok.*;

import javax.persistence.*;

/**
 * @author yishi.xing
 * create datetime 5/2/2024 3:46 PM
 * description
 */
@Entity
@Table(name = "drivers")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = {"id"})
public class Driver {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "cellphone")
    private String cellphone;

    @Column(name = "description")
    private String description;

    @Override
    public String toString() {
        return name + " - " + cellphone;
    }
}
