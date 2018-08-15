package pojo;

public class Product {
	private int id;
	private String NAME;
	private double price;
	private String mark;
	
	public Product(int id, String nAME, double price, String mark) {
		super();
		this.id = id;
		NAME = nAME;
		this.price = price;
		this.mark = mark;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNAME() {
		return NAME;
	}
	public void setNAME(String nAME) {
		NAME = nAME;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public String getMark() {
		return mark;
	}
	public void setMark(String mark) {
		this.mark = mark;
	}
}
