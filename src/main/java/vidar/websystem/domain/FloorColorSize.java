package vidar.websystem.domain;

/**
 * @author yishi.xing
 * @created Feb 4, 2024 - 10:01:46 PM
 */
public interface FloorColorSize {
	Long getId();
	String getColorName();
	String getWidth();
	String getLength();
	String getThickness();
	double getSqftPerCarton();
	String getWoodSpeciesName();
	String getInstallationPatternName();
	String getGradeName();
	String getGradeAlias();
	String getFilename();
}
