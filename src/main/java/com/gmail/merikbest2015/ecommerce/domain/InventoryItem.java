package com.gmail.merikbest2015.ecommerce.domain;

/**
 * @author yishi.xing
 * @created Feb 20, 2024 - 10:37:18 PM
 */
public interface InventoryItem {
	Long getId();
	String getWidth();
	String getColorName();
	String getGradeAlias();
	double getStock();
}
