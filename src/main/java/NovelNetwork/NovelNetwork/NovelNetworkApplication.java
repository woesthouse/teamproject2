package NovelNetwork.NovelNetwork;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan
//@EnableJpaRepositories(basePackages = "NovelNetwork.NovelNetwork.Repository")
public class NovelNetworkApplication {
	public static void main(String[] args) {
		SpringApplication.run(NovelNetworkApplication.class, args);

	}
}