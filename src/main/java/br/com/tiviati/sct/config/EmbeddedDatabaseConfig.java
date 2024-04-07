package br.com.tiviati.sct.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import javax.sql.DataSource;

import static br.com.tiviati.sct.commons.Constants.DATASOURCE_BEAN_ERROR;

public class EmbeddedDatabaseConfig {
    private static final Logger LOG = LoggerFactory.getLogger(EmbeddedDatabaseConfig.class);

    @Bean
    public DataSource dataSource() {
        try {
            var dbBuilder = new EmbeddedDatabaseBuilder();
            return dbBuilder.setType(EmbeddedDatabaseType.H2)
                    .addScripts("classpath:schema.sql")
                    .build();
        } catch (Exception e) {
            LOG.info(DATASOURCE_BEAN_ERROR, e);
            return null;
        }
    }
}