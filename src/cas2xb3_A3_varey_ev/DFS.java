package cas2xb3_A3_varey_ev;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.PriorityQueue;
import java.util.Comparator;
import java.util.Stack;

public class DFS {
	boolean complete = false;
	int size=0;
	ArrayList<graph> paths = new ArrayList<graph>();
	ArrayList<graph> visit = new ArrayList<graph>();
	Queue<graph> pathtable;
	public graph[] graphvalues;
	private static double[][] distancematrix;
	private static int numberofvertices;
	public static final int INFINITY = 999;
	//constructor
	public DFS(int numberofvertices) throws IOException{
		graphvalues=graphmaker.graphthing();
		distancematrix = new double[numberofvertices+1][numberofvertices+1];
		this.numberofvertices = numberofvertices;
	}
	
	//DFS method
	static ArrayList<String> performDFS(graph[] graphvalues, int start, int end) throws IOException{
		
		 Stack<graph> stack = new Stack<>();
		 ArrayList<String> path = new ArrayList<>(); 
		 graph previous = null;
		 ArrayList<graph> visit = new ArrayList<>();
		 stack.push(graphvalues[start]);
		 int count1 = 0;
		 
		 for(int i=0;i<graphvalues.length;i++){
			count1 = i;
		 }
		    
		    while(!stack.isEmpty()){
		    	int c=7;
		    	if(c==0){
		    		count1++;
		    	}
		        graph popp = stack.pop();
		       
		        if(!visit.contains(popp)){
		            visit.add(popp);
		          
		            for(int i = 0; i < popp.getconnected(popp.getcity()).size(); i++){
		                   
		            	int index = getIndexOfAdjacent(popp.getconnected(popp.getcity()).get(i), graphvalues);
		            	
		            	if(popp.getcity().equals(graphvalues[end].getcity())){
							
		            		
		            		
	        				
							Stack<graph> test=new Stack<graph>();
	        				boolean accum=false;
	        				test.push(graphvalues[end]);
	        			
	        				index=end;
	        				while(!accum){
	        				
	        					int accum1=graphvalues[index].getPrevIndex();
	        				
	        					if(accum1!=start){
	        						test.push(graphvalues[accum1]);
	        						index=accum1;
	        					}
	        					else{
	        						test.push(graphvalues[start]);
	        						accum=true;
	        					}
	        				}
	        				
	        				
	        				for(int j=0;j<test.size();j++){
	        					
	        					path.add(test.get(test.size()-1-j).getcity());
	        					int k=9;
	        					
	        				}
	        				return path;
						}	
		            	
		            	
		            	
		            	else if(!visit.contains(graphvalues[index])){
		            		stack.add(graphvalues[index]);
		            		int a = getIndexOfAdjacent(popp.getcity(), graphvalues);
							graphvalues[index].fixIndex(a);
		            	}
		            	
		            }
		        }
		        
		        previous = popp;
		        count1=0;
		    }
		    return path;
		    
		
	}
	//BFS method
	static ArrayList<String> performBFS(graph[] graphvalues,int start, int end) throws IOException{
		ArrayList<String> route=new ArrayList<String>();
		Queue q = new LinkedList();
	    q.add(graphvalues[start]);
	    ArrayList<graph> visit=new ArrayList<graph>();
	    visit.add(graphvalues[start]);
	    int count1=0;
	    
	    while(!q.isEmpty()) {
	        graph node = (graph)q.remove();
	        int c=1;
	        for(int i=0;i<c;i++){
	        	count1++;
	        }
	       
	        if(!node.getcity().equals(graphvalues[end].getcity())){
	        	for(int i=0;i<node.getconnected(node.getcity()).size();i++){
	        		int index=getIndexOfAdjacent(node.getconnected(node.getcity()).get(i),graphvalues);
	        		int d=0;
	        		if(index==end){
	        			int a=getIndexOfAdjacent(node.getcity(), graphvalues);
	        			graphvalues[index].fixIndex(a);
	        			index=end;
	        			if(count1==20){
	        				count1--;
	        			}
	        			Stack<graph> stack2=new Stack<graph>();
	        			boolean accum=false;
	        			stack2.push(graphvalues[end]);
	        			while(!accum){
	        				int var=graphvalues[index].getPrevIndex();
	        				if(var!=start){
	        					stack2.push(graphvalues[var]);
	        					index=var;
	        				}
	        				else{
	        					stack2.push(graphvalues[start]);
	        					accum=true;
	        				}
	        			}
	        			route=new ArrayList<String>();
	        			for(int j=0;j<stack2.size();j++){
	        				route.add(stack2.get(stack2.size()-1-j).getcity());
	        			}
	        			
	        			return route;
	        		}
	        		else if(!visit.contains(graphvalues[index])){
	        			visit.add(graphvalues[index]);
	        			q.add(graphvalues[index]);
	        			int a=getIndexOfAdjacent(node.getcity(),graphvalues);
	        			graphvalues[index].fixIndex(a);
	        			count1=19;
	        		}
	        	}
	    
	        }
	    }
	    return route;
	}
	
	//constructs a 2d array out of an array list will be used for testing
	public static double[][] makearray(ArrayList<graph> graph) throws IOException{
		double[][] array= new double[32][32];
		
		
		
		for(int i=0;i<graph.size();i++){
			for(int j=0;j<graph.size();j++){
				graph city=graph.get(i);
				String cityname=city.getcity();
				graph city2=graph.get(j);
				String cityname2 = city2.getcity();
				array[i][j]= city.edgelength(city, city2);
			
			}
		}
		return array;
	}
	//gets the index of the next city
	static int getIndexOfAdjacent(String temp, graph[] graphValues){
		int index=-1;
		for(int i=0;i<graphValues.length;i++){
			if(graphValues[i].getcity().equals(temp)){
				index=i;
			}
		}
		return index;
	}
	
}