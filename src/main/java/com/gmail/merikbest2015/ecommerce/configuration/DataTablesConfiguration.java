package com.gmail.merikbest2015.ecommerce.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.jpa.datatables.repository.DataTablesRepositoryFactoryBean;

/**
 * @author yishi.xing
 * @created Feb 13, 2024 - 10:34:07 PM
 */
@Configuration
@EnableJpaRepositories(repositoryFactoryBeanClass = DataTablesRepositoryFactoryBean.class)
public class DataTablesConfiguration {

}
