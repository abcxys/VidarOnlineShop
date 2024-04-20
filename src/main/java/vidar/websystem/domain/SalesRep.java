package vidar.websystem.domain;

import lombok.*;

import javax.persistence.*;

/**
 * @author yishi.xing
 * create datetime 4/19/2024 4:52 PM
 * description
 */
@Entity
@Table(name = "sales_reps")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = {"id"})
public class SalesRep {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "alias")
    private String alias;

    @Column(name = "description")
    private String description;

    @Override
    public String toString() {
        return alias;
    }
}
