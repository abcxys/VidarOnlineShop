package vidar.websystem.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author yishi.xing
 * @created Mar 27, 2024 - 4:30:34 PM
 */
@Entity
@Table(name = "grades")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = {"id"})
public class Grade {
	@Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "grade_id_seq")
    @SequenceGenerator(name = "grade_id_seq", sequenceName = "grade_id_seq", initialValue = 4, allocationSize = 1)
    private Long id;
	
	@Column(name = "name", nullable = false)
    private String name;
	
	@Column(name = "alias", nullable = false)
    private String alias;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "create_time" ,updatable = false)
	private Date create_time;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "update_time")
	private Date update_time;
	
	@Column(name = "create_user_id" ,updatable = false)
	private Long create_user_id;
	
	@Column(name = "update_user_id")
	private Long update_user_id;
	
	@Column(name = "description", nullable = true)
    private String description;
	
	@Override
	public String toString() {
		return name;
	}
}
