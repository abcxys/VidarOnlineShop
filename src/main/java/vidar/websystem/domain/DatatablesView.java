package vidar.websystem.domain;

import java.util.List;

import lombok.Data;

/**
 * @author yishi.xing
 * @created Feb 15, 2024 - 10:27:12 PM
 */
@Data
public class DatatablesView<T> {

	private List<T> data; // data 与datatales 加载的“dataSrc"对应

	private int recordsTotal;

	private int recordsFiltered;

	private int draw;

	public DatatablesView() {

	}

	public void setRecordsTotal(int recordsTotal) {
		this.recordsTotal = recordsTotal;
		this.recordsFiltered = recordsTotal;
	}
}