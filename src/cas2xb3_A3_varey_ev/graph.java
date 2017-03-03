package cas2xb3_A3_varey_ev;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class graph {
	
	public static ArrayList<String> nodes = new ArrayList();
	public String city;
	public double latitude;
	public double longitude;
	public String[] whale;
	public String[] actualStates;
	public String[] name;
	static boolean mcdonalds;
	static boolean wendys;
	static boolean burgerking;
	public int prevIndex;
	
	//constructor
	public graph(String city) throws IOException{
		this.longitude=0;
		this.latitude=0;
		this.city=city;
		//this.array=graphmaker.readdata("2XB3_A3_DataSets/connectedCities.txt");
		ArrayList<String> thing = getstates(this.city);
		this.actualStates= new String[thing.size()]; 
		for(int i=0;i<thing.size();i++){
			this.actualStates[i]=thing.get(i);
		}
		getlonglat(this.city,this.actualStates);
		this.prevIndex=-1;
	}
	
	//getter for the city name
	public String getcity(){
		//int bracket= this.city.indexOf('(');
		//this.city = this.city.substring(0,bracket-1);
		return this.city;
	}
	
	//modifies to get the previous index
	public void fixIndex(int a){
		this.prevIndex=a;
	}
	
	//returns the previoud index
	public int getPrevIndex(){
		return this.prevIndex;
	}
	
	//2d array of city name and its associated edge value
	public static String[][] nodeedge(String city, graph city1) throws IOException{
		ArrayList<String> cities= getconnected(city);
		double[] edges= new double[cities.size()];
		String[][] nodeedge = new String[cities.size()][cities.size()];
		for(int i=0;i<cities.size();i++){
			graph g= new graph((String) cities.get(i));
			edges[i] = edgelength(city1,g);
		}
		for(int i=0; i<cities.size();i++){
			for(int j=0;j<edges.length;j++){
				nodeedge[i][j] = cities.get(i)+","+(Double.toString(edges[j]));
			}
		}
		return nodeedge;
	}
	
	//creates an array list of all the cities that are connected to the given city
	public static ArrayList<String> getconnected(String city) throws IOException{
		String[] cities=graphmaker.readdata("2XB3_A3_DataSets/connectedCities.txt");
		int dog;
		dog=graphmaker.arraysearch(cities,city);
		int[] occurance = new int[dog];
		occurance=arrayreturn(cities,city,dog);
		String[] whale =  new String[occurance.length];
		whale=connectedcities(occurance,cities,city);
		ArrayList<String> thing = new ArrayList<String>();
		for(int i=0;i<whale.length;i++){
			thing.add(whale[i]);
		}
		return thing;
	}
	
	
	//method that reads the file StateGasPrice and returns the avg gas prices for all the given
	//states the city is in
	public static double gasprice(String[] actualStates) throws IOException{
		String[] array= graphmaker.readdata("2XB3_A3_DataSets/StateGasPrice.csv");
		int price1=0;
		int price2=0;
		int price3=0;
		int price4=0;
		String state1=actualStates[0];
		String state2="";
		String state3="";
		String state4="";
		//this may look lengthy and weird but basically its reading the array actual states which contains 
		//the states the city is considered to be in and saving these to strings but because this 
		//has an unknown length if statements allow the assigning to occur without error
		if(actualStates.length==2){
			state2=actualStates[1];
		}
		if(actualStates.length==3){
			state2=actualStates[1];
			state3=actualStates[2];
		}
		if(actualStates.length==4){
			state2=actualStates[1];
			state3=actualStates[2];
			state4=actualStates[3];
		}
		for(int i=0;i<array.length;i++){
			String content=array[i];
			int comma=content.indexOf(',');
			String state=content.substring(0,comma);
			if(state.equals(state1)){
				String price=content.substring(comma+2);
				price1=Integer.parseInt(price);
			}
			else if(state.equals(state2)){
				String price=content.substring(comma+2);
				price2=Integer.parseInt(price);
				
			}
			else if(state.equals(state3)){
				String price=content.substring(comma+2);
				price3=Integer.parseInt(price);
				
			}
			else if(state.equals(state4)){
				String price=content.substring(comma+2);
				price4=Integer.parseInt(price);
				
			}
		}
		double avgprice=price1+price2+price3+price4/4.0;
		return avgprice;
	}
	
	//returns t he edge length values between two nodes
	public static double edgelength(graph city1, graph city2) throws IOException{
		double gas=gasprice(city1.actualStates);
		double gas2 = gasprice(city2.actualStates);
		double dist=getdistance(city1,city2);
		double avg= (gas+gas2)/2.0;
		double fuelcost= (avg*8.2/100)*dist/100;
		return (avg*8.2/100)*dist/100;
	}
	
	//gets the food cost
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
	
	//returns the states a city is considered to be in
	public ArrayList<String> getstates(String city) throws IOException{
		String[] dog=graphmaker.readdata("2XB3_A3_DataSets/connectedCities.txt");
		ArrayList<String> state = new ArrayList<String>();
		int length=city.length();
		for(int i=0;i<dog.length; i++){
			String cat=dog[i];
			int comma=cat.indexOf(')');
			String var = cat.substring(0,comma+1);
			String varone = cat.substring(comma+3);
			int accumz= var.indexOf('(');
			String ik = var.substring(0,accumz-1);
			int accumzz= varone.indexOf('(');
			String ikz = varone.substring(0,accumzz-1);
			if(ik.equals(city)&&length==ik.length()){
			int accum= var.indexOf('(');
			int accum2= var.indexOf(')');
			String statesone=var.substring(accum+1,accum2);
			String[] states = statesone.split(",");
			for(int k=0;k<states.length;k++){
				state.add(states[k]);
			}
			}
			else if(ikz.equals(city)&&length==ikz.length()){
			int count= varone.indexOf('(');
			int count1= varone.indexOf(')');
			String states2=varone.substring(count+1,count1);
			String[] states = states2.split(",");
			for(int k=0;k<states.length;k++){
				state.add(states[k]);
			}
			}
		}
		return state;
	}
	//gets the city name
	public String cityname(){
		return this.city;
	}
	//grabs the latitude value
	public double latitude(){
		return this.latitude;
	}
	//grabs the longitude
	public double longitude(){
		return this.longitude;
	}
	
	//method that appends all cities connected to the city to an array list
	public static ArrayList<String> connectedcities(String[] array, String city){
		int dog=0;
		dog=graphmaker.arraysearch(array,city);
		System.out.println(dog+"yyyy");
		int[] occurance = new int[dog];
		occurance=graphmaker.arrayreturn(array,city,dog);
		String[] answer=graphmaker.getname(array);
		for(int i=0;i<occurance.length;i++){
			nodes.add(answer[occurance[i]]);
		}
		return nodes;
	}
	//returns the km conversion of lat
	public static double latconverter(double actuallat){
		double km=actuallat;
		return km;
	}
	//returns the km conversion of long
	public static double longconverter(double longitude){
		double km=Math.cos(longitude);
		return km;
		
	}
	//this method looks confusing but it just takes two cities and there latitudes and longitudes and find the distance between
	//then it uses average gas price between the two cities and multiples by the km value of this distance
	//and gets what the graph edge value will be
	public void getlonglat(String cityone,String[] actualStates) throws IOException{
		 String actualCity1=cityone.toUpperCase();
		  try {
		   FileReader Fr= new FileReader("2XB3_A3_DataSets/zips1990.csv");
		   BufferedReader Br=new BufferedReader(Fr);
		   boolean i=true;
		   
		   while(i){
		    String test=Br.readLine();
		    
		    if(test==null){
		     i=false;
		    }
		    else{
		     String state=test.substring(9,11);
		     String rest=test.substring(12);
		     int comma=rest.indexOf(",");
		     String city=rest.substring(0,comma);
		     for(int j=0;j<actualStates.length;j++){
		      if(actualStates[j].equals(state) && city.contains(actualCity1)){
		       i=false;
		       String lastBit=rest.substring(comma+1);
		       int comma1=lastBit.indexOf(",");
		       longitude=-1*Double.parseDouble(lastBit.substring(0, comma1));
		       
		       String lastBit1=lastBit.substring(comma1+1);
		       int comma2=lastBit1.indexOf(",");
		       latitude=Double.parseDouble(lastBit1.substring(0, comma2));
		      
		      }
		      
		     }
		     
		    }
		   }
		 
		   Br.close();
		   
		  } catch (FileNotFoundException e) {
		   e.printStackTrace();
		  } catch (IOException e) {
		   e.printStackTrace();
		  }
	}
	
	//gets the distance in km between two cities
	public static double getdistance(graph cityone, graph citytwo){
		double earthRadius = 6373;
	    double dLat = Math.toRadians(citytwo.latitude()-cityone.latitude());
	    double dLng = Math.toRadians(citytwo.longitude()-cityone.longitude());
	    double a = Math.sin(dLat/2) * Math.sin(dLat/2) +Math.cos(Math.toRadians(cityone.latitude())) * Math.cos(Math.toRadians(citytwo.latitude())) *Math.sin(dLng/2) * Math.sin(dLng/2);
	    double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
	    double dist = earthRadius * c;

	    return dist;
		
		
	}
	
	//searchs the array for a city name
	public static int arraysearch(String[] cityarray, String cityname){
		int count =0;
		//loops through the array to return an integer array with all the indexes the city name occurs at
		for(int i=0;i<cityarray.length;i++){
			String location = cityarray[i];
			int bracket=location.indexOf(")");
			String city=location.substring(0,bracket+1);
			String second=location.substring(bracket+3);
			if(city.equals(cityname)){
				count++;
			}
			else if(second.equals(cityname)){
				count++;
			}

		}
		return count;
	}
	
	//method that uses the above method to create an integer array that saves the indexes that each city
	//it occurs at
	public static int[] arrayreturn(String[] cityarray, String cityname, int count){
		int[] hits = new int[count];
		int index=0;
		for(int i=0;i<cityarray.length;i++){
			String location = cityarray[i];
			int bracket=location.indexOf("(");
			int bracket1=location.indexOf(")");
			String city=location.substring(0,bracket-1);
			String second=location.substring(bracket1+3);
			int secondbracket=second.indexOf("(");
			second=second.substring(0,secondbracket-1);
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
	
	//gets all the cities connected to a given city and saves it to an array
	public static String[] connectedcities(int[] occurance,String[] cityarray,String cityname){
		String connected="";
		String[] returnarray=new String[occurance.length];
		int count=0;
		for(int i=0;i<occurance.length;i++){
			int line=occurance[i];
			String location = cityarray[line];
			int bracket=location.indexOf("(");
			int bracket1=location.indexOf(")");
			String city=location.substring(0,bracket-1);
			String second=location.substring(bracket1+3);
			int bracket2=second.indexOf("(");
			second=second.substring(0,bracket2-1);
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
	
	//checks to see if mcdonalds is within the alotted distance
	public static boolean ismcd(double longitude, double latitude) throws IOException{
		String[] cat = graphmaker.readdata("2XB3_A3_DataSets/mcdonalds.csv");
		for(int i=0; i<cat.length;i++){
			String line=cat[i];
			int comma=line.indexOf(',');
			int m=line.indexOf('M');
			String mcdlong= line.substring(0,comma);
			String mcdlat= line.substring(comma+1,m);
			double mcdlongitude= Math.abs(Double.parseDouble(mcdlong));
			double mcdlatitude = Math.abs(Double.parseDouble(mcdlat));
			if(Math.abs((longitude-mcdlongitude))<0.5){
				mcdonalds=true;
			}
			
		}
		
		return mcdonalds;
		
	}
	//checks to see if any wendy's are within the allotted distance
	public static boolean iswendy(double longitude, double latitude) throws IOException{
		String[] cat = graphmaker.readdata("2XB3_A3_DataSets/mcdonalds.csv");
		for(int i=0; i<cat.length;i++){
			String line=cat[i];
			int comma=line.indexOf(',');
			int m=line.indexOf('W');
			String mcdlong= line.substring(0,comma);
			String mcdlat= line.substring(comma+1,m);
			double mcdlongitude= Math.abs(Double.parseDouble(mcdlong));
			double mcdlatitude = Math.abs(Double.parseDouble(mcdlat));
			if(Math.abs((longitude-mcdlongitude))<0.5){
				wendys=true;
			}
			
		}
		
		return wendys;
		
	}
	
	//checks to see if any burgers kings are within the alotted distance.
	public static boolean isburger(double longitude, double latitude) throws IOException{
		String[] cat = graphmaker.readdata("2XB3_A3_DataSets/mcdonalds.csv");
		for(int i=0; i<cat.length;i++){
			String line=cat[i];
			int comma=line.indexOf(',');
			int m=line.indexOf('B');
			String mcdlong= line.substring(0,comma);
			String mcdlat= line.substring(comma+1,m);
			double mcdlongitude= Math.abs(Double.parseDouble(mcdlong));
			double mcdlatitude = Math.abs(Double.parseDouble(mcdlat));
			if(Math.abs((longitude-mcdlongitude))<0.5){
				burgerking=true;
			}
			
		}
		
		return burgerking;
	
	}

}
