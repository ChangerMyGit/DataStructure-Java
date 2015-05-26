package string;

/**
 * ��
 * 
 * @author lyb
 * 
 */
public class MyString {

	private int[] buildNext(String p) {
		int m = p.length();
		int j = 0;// ��������ָ��
		int N[] = new int[m];
		int t = N[0] = -1;
		while (j < m - 1) {
			if (0 > t || p.charAt(j) == p.charAt(t)) { // ƥ��
				j++;
				t++;
				N[j] = t; // �˾�ɸĽ�...
			} else
				// ʧ��
				t = N[t];
		}
		return N;
	}

	public int match(String text, String patten) {
		int n = text.length();
		int m = patten.length();
		int i, j, k;
		for (i = j = 0; i < n;) {
			k = i;
			while (true) {
				if (j < m && text.charAt(k) == patten.charAt(j)) {
					k++;
					j++;
				} else if (j == m) {
					return i;
				} else {
					i++;
					j = 0;
					break;
				}
			}
		}
		return -1;
	}

	public int match2(String text, String patten) {
		int n = text.length();
		int m = patten.length();
		int i = 0, j = 0;
		while (i < n && j < m) {
			if (text.charAt(i) == patten.charAt(j)) {
				i++;
				j++;
			} else {
				i = i - j + 1;
				j = 0;
			}
		}
		return i - j;
	}

	public int kmpMatch(String text, String patten) {
		int next[] = buildNext(patten);
		int n = text.length();
		int m = patten.length();
		int i = 0, j = 0;
		while (i < n && j < m) {
			if (j < 0 || text.charAt(i) == patten.charAt(j)) {
				i++;
				j++;
			} else {
				j = next[j];// ģʽ�����ƣ�ע�⣺�ı������û��ˣ�
			}
		}
		return i - j;
	}
}
