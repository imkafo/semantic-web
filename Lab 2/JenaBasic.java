/*
 * Lab 2 - 2.1 Set Up Jena in a Java IDE
 */

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.util.FileManager;

public class JenaBasic {
    public static void main(String[] args) {
		// create an empty model
		Model model = ModelFactory.createDefaultModel();

		// use the FileManager to find the input file
		String inputFileName = "src/main/resources/data.ttl";
        InputStream in = FileManager.get().open(inputFileName);
        if (in == null) {
            throw new IllegalArgumentException(
                    "File: " + inputFileName + " not found");
        }

		// read the Turtle file
        model.read(in, null, "TTL");
    }
}

