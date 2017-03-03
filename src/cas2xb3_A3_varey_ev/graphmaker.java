package cas2xb3_A3_varey_ev;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Formatter;
import java.util.Stack;

public class graphmaker {
	static ArrayList<graph> graphz;
	public static String tempDouble;
	
	
	//calls shortest path, creates graphs, reads input, and prints to output file
	public static void main(String args[]) throws IOException{
		 String input[] =readdata("data/a3_in.txt");
		 //splitting up the two cities into two separate strings that will be used to call dfs and bfs
		 String firstlocation =input[0];
		 String secondlocation = input[1];
		 
		//calling connectedcities data to begin creating the graph
		 //method with array list that contains cities, each city has an array list with all connected cities
		 //check to see if city is already there when making array list
		String[] cities=readdata("2XB3_A3_DataSets/connectedCities.txt");
		makegraph();
		graph[] cat= graphthing();
		ArrayList<String> test = new ArrayList<String>();
		test= DFS.performBFS(cat, 2, 5);
		double[] kk=graph.meal();
		ArrayList<Edge> weightededgelist= new ArrayList<Edge>();
		PrintWriter writer = new PrintWriter("data/a3_out.txt", "UTF-8");
		FileReader read = new FileReader("2XB3_A3_DataSets/connectedCities.txt");
		BufferedReader bufferread = new BufferedReader(read);
		String lineread1 = bufferread.readLine();
					while (lineread1 != null) {
						String[] temp = lineread1.split("\\),");
						for (int i = 0; i < temp.length; i++) {
							int index = temp[i].indexOf("(");
							temp[i] = temp[i].substring(0, index).trim();
						}
						int index1 = DFS.getIndexOfAdjacent(temp[0], cat);
						int index2 = DFS.getIndexOfAdjacent(temp[1], cat);
						double weight = graph.edgelength(cat[index1],cat[index2]);
						Edge edge = new Edge(index1, index2, weight);
						weightededgelist.add(edge);
						lineread1 = bufferread.readLine();
					}
					System.out.println(weightededgelist.size());
				EdgeWeightedGraph g = new EdgeWeightedGraph(cat.length);
				for(int m = 0; m < weightededgelist.size(); m++){
					g.addEdge(weightededgelist.get(m));
				}
				DijkstraUndirectedSP sp = new DijkstraUndirectedSP(g, DFS.getIndexOfAdjacent(firstlocation, cat));
				Stack<Edge> stack = (Stack<Edge>) sp.pathTo(DFS.getIndexOfAdjacent(secondlocation, cat));
				double total = sp.distTo(DFS.getIndexOfAdjacent(secondlocation, cat));
				System.out.println("totalPrice = " + total);
				System.out.println(stack.size());
				System.out.println(firstlocation);
				ArrayList<String> paths = new ArrayList<String>();
				paths.add(firstlocation);
				for (int n = stack.size()-1; n > -1; n--){
					if(!paths.contains(cat[stack.get(n).either()].getcity())){
						paths.add(cat[stack.get(n).either()].getcity());
					}
					else{
						paths.add(cat[stack.get(n).that()].getcity());
					}
					System.out.println(cat[stack.get(n).either()].getcity());
				}
				for(int i=0;i<stack.size();i++){
					String accum= cat[stack.get(i).either()].getcity();
				}
				int save=0;
				int save2=0;
				for(int i=0;i<cat.length;i++){
					if(cat[i].getcity().equals(firstlocation)){
						save=i;
					}
					else if(cat[i].getcity().equals(secondlocation)){
						save2=i;
					}
				}
				output(paths);
				writer.println(DFS.performDFS(cat, save, save2));
				writer.println(DFS.performBFS(cat,save,save2));
				writer.close();
		//create each node, find each connecting point, find edge length,  
	}
		
		
	public static String[] mealname() throws IOException{
		String[] food=graphmaker.readdata("2XB3_A3_DataSets/menu.csv");
		String[] name = new String[food.length];
		double[] price= new double[food.length];
		int count=0;
		for(int i=1;i<food.length;i++){
			String foodline = food[i];
			String[] foodcol=foodline.split(",");
			name[i-1] = foodcol[1];
			String temp = foodcol[2].substring(1);
			price[i] = Double.parseDouble(temp);
		}
		//using insertion sort to order food items by item cost
		for(int i=0;i<price.length;i++){
			for(int j=i+1;j<price.length;j++){
				if((price[i])>(price[j])){
					double temp=0;
					String temp1="";
					temp1=name[i];
					temp=price[i];
					price[i]=price[j];
					name[i]=name[j];
					name[j] = temp1;
					price[j] = temp;
				}
			}
		}
		return name;
	}
	public static double[] meal() throws IOException{
		String[] food=graphmaker.readdata("2XB3_A3_DataSets/menu.csv");
		String[] name = new String[food.length];
		double[] price= new double[food.length];
		for(int i=1;i<food.length;i++){
			String foodline = food[i];
			String[] foodcol=foodline.split(",");
			name[i] = foodcol[1];
			String temp = foodcol[2].substring(1);
			price[i] = Double.parseDouble(temp);
		}
		//using insertion sort to order food items by item cost
		for(int i=0;i<price.length;i++){
			for(int j=i+1;j<price.length;j++){
				if((price[i])>(price[j])){
					double temp=0;
					String temp1="";
					temp1=name[i];
					temp=price[i];
					price[i]=price[j];
					name[i]=name[j];
					name[j] = temp1;
					price[j] = temp;
				}
			}
		}
		return price;
	}
	
