package management.entity;

import java.util.UUID;

public class Person {
	private String id;
	private String name;
	private String password;
	public Person(){
		
	}
	public Person(String name, String password) {
		this.id=UUID.randomUUID().toString();
		this.name = name;
		this.password = password;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public void setId(){
		this.id=UUID.randomUUID().toString();
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public boolean equals(Person person){
		if(this.getName().equals(person.getName())&&this.getPassword().equals(person.password)){
			return true;
		}
		return false;
	}
}
