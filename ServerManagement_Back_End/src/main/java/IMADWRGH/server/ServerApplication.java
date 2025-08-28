package IMADWRGH.server;

import IMADWRGH.server.Repository.ServerRepository;
import IMADWRGH.server.enumeration.Status;
import IMADWRGH.server.model.Server;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;



import java.util.List;

@SpringBootApplication
public class ServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServerApplication.class, args);
	}


	@Bean
	CommandLineRunner run(ServerRepository serverRepository){
		return args -> {
			serverRepository.save(new Server(null,"192.168.1.160", "Ubuntu Linux","16 GB","Personal PC","http://localhost:8080/server/image/server1.png", Status.SERVER_UP));
			serverRepository.save(new Server(null,"192.168.1.58", "Fedora Linux","16 GB","Dell Tower","http://localhost:8080/server/image/server2.png", Status.SERVER_UP));
			serverRepository.save(new Server(null,"192.168.1.21", "MS 2008","32 GB","Web Server","http://localhost:8080/server/image/server3.png", Status.SERVER_UP));
			serverRepository.save(new Server(null,"192.168.1.14", "Red Hat Enterprise Linux","64 GB","Mail Server","http://localhost:8080/server/image/server4.png", Status.SERVER_UP));

		};
	}


	@Bean
	public CorsFilter corsFilter() {
		UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
		CorsConfiguration corsConfiguration = new CorsConfiguration();
		corsConfiguration.setAllowCredentials(true);
		corsConfiguration.setAllowedOrigins(List.of("http://localhost:4200"));
		corsConfiguration.setAllowedHeaders(List.of("Origin", "Access-Control-Allow-Origin", "Content-Type", "Accept", "Jwt-Token", "Authorization",
				"Origin, Accept", "X-Requested-With", "Access-Control-Request-Method", "Access-Control-Request-Headers"));
		corsConfiguration.setExposedHeaders(List.of("Origin", "Content-Type", "Accept", "Jwt-Token", "Authorization",
				"Access-Control-Allow-Origin", "Access-Control-Allow-Origin", "Access-Control-Allow-Credentials", "Filename"));
		corsConfiguration.setAllowedMethods(List.of("POST", "GET", "PUT", "DELETE", "OPTIONS", "PATCH"));
		urlBasedCorsConfigurationSource.registerCorsConfiguration("/**", corsConfiguration);
		return new CorsFilter(urlBasedCorsConfigurationSource);
	}
}
