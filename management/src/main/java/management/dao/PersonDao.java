package management.dao;

import management.entity.Person;
public interface PersonDao {
	public Person getPerson(Person person);
	public Person ifPersonExistByName(String string);
	public boolean addPerson(Person person);
	public boolean updatePerson(Person person);
	public boolean deletePerson(String id);
}
