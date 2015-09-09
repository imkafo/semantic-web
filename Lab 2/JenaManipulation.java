/*
 * Lab 2 - 2.2 Create and Manipulate RDF Graphs
 */

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.util.FileManager;
import org.apache.jena.vocabulary.VCARD;

public class JenaManipulation {
    public static void main(String[] args) {
		// definitions for adding RDF description 
		String friendURI = "http://example.org/foo-bar";
        String nickName = "fb";

		// create an empty model
		Model model = ModelFactory.createDefaultModel();
		
		// use the FileManager to find the input file	
		String inputFileName = "src/main/resources/lab-1.ttl";
        InputStream in = FileManager.get().open(inputFileName);
        if (in == null) {
            throw new IllegalArgumentException(
                    "File: " + inputFileName + " not found");
        }

		// read the Turtle file
        model.read(in, null, "TTL");

		// get the resouce describing the friend
		Resource fooBar = model.getResource(friendURI);

		// add nickname description
        fooBar.addProperty(VCARD.NICKNAME, nickName);

		// serialize as an XML file
        try {
            FileWriter outXML = new FileWriter("src/main/resources/out.xml");
            model.write(outXML, "RDF/XML");
            outXML.close();
        }
        catch (IOException ioe){
            System.out.println("Error: " + ioe);
        }

		// serialize as a Turtle file
        try {
            FileWriter outTTL = new FileWriter("src/main/resources/out.ttl");
            model.write(outTTL, "TURTLE");
            outTTL.close();
        }
        catch (IOException ioe){
            System.out.println("Error: " + ioe);
        }
		
    }
}
