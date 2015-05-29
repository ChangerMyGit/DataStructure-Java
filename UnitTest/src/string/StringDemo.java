package string;

public class StringDemo {

	public static void main(String[] args) {
		MyString myString = new MyString();
		//System.out.println(myString.match("00001100", "1100"));
		//System.out.println(myString.match2("00001100", "1100"));
		System.out.println(myString.kmpMatch("chinche chinchilla", "chinchi"));
	}
}