	//creates the table
	public static void output(ArrayList<String> path) throws IOException{
		ArrayList<String> table = new ArrayList<String>();
		int a; int b; int c; int d;
		int longeststring1 = 0; int longeststring2 = 0; int longeststring3 = 0; int longeststring4 = 0;
		char[] spaces1; char[] spaces2; char[] spaces3; char[] spaces4;
		char[] s1; char[] s2; char[] s3; char[] s4;
		a = "Meal".length();
		b = "Meal Choice".length();
		c = "Cost Of Meal".length();
		d = "Cost of Fuel".length();
		
		//find the longest length to compare rest of sizes to
		int length = 0;
		for(int i = 0; i < path.size(); i++){
			String tempstring1 = path.get(i);
			if(tempstring1.length()>longeststring1){
				longeststring1 = tempstring1.length();
				length =longeststring1;
			}
		}
		double[] food = meal();
		String[] name= mealname();
		//getting longest meal name for comparisions in size
		for(int i = 1; i < path.size()-1; i++){
			String tempstring2 = name[i];
			if(tempstring2.length()>longeststring2){
				longeststring2 = tempstring2.length();
			}
		}
		//long meal price
		for(int i = 0; i < path.size()-1; i ++){
			String tempstring3 = Double.toString(food[i]);
			if(tempstring3.length()>longeststring3){
				longeststring3 = tempstring3.length();
			}
		}
		//longest fuel length
		String[] gasprices = new String[path.size()-1];
		for(int i = 0; i < gasprices.length; i++){
			graph one = new graph(path.get(i));
			graph two = new graph(path.get(i+1));
			double priceBetweenCities = graph.edgelength(one,two);
			int index = Double.toString(priceBetweenCities).indexOf(".");
			String tempString4 = Double.toString(priceBetweenCities).substring(0, index+3);
			System.out.println(tempString4);
			gasprices[i] = tempString4;
			if(tempString4.length()>longeststring4){
				longeststring4 = tempString4.length();
			}
		}
		if(a<longeststring1){ 
			spaces1 = new char[Math.abs(a - longeststring1)+3];
			Arrays.fill(spaces1, ' ');
		}
		else{
			spaces1 = new char[3]; 
			Arrays.fill(spaces1, ' ');
		}
		
	    if (b<longeststring2){
	    	spaces2 = new char[Math.abs(b - longeststring2)+3];
	    	Arrays.fill(spaces2, ' ');
	    }
	    
	    else{
	    	spaces2 = new char[3];
	    	Arrays.fill(spaces2, ' ');
	    }
		
	    if(c<longeststring3){
	    	spaces3 = new char[Math.abs(c - longeststring3)+3];
	    	Arrays.fill(spaces3, ' ');
	    }
	    else{
	    	spaces3 = new char[3];
	    	Arrays.fill(spaces3, ' ');
	    } 
		
		if(d<longeststring4){
			spaces4 = new char[Math.abs(d - longeststring4)+3];
			Arrays.fill(spaces4, ' ');
		}
		else{
			spaces4 = new char[3];
			Arrays.fill(spaces4, ' ');
		}
		
	
		// Find spaces for first city
		char[] firstCitySpace;
		if(path.get(0).length()<longeststring1){
			firstCitySpace = new char[Math.abs(path.get(0).length() - longeststring1)+3];
			Arrays.fill(firstCitySpace, ' ');
		}
		else{
			firstCitySpace = new char[3];
			Arrays.fill(firstCitySpace, ' ');
		}
		
		char[] firstMealChoiceSpace = new char[Math.abs("-".length() - longeststring2)+3];
		Arrays.fill(firstMealChoiceSpace, ' ');
		
		char[] firstCostOfMealSpace;
		if("Cost of Meal".length() < longeststring3){
			firstCostOfMealSpace = new char[Math.abs("-".length() - longeststring3)+3];
			Arrays.fill(firstCostOfMealSpace, ' ');
		}
		else{
			firstCostOfMealSpace = new char[Math.abs("-".length() - "Cost of Meal".length())+3];
			Arrays.fill(firstCostOfMealSpace, ' ');
		}
		
		char[] firstCostOfFuelSpace;
		if("Cost of Meal".length() < longeststring4){
			firstCostOfFuelSpace = new char[Math.abs("-".length() - longeststring4)+3];
			Arrays.fill(firstCostOfFuelSpace, ' ');
		}
		else{
			firstCostOfFuelSpace = new char[Math.abs("-".length() - "Cost of Fuel".length())+3];
			Arrays.fill(firstCostOfFuelSpace, ' ');
		}
		
		System.out.println();
		
		System.out.println("City"+new String(spaces1)+"Meal Choice"+new String(spaces2)+"Cost of Meal"+new String(spaces3)+"Cost of Fuel"+new String(spaces4)+"Total");
		
		table.add("City"+new String(spaces1)+"Meal Choice"+new String(spaces2)+"Cost of Meal"+new String(spaces3)+"Cost of Fuel"+new String(spaces4)+"Total");
		table.add("");
		System.out.println();
		System.out.println(path.get(0)+new String(firstCitySpace)+"-"+new String(firstMealChoiceSpace)+"-"+new String(firstCostOfMealSpace)+"-"+new String(firstCostOfFuelSpace)+"-");
		table.add(path.get(0)+new String(firstCitySpace)+"-"+new String(firstMealChoiceSpace)+"-"+new String(firstCostOfMealSpace)+"-"+new String(firstCostOfFuelSpace)+"-");
		double finalTotal = 0;
		
		
		for (int i=0; i< path.size()-1; i++){ 
			if (a>path.get(i+1).length()&&a>longeststring1){
				s1 = new char[3+a-path.get(i+1).length()];
				Arrays.fill(s1, ' ');
			}
			else if (a>path.get(i+1).length()){
				s1 = new char[Math.abs(a - longeststring1)+3+a-path.get(i+1).length()];
				Arrays.fill(s1, ' ');
			}
			else{
				s1 = new char[longeststring1-path.get(i+1).length()+3];
				Arrays.fill(s1, ' ');
			}
			
			if (b>name[i].length()&&b>longeststring2){
				s2 = new char[3+b-name[i].length()];
				Arrays.fill(s2, ' ');
			}
			else if (b>name[i].length()){
				s2 = new char[Math.abs(b - longeststring2)+3+b-name[i].length()];
				Arrays.fill(s2, ' '); 
			}
			else{ 
				s2 = new char[longeststring2-name[i].length()+3];
				Arrays.fill(s2, ' '); 
			}
			
			if(c>Double.toString(food[i]).length()&&c>longeststring3){
				s3 = new char[2+c-Double.toString(food[i]).length()]; 
				Arrays.fill(s3, ' ');
			}
			else if(c>Double.toString(food[i]).length()){
				s3 = new char[Math.abs(c - longeststring3)+2+c-Double.toString(food[i]).length()];
				Arrays.fill(s3, ' '); 
			} 
			else{
				s3 = new char[longeststring3-Double.toString(food[i]).length()+2];
				Arrays.fill(s3, ' ');
			} 
			
			if(d>gasprices[i].length()&&d>longeststring4){ 
				s4 = new char[2+d-gasprices[i].length()];
				Arrays.fill(s4, ' ');
			} 
			else if(d>gasprices[i].length()){ 
				s4 = new char[Math.abs(d - longeststring4)+2+d-gasprices[i].length()];
				Arrays.fill(s4, ' ');
			} 
			else{ 
				s4 = new char[longeststring4-gasprices[i].length()+2];
				Arrays.fill(s4, ' ');
			}
			
			
			
			finalTotal += food[i] + Double.parseDouble(gasprices[i]);
			
			
			tempDouble = Double.toString(finalTotal);
			int index9 = tempDouble.indexOf(".");
			if(tempDouble.length()>6){
				tempDouble = tempDouble.substring(0,index9+3);
			}
			
			
			
			System.out.println(path.get(i+1)+new String(s1)+name[i]+new String(s2)+"$"+food[i]+new String(s3)+"$"+gasprices[i]+new String(s4)+"$"+tempDouble);
			table.add(path.get(i+1)+new String(s1)+name[i]+new String(s2)+"$"+food[i]+new String(s3)+"$"+gasprices[i]+new String(s4)+"$"+tempDouble);
		} 	

		
		double mealcost = 0;
		double totalfuel = 0;
		
		for(int i = 0; i < path.size()-1; i++){
			
			mealcost += food[i];
			totalfuel += Double.parseDouble(gasprices[i]);
		}
		String meal;
		int index1 = Double.toString(mealcost).indexOf(".");
		
		if(Double.toString(mealcost).length()<5 && Double.toString(mealcost).substring(0, index1).length() > 1){
			
			meal = "$" + Double.toString(mealcost).substring(0, index1+2);
		}
		else{
			meal = "$" + Double.toString(mealcost).substring(0, index1+3);
		}
		
		String fuel;
		String totalfuelcost=Double.toString(totalfuel);
		int index2 = totalfuelcost.indexOf(".");
		
		if(totalfuelcost.length()<5 && totalfuelcost.substring(0, index1).length() > 1){
			fuel = "$" + totalfuelcost.substring(0, index2+2);
		}
		else{
			fuel = "$" + totalfuelcost.substring(0, index2+3);
		}
	    char[] newSpace = new char[Math.abs("Cost of Meal".length() - meal.length())+3];
	    Arrays.fill(newSpace, ' ');
	   
	    char[] newSpace1 = new char[Math.abs("Cost of Fuel".length() - fuel.length())+3];
	    Arrays.fill(newSpace1, ' ');
		
		System.out.println("    "+new String(spaces1)+"           "+new String(spaces2)+"------"+"         "+"--------"+"       "+"--------");
		System.out.println("    "+new String(spaces1)+"           "+new String(spaces2)+meal + new String(newSpace) + fuel +new String(newSpace1) + "$"+finalTotal);
		
		table.add("    "+new String(spaces1)+"           "+new String(spaces2)+"------"+"         "+"--------"+"       "+"--------");
		table.add("    "+new String(spaces1)+"           "+new String(spaces2)+meal + new String(newSpace) + fuel +new String(newSpace1) + "$"+tempDouble);
		
		a3output(table);
}
//printing the table to file
public static void a3output(ArrayList<String> list) throws IOException{
	
	
		PrintWriter out = new PrintWriter(new FileWriter("data/a3_out.txt", true));
		out.write("\n");
		out.write("\n");
		for (int i = 0; i < list.size(); i++){
			out.write(list.get(i));
			out.write("\n");
		}
		out.close();
	}
	
	
	//gets a graph array of all nodes in the graph
	public static graph[] graphthing() throws IOException{
		ArrayList<graph> thing = new ArrayList<graph>();
		thing = makegraph();
		graph[] array = new graph[thing.size()];
		for(int i=0;i<thing.size();i++){
			graph location = thing.get(i);
			array[i] = location;
		}
		return array;	
		
	}
	
