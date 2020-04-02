package sk.upjs.cassandra_repository.simple_student;

import java.util.List;

import org.springframework.data.cassandra.repository.AllowFiltering;
import org.springframework.data.repository.CrudRepository;

public interface SimpleStudentRepository extends CrudRepository<CassandraSimpleStudent, Long> {

	List<CassandraSimpleStudent> findByPriezvisko(String priezvisko);
	
	@AllowFiltering
	List<CassandraSimpleStudent> findBySkratkaakadtitul(String titul);
}
