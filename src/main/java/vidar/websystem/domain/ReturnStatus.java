package vidar.websystem.domain;

import lombok.*;

import javax.persistence.*;

/**
 * @author yishi.xing
 * create datetime 5/9/2024 3:30 PM
 * description
 */
@Entity
@Table(name = "return_status")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = {"id"})
public class ReturnStatus {

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