	//makes an array list of all the values in graph
	public static ArrayList<graph> makegraph() throws IOException{
		String[] cities=readdata("2XB3_A3_DataSets/connectedCities.txt");
		
		graphz = new ArrayList<graph>();
		
		for(int i=0;i<cities.length;i++){
			boolean add = false;
			String location = cities[i];
			int bracket=location.indexOf("(");
			String city=location.substring(0,bracket-1);
			graph one = new graph(city);
			if(graphz.size()==0){
				graphz.add(one);
			}
			for(int j=0;j<graphz.size();j++){
				String thing = graphz.get(j).getcity();
				if((thing.equals(city))){
					add=true;
				}
			}
			if(!add){
				graphz.add(one);
			}
			//this is thing
		}
		graph miami = new graph("Miami");
		graphz.add(miami);
		graph mini = new graph("Minneapolis");
		graphz.add(mini);
		graph san = new graph ("San Antonio");
		graphz.add(san);
		return graphz;
		
	}
	//uses the occurance array to return an array of the city names of the connected cities
	public static String[] connectedcities(int[] occurance,String[] cityarray,String cityname){
		String connected="";
		String[] returnarray=new String[occurance.length];
		int count=0;
		for(int i=0;i<occurance.length;i++){
			int line=occurance[i];
			String location = cityarray[line];
			int bracket=location.indexOf(")");
			String city=location.substring(0,bracket+1);
			String second=location.substring(bracket+3);
			if(city.equals(cityname)){
				connected=second;
				returnarray[count] = connected;
				count++;
			}
			else if(second.equals(cityname)){
				connected=city;
				returnarray[count] = connected;
				count++;
			}
			
			
		}
		return returnarray;
	}
	//method to read files and save to a string
	public static String[] readdata(String a) throws IOException{
		FileReader fr = new FileReader(a);
		BufferedReader br = new BufferedReader(fr);
		String line = br.readLine();
		int count =0;
		 
		 while (line != null) {
			 line=br.readLine();
			 count++;
			 
		 }
		br.close();
		//each line will be an array index
		String[] input = new String[count];
		FileReader file = new FileReader(a);
		BufferedReader two = new BufferedReader(file);
		int linenumber=0;
		String lines= two.readLine();
		
		while(lines != null){
			
			if(lines!=null){
				input[linenumber]=lines;
				linenumber++;
				lines = two.readLine();
			}
			
		}
		two.close();
		return input;
	}
	
