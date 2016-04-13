import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.TreeMap;
import java.util.Random;
import java.util.Scanner;

/**
 * Program yang akan menjalankan game Acak Kata
 * @author Muhammad Rif'at - Ilmu Komputer, Universitas Indonesia
 * Koding Test Internship Veritrans
 * version 1.0
 */
public class AcakKata {
	public static void main(String[] args) throws IOException {
		/**Simpan list kata dari file kataKata.txt ke dalam TreeMap**/
		BufferedReader br = new BufferedReader(new FileReader("kataKata.txt"));
		String word = "";
		TreeMap<Integer, String> korpus = new TreeMap<>();
		int index = 0;
		while ((word = br.readLine()) != null) {
			korpus.put(index, word);
			index++;
		}
		
		/**Jalankan game**/
		run(korpus);
		
		/**Game telah selesai**/
		System.exit(0);
		
	}
	
	/**
	 * Method untuk menjalankan game
	 * @param korpus adalah TreeMap berisi list kata
	 */
	public static void run(TreeMap<Integer,String> korpus) {
		/**Keterangan game**/
		System.out.println();
		System.out.println("=====SELAMAT DATANG DI GAME ACAK KATA!=====");
		System.out.println("INSTRUCTION:");
		System.out.println("Pada game ini, Anda diminta untuk menebak kata yang benar dari kata yang telah diacak.");
		System.out.println("Silakan masukkan tebakan Anda pada bagian \"Jawab\"");
		System.out.println("Permainan akan berlangsung secara berulang-ulang");
		System.out.println("Jika Anda ingin melewati suatu kata (pass), silakan ketikkan \"-pass-\" pada jawaban Anda (tanpa tanda kutip)");
		System.out.println("Jika Anda ingin berhenti dari game ini, silakan ketikkan \"-quit-\" pada jawaban Anda (tanpa tanda kutip)");
		System.out.println("------------------------------------------------------------------------");
		System.out.println("Inputkan apa saja untuk melanjutkan!");
		
		Scanner scan = new Scanner(System.in);
		String lanjut = scan.nextLine();
		System.out.println();
		
		int size = korpus.size();
		Random rand = new Random();
		boolean stopGame = false;
		
		/**Game berjalan secara looping**/
		while (!stopGame) {
			int index = rand.nextInt(size);
			String kata = korpus.get(index); //memilih kata secara random dari korpus
			String shuffled = shuffle(kata); //mengacak kata
			System.out.println("Tebak kata: "+shuffled);
			System.out.print("Jawab: ");
			
			boolean berhentiJawab = false;
			String jawaban = "";
			//User menebak sampai jawabannya benar, atau pass, atau berhenti dari game
			while (!berhentiJawab) {
				jawaban = scan.nextLine();
				if (jawaban.equalsIgnoreCase("-quit-")) {
					//Pemain ingin berhenti dari game
					berhentiJawab = true;
					stopGame = true;
				} else if (jawaban.equalsIgnoreCase("-pass-")) {
					//Pemain ingin menebak kata lain karena terlalu sulit
					berhentiJawab = true;
					System.out.println("Sayang sekali, jawabannya adalah: "+kata);
				} else {
					//Pemain menebak suatu kata
					if (jawaban.equalsIgnoreCase(kata)) {
						//jika jawaban benar
						berhentiJawab = true;
						System.out.println("BENAR!");
					} else {
						//Jika jawaban salah, diminta menebak lagi
						System.out.println("SALAH! Silakan coba lagi");
						System.out.print("Jawab: ");
					}
				}	
			}
			System.out.println("======***======");
		}
		System.out.println("Terima kasih telah memainkan game Acak Kata!");
	}
	
	/**
	 * Method untuk mengacak suatu kata
	 * @param kata adalah string yang ingin diacak
	 * @return string hasil pengacakan kata
	 */
	public static String shuffle(String kata) {
		Random random = new Random();
		String result = "";
		int len = kata.length();
		
		while (kata != "") {
			int idx = random.nextInt(len);
			result += kata.charAt(idx);
			if (idx == 0) {
				if (len == 1) {
					kata = "";
				} else {
					kata = kata.substring(1);
				}
			} else if (idx+1 == len) {
				kata = kata.substring(0,len-1);
			} else {
				kata = kata.substring(0,idx)+kata.substring(idx+1);
			}
			len--;
		}
		return result;
	}
}