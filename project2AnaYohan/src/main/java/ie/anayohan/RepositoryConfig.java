package ie.anayohan;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;

import ie.anayohan.domain.Bid;
import ie.anayohan.domain.Job;
import ie.anayohan.domain.Role;
import ie.anayohan.domain.User;

@Configuration
public class RepositoryConfig implements RepositoryRestConfigurer {
	public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config) {
		config.exposeIdsFor(Job.class);
		config.exposeIdsFor(User.class);
		config.exposeIdsFor(Bid.class);
		config.exposeIdsFor(Role.class);
	}
}