	//method that will count how many times a city occurs in the array
	public static int arraysearch(String[] cityarray, String cityname){
		int count =0;
		//loops through the array to return an integer array with all the indexes the city name occurs at
		for(int i=0;i<cityarray.length;i++){
			String location = cityarray[i];
			int bracket=location.indexOf("(");
			int bracket1=location.indexOf(")");
			String city=location.substring(0,bracket-1);
			String second=location.substring(bracket1+3);
			int bracket2 = second.indexOf("(");
			String second1=second.substring(0,bracket2-1);
			if(city.equals(cityname)){
				count++;
			}
			else if(second1.equals(cityname)){
				count++;
			}

		}
		return count;
	}
	
	//method that uses the above method to create an integer array that saves the indexes that each city
	//it occurs at
	public static String[] getname(String[] cities){
		String[] returns = new String[cities.length];
		for(int i=0;i<cities.length;i++){
			String location = cities[i];
			int bracket=location.indexOf(")");
			int endbracket=location.indexOf("(");
			String city = location.substring(0,endbracket-1);
			returns[i] = city;
		}
		return returns;
	}
	
	//return and array of all the indexes a city occurs at
	public static int[] arrayreturn(String[] cityarray, String cityname, int count){
		int[] hits = new int[count];
		int index=0;
		for(int i=0;i<cityarray.length;i++){
			String location = cityarray[i];
			int bracket=location.indexOf(")");
			int endbracket=location.indexOf("(");
			String city=location.substring(0,bracket+1);
			String second=location.substring(bracket+3);
			if(city.equals(cityname)){
				hits[index]=i;
				index++;
			}
			else if(second.equals(cityname)){
				hits[index]=i;
				index++;
			}
			
		}
		return hits;
	}

}
