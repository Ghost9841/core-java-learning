public class Book {
    private String title;
	private String author;
	private double price;
	void displayDetails(){
		System.out.println("Title: " + title + "Author: "+ author + "Price: " + price);
	}
	void setTitle(String title) {
		this.title = title;
	}
	void setAuthor(String author) {
		this.author = author;
	}
	void setPrice(double price) {
		if (price > 0) {
			
			this.price = price;
		}else{
			System.out.println("Price is negative");
		}
	}
	public String getTitle() {
		return title;
	}
	public String getAuthor() {
		return author;
	}
	public double getPrice() {
		return price;
	}
	
}