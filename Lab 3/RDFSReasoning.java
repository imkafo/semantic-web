/**
 * Lab 3
 */

import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.rdf.model.*;
import org.apache.jena.rdf.model.impl.StmtIteratorImpl;
import org.apache.jena.riot.RDFDataMgr;
import org.apache.jena.riot.RDFLanguages;
import org.apache.jena.util.FileManager;
import org.apache.jena.util.iterator.ExtendedIterator;
import org.apache.jena.util.iterator.Filter;

import java.io.*;


public class RDFSReasoning {
    public static void main(String[] args) {
        // create an empty model
        Model model = ModelFactory.createDefaultModel();

        // use the FileManager to find the input file
        String inputFileName = "src/main/resources/lab-3.ttl";
        InputStream in = FileManager.get().open(inputFileName);
        if (in == null) {
            throw new IllegalArgumentException(
                    "File: " + inputFileName + " not found");
        }

        // read the Turtle file
        model.read(in, null, "TTL");

        // get the inferred mode
		/* The getDeductionsFunction somehow returns the incorrect answer, so I have to use the deprecated class 'Filter'. */
        InfModel infModel = ModelFactory.createRDFSModel(model);
        ExtendedIterator<Statement> stmts = infModel.listStatements().filterDrop(new Filter<Statement>() {
            @Override
            public boolean accept(Statement o) {
                return model.contains(o);
            }
        });
        Model deductionsModel = ModelFactory.createDefaultModel().add(new StmtIteratorImpl(stmts));

        // list new RDFS-inferred triples about 'Museion'
        outputInfo(deductionsModel, "museion");

        // list new RDFS-inferred triples about 'Chicken Hut'
        outputInfo(deductionsModel, "chickenHut");
    }

    public static void outputInfo(Model model, String var){
		// compose the query
        String sparql =
                "BASE <http://example.org/inst/>" +
                        "CONSTRUCT { <" + var +"> ?P ?O } " +
                        "WHERE { <" + var +"> ?P ?O }";

		// carry out the query
        Query query = QueryFactory.create(sparql);
        QueryExecution qe = QueryExecutionFactory.create(query, model);
        Model results =  qe.execConstruct();

		// print inferred triples
        System.out.println();
        System.out.println(var + ":");
        RDFDataMgr.write(System.out, results, RDFLanguages.NT);
    }
}
