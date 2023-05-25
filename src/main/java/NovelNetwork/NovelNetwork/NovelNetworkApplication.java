package NovelNetwork.NovelNetwork;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@ComponentScan
//@EnableJpaRepositories(basePackages = "NovelNetwork.NovelNetwork.Repository")
public class NovelNetworkApplication {
	public static void main(String[] args) {
		SpringApplication.run(NovelNetworkApplication.class, args);

	/* 	String pythonScriptPath = "scrayper.py";

		String[] cmd = {
            "python",
            pythonScriptPath
        };

        try {
            Process pr = Runtime.getRuntime().exec(cmd);
            
            // 파이썬 스크립트의 출력을 읽기
            InputStream in = pr.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }    */

	}
}