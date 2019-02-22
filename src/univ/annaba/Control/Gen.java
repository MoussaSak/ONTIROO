/**
 * 
 */
package univ.annaba.Control;

import java.io.File;

/**
 * @author Moussa
 *
 */

public class Gen {

	public static void main(String[] args) {
		
		File file = new File("/home/moise/Documents/ONTIROO/phase prétraitement/src/Mypackage/Java.g4");
		System.out.println("First Change at Gen"+ Gen.class.getClass().getSimpleName());
		String[] arg0 = { "-visitor", file.getPath(), "-package", "Mypackage" };
		org.antlr.v4.Tool.main(arg0);

	}

}