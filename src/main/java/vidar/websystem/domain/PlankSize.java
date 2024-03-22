package vidar.websystem.domain;

import lombok.*;

import javax.persistence.*;

/**
 * @author yishi.xing
 * @created Feb 2, 2024 - 10:06:12 PM
 */
@Entity
@Table(name = "plank_sizes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = {"id"})
public class PlankSize {
	@Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "plank_size_id_seq")
    @SequenceGenerator(name = "plank_size_id_seq", sequenceName = "plank_size_id_seq", initialValue = 1, allocationSize = 1)
    private Long id;
	
	@Column(name = "width_in_inch", nullable = false)
    private String width_in_inch;
	
	@Column(name = "length", nullable = false)
    private String length;
	
	@Column(name = "thickness_in_inch", nullable = false)
    private String thickness_in_inch;
	
	@Column(name = "squarefoot_per_carton", nullable = false)
    private double squarefoot_per_carton;
	
	@Column(name = "description", nullable = false)
    private String description;
}
