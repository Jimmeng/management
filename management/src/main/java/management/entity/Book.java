package management.entity;

import java.util.UUID;

public class Book {
	private String id;
	private String name;
	private String writer;
	private double price;
	private int number;
	public void borrow(){
		number--;
	}
	public void borrow(int num){
		number-=num;
	}
	public void returnback(){
		number++;
	}
	public void returnback(int num){
		number+=num;
	}
	public Book(){
		
	}
	public Book(String name, String writer, double price, int number) {
		id=UUID.randomUUID().toString();
		this.name = name;
		this.writer = writer;
		this.price = price;
		this.number = number;
	}
	public Book(String id, String name, String writer, double price, int number) {
		this.id = id;
		this.name = name;
		this.writer = writer;
		this.price = price;
		this.number = number;
	}
	public Book(String id, String name, String writer, String price, String number) {
		this.id = id;
		this.name = name;
		this.writer = writer;
		this.price = Double.parseDouble(price);
		this.number = Integer.parseInt(number);
	}
	@Override
	public String toString() {
		return "{id:" + id + ", name:" + name + ", writer:" + writer
				+ ", price:" + price + ", number:" + number + "}";
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
	public String getWriter() {
		return writer;
	}
	public void setWriter(String writer) {
		this.writer = writer;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	/*public void setPrice(String price) {
		this.price = Double.parseDouble(price);
	}*/
	public int getNumber() {
		return number;
	}
	/*public void setNumber(int number) {
		this.number = number;
	}*/
	public void setNumber(double number) {
		this.number = (int)number;
	}
	/*public void setNumber(String number) {
		this.number = Integer.parseInt(number);
	}*/
}
