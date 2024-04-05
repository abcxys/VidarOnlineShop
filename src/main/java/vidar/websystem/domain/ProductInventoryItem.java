package vidar.websystem.domain;

/**
 * @author yishi.xing
 * @created Feb 20, 2024 - 10:37:18 PM
 */
public interface ProductInventoryItem {
	Long getId();
	String getWidth();
	String getColorName();
	String getGradeAlias();
	String getSpeciesName();
	String getBatch();
	String getBay();
	double getStock();
}