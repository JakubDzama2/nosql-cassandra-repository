package sk.upjs.cassandra_repository;

import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.datastax.driver.core.Session;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import sk.upjs.cassandra_repository.simple_student.SimpleStudentRepositoryService;
import sk.upjs.cassandra_repository.student.StudentRepositoryService;

/**
 * Hello world!
 *
 */

public class App2 {
	public static void main(String[] args) {
		Logger logger = (Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
		logger.setLevel(Level.INFO);
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(CassandraConfig.class);
		StudentRepositoryService repositoryService = context.getBean(StudentRepositoryService.class);
//		repositoryService.insertOneStudent();
//		repositoryService.insertAllStudents();
//		repositoryService.printStudents();
//		repositoryService.deleteAllStudents();
//		repositoryService.printStudents();
//		System.out.println(repositoryService.findByPriezvisko("Vuko"));
//		System.out.println(repositoryService.findByTitul("RNDr."));
//		System.out.println(repositoryService.findByTitul("RNDr."));
//		System.out.println(repositoryService.findByIdAndPriezvisko(1006326L, "Guta"));
		
//		repositoryService.insertAllSkratStudProgStudents();
//		repositoryService.printSkratStudProgStudents();
		System.out.println(repositoryService.findBySkratkaStudijnehoProgramu("CH"));
		
		context.getBean(Session.class).getCluster().close();
		context.close();
	}
}
