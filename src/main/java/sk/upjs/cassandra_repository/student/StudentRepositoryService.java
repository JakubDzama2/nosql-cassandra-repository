package sk.upjs.cassandra_repository.student;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.cassandra.core.mapping.BasicMapId;
import org.springframework.data.cassandra.core.mapping.MapId;
import org.springframework.stereotype.Service;

import sk.upjs.nosql_data_source.persist.DaoFactory;
import sk.upjs.nosql_data_source.persist.StudentDao;

@Service
public class StudentRepositoryService {

	@Autowired
	StudentRepository repository;
	
	private StudentDao studentDao = DaoFactory.INSTANCE.getStudentDao();
	
	public void insertAllStudents() {
		List<CassandraStudent> cStudents = studentDao.getAll()
				.stream()
				.map(s -> new CassandraStudent(s))
				.collect(Collectors.toList());
		repository.saveAll(cStudents);
	}
	
	public void deleteAllStudents() {
		repository.deleteAll();
	}
	
	public void printStudents() {
		repository.findAll().forEach(System.out::println);
	}
	
	public Collection<NamesOnly> findByTitul(String titul) {
		return repository.findBySkratkaakadtitul(titul);
	}
	
	public CassandraStudent findByIdAndPriezvisko(Long id, String priezvisko) {
		MapId myId = BasicMapId.id("id", id).with("priezvisko", priezvisko);
		return repository.findById(myId).orElse(null);
	}
}
