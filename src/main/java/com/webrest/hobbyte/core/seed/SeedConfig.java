/**
 * 
 */
package com.webrest.hobbyte.core.seed;

import java.util.List;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.flyway.FlywayAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.webrest.hobbyte.core.logger.IActionLogger;
import com.webrest.hobbyte.core.logger.LoggerFactory;
import com.webrest.hobbyte.core.seed.ISeed.SeedExecuteResult;

/**
 * @author Emil Wojewódka
 *
 * @since 20 maj 2018
 */
@Configuration
@ConditionalOnClass(FlywayAutoConfiguration.class)
public class SeedConfig {

	private static final Logger LOGGER = LoggerFactory.getLogger();

	@Autowired
	private List<ISeed> seeds;

	@Bean
	public Class<Void> executeSeeds() {
		IActionLogger actionLogger = LoggerFactory.getActionLogger("Execute seeds.");
		actionLogger.start();
		seeds.sort((o1, o2) -> {
			return o1.getCreateDate().compareTo(o2.getCreateDate());
		});
		seeds.forEach(seed -> {
			LOGGER.info("Seed {} execute status [{}]", seed.getClass(),
					(seed.needExecute() ? seed.execute() : SeedExecuteResult.SKIPED));
		});
		actionLogger.stop();
		return Void.TYPE;
	}

}
