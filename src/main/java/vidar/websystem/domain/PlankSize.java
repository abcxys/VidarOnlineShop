package vidar.websystem.domain;

import lombok.*;

import java.math.BigDecimal;
import java.util.Date;

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
    @SequenceGenerator(name = "plank_size_id_seq", sequenceName = "plank_size_id_seq", initialValue = 5, allocationSize = 1)
    private Long id;
	
	@Column(name = "width_in_inch", nullable = false)
    private String widthInInch;
	
	@Column(name = "length", nullable = false)
    private String length;
	
	@Column(name = "thickness_in_inch", nullable = false)
    private String thicknessInInch;
	
	@Column(name = "squarefoot_per_carton", precision = 10, scale = 2, nullable = false)
    private BigDecimal squarefootPerCarton;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "create_time", updatable = false)
	private Date create_time;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "update_time")
	private Date update_time;
	
	@Column(name = "create_user_id", updatable = false)
	private Long create_user_id;
	
	@Column(name = "update_user_id")
	private Long update_user_id;
	
	@Column(name = "description", nullable = false)
    private String description;
	
	@Override
	public String toString() {
		return widthInInch + " inch x " + thicknessInInch + " inch x "
	+ length + " " + String.valueOf(squarefootPerCarton);
	}
}
