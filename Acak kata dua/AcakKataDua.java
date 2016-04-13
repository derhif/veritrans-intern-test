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
 * version 2.0
 */
public class AcakKataDua {
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
		Game game = new Game(korpus);
		game.run();
		
		/**Game telah selesai**/
		System.exit(0);
		
	}
	
	/**
	 * Method untuk menjalankan game
	 * @param korpus adalah TreeMap berisi list kata
	 */
	
	
	/**
	 * Method untuk mengacak suatu kata
	 * @param kata adalah string yang ingin diacak
	 * @return string hasil pengacakan kata
	 */
}

class Game {
	private TreeMap<Integer,String> korpus;
	private int skor;
	private int[] help = new int[2];
	private int successInARow;
	
	public Game(TreeMap<Integer, String> korpus) {
		this.korpus = korpus;
		this.skor = 50;
		this.help[0] = 1; 
		this.help[1] = 0;
		this.successInARow = 0;
	}
	
	public void run() {
		/**Keterangan game**/
		System.out.println();
		System.out.println("=====SELAMAT DATANG DI GAME ACAK KATA!=====");
		System.out.println("INSTRUCTION:");
		System.out.println("1. Pada game ini, Anda diminta untuk menebak kata yang benar dari kata yang telah diacak.");
		System.out.println("2. Silakan masukkan tebakan Anda pada bagian \"Jawab\"");
		System.out.println("3. Permainan akan berlangsung secara berulang-ulang");
		System.out.println("4. Jika Anda ingin melewati suatu kata (pass), silakan ketikkan \"-pass-\" pada jawaban Anda (tanpa tanda kutip)");
		System.out.println("5. Jika Anda ingin berhenti dari game ini, silakan ketikkan \"-quit-\" pada jawaban Anda (tanpa tanda kutip)");
		System.out.println("6. Skor saat awal bermain adalah 50");
		System.out.println("7a. Setiap jawaban benar akan mendapat 10 poin");
		System.out.println("7b. Pass akan dikenakan penalti 5 poin, sedangkan salah menjawab akan dikenakan penalti 3 poin");
		System.out.println("8. Game akan berakhir jika skor Anda mencapai NOL");
		System.out.println("9a. Ada dua bantuan yang tersedia, yaitu cheat dan Healing");
		System.out.println("9b. Bantuan akan bertambah jika Anda berhasil menjawab benar 3 kali berturut-turut");
		System.out.println("10. Cheat akan menampilkan sebagian huruf dari kata asli dalam urutan yang benar (Gunakan perintah -cheat-)");
		System.out.println("11. Healing akan menambahkan skor Anda (Gunakan perintah -healing-)");
		System.out.println("12. Selamat bersenang-senang, raihlah skor yang tinggi :D");
		System.out.println("------------------------------------------------------------------------");
		System.out.println("Inputkan apa saja untuk melanjutkan!");
		
		Scanner scan = new Scanner(System.in);
		String lanjut = scan.nextLine();
		System.out.println();
		
		int size = this.korpus.size();
		Random rand = new Random();
		boolean stopGame = false;
		
		/**Game berjalan secara looping**/
		while (!stopGame) {
			int index = rand.nextInt(size);
			boolean mayCheat = true;
			String kata = this.korpus.get(index); //memilih kata secara random dari korpus
			String shuffled = this.shuffle(kata); //mengacak kata
			System.out.println("Tebak kata: "+shuffled);
			System.out.print("Jawab: ");
			
			boolean berhentiJawab = false;
			String jawaban = "";
			//User menebak sampai jawabannya benar, atau pass, atau berhenti dari game
			while (!berhentiJawab) {
				if (this.skor <= 0) {
					berhentiJawab = true;
					stopGame = true;
				} else {
					jawaban = scan.nextLine();
					if (jawaban.equalsIgnoreCase("-quit-")) {
						//Pemain ingin berhenti dari game
						berhentiJawab = true;
						stopGame = true;
					} else if (jawaban.equalsIgnoreCase("-pass-")) {
						//Pemain ingin menebak kata lain karena terlalu sulit
						berhentiJawab = true;
						System.out.println("Sayang sekali, jawabannya adalah: "+kata);
						this.skor -= 5;
						System.out.println("Penalti 5 poin. Skor Anda saat ini: "+this.skor);
					} else if (jawaban.equalsIgnoreCase("-healing-")) {
						if (help[1] == 0) {
							System.out.println("Sayang sekali, Anda tidak memiliki item Healing saat ini");
							System.out.println("------------------------------------");
							System.out.println("Tebak kata: "+shuffled);
							System.out.print("Jawab: ");
						} else {
							skor += 20;
							help[1] = help[1]-1;
							System.out.println("Skor Anda bertambah 20 poin. Skor Anda saat ini: "+this.skor);
							System.out.println("------------------------------------");
							System.out.println("Tebak kata: "+shuffled);
							System.out.print("Jawab: ");
						}
					} else if (jawaban.equalsIgnoreCase("-cheat-")) {
						if (help[0] == 0) {
							System.out.println("Sayang sekali, Anda tidak memiliki item cheat saat ini");
							System.out.println("------------------------------------");
							System.out.println("Tebak kata: "+shuffled);
							System.out.print("Jawab: ");
						} else if(!mayCheat) {
							System.out.println("Sayang sekali, Cheat hanya bisa digunakan sekali untuk satu kata");
							System.out.println("------------------------------------");
							System.out.println("Tebak kata: "+shuffled);
							System.out.print("Jawab: ");
						} else {
							mayCheat = false;
							help[0] = help[0]-1;
							int i = rand.nextInt(kata.length());
							int j = 0;
							while (j == i) {
								j = rand.nextInt(kata.length());
							}
							
							char c1 = kata.charAt(i);
							char c2 = kata.charAt(j);
							String view = "";
							for (int x = 0; x < kata.length(); x++) {
								char c = kata.charAt(x);
								if (c == c1 || c == c2) {
									view += c;
								} else {
									view += "_";
								}
							}
							System.out.println("cheat: "+view);
							System.out.print("Jawab: ");
						}
					} else {
						//Pemain menebak suatu kata
						if (jawaban.equalsIgnoreCase(kata)) {
							//jika jawaban benar
							berhentiJawab = true;
							this.successInARow++;
							this.skor += 10;
							if (successInARow == 3) {
								int addHelp = rand.nextInt(2);
								this.help[addHelp]++;
								successInARow = 0;
							}
							System.out.println("BENAR! Skor Anda: "+this.skor);
						} else {
							//Jika jawaban salah, diminta menebak lagi
							this.skor -= 3;
							System.out.println("SALAH! Penalti 3 poin. Skor Anda: "+this.skor+". Silakan coba lagi");
							this.successInARow = 0;
							System.out.print("Jawab: ");
						}
					}
				}	
			}
			System.out.println("======***======");
		}
		System.out.println("Skor Akhir Anda: "+this.skor);
		System.out.println("Terima kasih telah memainkan game Acak Kata!");
	}
	
	public String shuffle(String kata) {
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