import java.util.Arrays;
import java.util.Hashtable;
import java.util.HashSet;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Main {
	public static void main(String[] args) {
		File file = new File(args[0]);
		BufferedReader in = null;
		try {
			in = new BufferedReader(new FileReader(file));
		} catch (FileNotFoundException fnf) {
			fnf.printStackTrace();
		}
		String line;
		try {
			while ((line = in.readLine()) != null) {
				HashSet<String> fragments1 = new HashSet<String>(
						Arrays.asList(line.split(";")));
				while (fragments1.size() > 1) {
					Hashtable<String, String> tmp_answer = null;
					Boolean unset = true;
					String[] ary_fragments1 = fragments1
							.toArray((new String[fragments1.size()]));
					for (int i = 0; i < ary_fragments1.length; i++) {
						String f1 = ary_fragments1[i];
						for (int j = i; j < ary_fragments1.length; j++) {
							String f2 = ary_fragments1[j];
							if (f1 != null && f2 != null && !f1.equals(f2)) {
								Hashtable<String, String> ol = findMaxOl(f1, f2);
								if (unset
										|| ol.get("ol").length() > tmp_answer
												.get("ol").length()) {
									tmp_answer = ol;
									unset = false;
								}
							}
						}
					}
					System.out.println(tmp_answer.get("ol") + " ("
							+ tmp_answer.get("ol").length() + ")");
					if (tmp_answer.get("ol").length() == 0) {
						int f1_l = tmp_answer.get("f1").length();
						int f2_l = tmp_answer.get("f2").length();
						String short_s = f1_l < f2_l ? tmp_answer.get("f1")
								: tmp_answer.get("f2");
						fragments1.remove(short_s);
					} else {
						String f1_dep = tmp_answer.get("f1");
						String f2_dep = tmp_answer.get("f2");
						fragments1.remove(f1_dep);
						fragments1.remove(f2_dep);
						String fh = tmp_answer.get("f1");
						int ol_length = tmp_answer.get("ol").length();
						String lh = tmp_answer.get("f2").substring(ol_length);
						String join_frag = fh + lh;
						fragments1.add(join_frag);
					}
				}
				System.out.println(fragments1.toString().replace("[", "")
						.replace("]", ""));
			}
		} catch (IOException io) {
			io.printStackTrace();
		}
	}

	public static Hashtable<String, String> findMaxOl(String f1, String f2) {
		String short_f, long_f;
		short_f = f1.length() > f2.length() ? f2 : f1;
		long_f = f1.length() > f2.length() ? f1 : f2;

		Hashtable<String, String> frag_ol = new Hashtable<String, String>();

		String longFrontOl = findLongFrontOl(short_f, long_f);
		String shortFrontOl = findShortFrontOl(short_f, long_f);

		if (longFrontOl.length() > shortFrontOl.length()) {
			frag_ol.put("f1", long_f);
			frag_ol.put("f2", short_f);
			frag_ol.put("ol", longFrontOl);
		} else {
			frag_ol.put("f1", short_f);
			frag_ol.put("f2", long_f);
			frag_ol.put("ol", shortFrontOl);
		}

		return frag_ol;
	}

	public static String findLongFrontOl(String short_f, String long_f) {
		String ol = "";
		for (int i = 1; i < short_f.length() + 1; i++) {
			String short_head = short_f.substring(0, i);
			String long_tail = long_f.substring(long_f.length() - i,
					long_f.length());
			if (short_head.equals(long_tail)) {
				ol = short_f.substring(0, i);
			}
		}
		return ol;
	}

	public static String findShortFrontOl(String short_f, String long_f) {
		String ol = "";
		for (int i = 1; i < short_f.length() + 1; i++) {
			String short_tail = short_f.substring(short_f.length() - i,
					short_f.length());
			String long_head = long_f.substring(0, i);
			if (short_tail.equals(long_head)) {
				ol = long_f.substring(0, i);
			}
		}
		return ol;
	}
}
