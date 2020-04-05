package sk.upjs.cassandra_repository.student;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.cassandra.core.mapping.BasicMapId;
import org.springframework.data.cassandra.core.mapping.MapId;
import org.springframework.stereotype.Service;

import sk.upjs.nosql_data_source.entity.Student;
import sk.upjs.nosql_data_source.entity.Studium;
import sk.upjs.nosql_data_source.persist.DaoFactory;
import sk.upjs.nosql_data_source.persist.StudentDao;

@Service
public class StudentRepositoryService {

	@Autowired
	StudentRepository studentRepository;
	
	@Autowired
	SkratStudProgStudentsRepository skratStudProgStudentsRepository;
	
	private StudentDao studentDao = DaoFactory.INSTANCE.getStudentDao();
	
	public void insertAllStudents() {
		List<CassandraStudent> cStudents = studentDao.getAll()
				.stream()
				.map(s -> new CassandraStudent(s))
				.collect(Collectors.toList());
		studentRepository.saveAll(cStudents);
	}
	
	public void insertAllSkratStudProgStudents() {
		Map<String, List<CassandraStudent>> map = new HashMap<String, List<CassandraStudent>>();
		List<Student> students = studentDao.getAll();
		for (Student student: students) {
			for (Studium studium: student.getStudium()) {
				String skratka = studium.getStudijnyProgram().getSkratka();
				if (!map.containsKey(skratka)) {
					map.put(skratka, new ArrayList<CassandraStudent>());
				}
				map.get(skratka).add(new CassandraStudent(student));
			}
		}
		List<CassandraSkratStudProgStudents> cassandraSkratStudProgStudents = map.entrySet().stream()
			.map(entry -> new CassandraSkratStudProgStudents(entry))
			.collect(Collectors.toList());
		skratStudProgStudentsRepository.saveAll(cassandraSkratStudProgStudents);
	}
	
	public void deleteAllStudents() {
		studentRepository.deleteAll();
	}
	
	public void deleteAllSkratStudProgStudents() {
		skratStudProgStudentsRepository.deleteAll();
	}
	
	public void printStudents() {
		studentRepository.findAll().forEach(System.out::println);
	}
	
	public void printSkratStudProgStudents() {
		skratStudProgStudentsRepository.findAll().forEach(System.out::println);
	}
	
	public List<CassandraStudent> findBySkratkaStudijnehoProgramu(String skratka) {
		CassandraSkratStudProgStudents skratStudProgStudents = skratStudProgStudentsRepository.findById(skratka).orElse(null);
		return skratStudProgStudents == null ? new ArrayList<CassandraStudent>() : skratStudProgStudents.getStudenti(); 
	}
	
	public Collection<NamesOnly> findByTitul(String titul) {
		return studentRepository.findBySkratkaakadtitul(titul);
	}
	
	public CassandraStudent findByIdAndPriezvisko(Long id, String priezvisko) {
		MapId myId = BasicMapId.id("id", id).with("priezvisko", priezvisko);
		return studentRepository.findById(myId).orElse(null);
	}
}
