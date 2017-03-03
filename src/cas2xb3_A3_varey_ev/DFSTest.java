package cas2xb3_A3_varey_ev;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;

import org.junit.Test;

public class DFSTest {


	@Test
	public void testPerformBFS() throws IOException {
		graph[] cat= graphmaker.graphthing();
		ArrayList<String> dog = new ArrayList<String>();
		dog= DFS.performBFS(cat, 1, 4);
		for(int i=0;i<dog.size()-1;i++){
			if(dog.get(i)==dog.get(i+1)){
				fail("they are not distinct");
			}
		}
		//fail("Not yet implemented");
	}
	@Test
	public void testpath() throws IOException{
		graph[] cat= graphmaker.graphthing();
		ArrayList<String> dog = new ArrayList<String>();
		dog= DFS.performBFS(cat, 1, 4);
		for(int i=0;i<dog.size()-1;i++){
			graph one = new graph(dog.get(i));
			graph two = new graph(dog.get(i+1));
			if(graph.edgelength(one, two)==0){
				fail("they are not distinct");
			}
		}
		
	}

}
