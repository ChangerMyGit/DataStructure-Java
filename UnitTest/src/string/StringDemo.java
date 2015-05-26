package string;

public class StringDemo {

	public static void main(String[] args) {
		MyString myString = new MyString();
		System.out.println(myString.match("Hello I am Changer !!", "Changer"));
		System.out.println(myString.match2("Hello I am Changer !!", "Changer"));
		System.out.println(myString.kmpMatch("Hello I am Changer !!", "Changer"));
	}
}
