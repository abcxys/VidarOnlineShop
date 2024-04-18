package vidar.websystem.domain;

import lombok.*;

import javax.persistence.*;

/**
 * @author yishi.xing
 * create datetime 4/18/2024 2:57 PM
 * description
 */
@Entity
@Table(name = "dealers")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = {"id"})
public class Dealer {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "type_id", nullable = false)
    private Long typeId;

    @Column(name = "company_name", nullable = false)
    private String companyName;

    @Column(name = "full_name", nullable = false)
    private String fullName;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;

    @Column(name = "main_email1", nullable = false)
    private String mainEmail1;

    @Column(name = "main_email2")
    private String mainEmail2;

    @Column(name = "cc_email")
    private String ccEmail;

    @Column(name = "active", nullable = false)
    private boolean active;

    @Column(name = "description")
    private String description;

    @Override
    public String toString(){
        return companyName;
    }
}
