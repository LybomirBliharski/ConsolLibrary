package consol.libr.main;

import java.io.IOException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import consol.libr.config.DatabaseConfig;

public class Mains {

	public static void main(String[] args) throws IOException {
		ApplicationContext context = new AnnotationConfigApplicationContext(DatabaseConfig.class);
		App app = context.getBean(App.class);
		app.run();
	}
}
