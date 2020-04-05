package sk.upjs.cassandra_repository.student;

import java.util.List;
import java.util.Map.Entry;

import org.springframework.data.cassandra.core.mapping.CassandraType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import com.datastax.driver.core.DataType.Name;

/*
 
 CREATE TABLE IF NOT EXISTS skrat_stud_prog_student (
 	skratka text PRIMARY KEY,
 	studenti list<FROZEN<student>>
 );
 
  CREATE TYPE IF NOT EXISTS student (
	id bigint,
 	meno text,
 	priezvisko text,
 	titul text,
 	studia list<FROZEN<studium>>
);
  
 */

@Table(value = "skrat_stud_prog_student")
public class CassandraSkratStudProgStudents {
	
	@PrimaryKey()
	private String skratka;
	
	@Column()
	@CassandraType(type = Name.LIST, userTypeName = "student", typeArguments = {Name.UDT})
	private List<CassandraStudent> studenti;
	
	public CassandraSkratStudProgStudents() {
		
	}
	
	public CassandraSkratStudProgStudents(Entry<String, List<CassandraStudent>> entry) {
		this.skratka = entry.getKey();
		this.studenti = entry.getValue();
	}

	public String getSkratka() {
		return skratka;
	}

	public void setSkratka(String skratka) {
		this.skratka = skratka;
	}

	public List<CassandraStudent> getStudenti() {
		return studenti;
	}

	public void setStudenti(List<CassandraStudent> studenti) {
		this.studenti = studenti;
	}

	@Override
	public String toString() {
		return "CassandraSkratStudProgStudent [skratka=" + skratka + ", studenti=" + studenti + "]";
	}
		
}
