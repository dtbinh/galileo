package mapping;
import java.util.ArrayList;

//Ziad Nörpel:
//Map class which inherits from ArrayList
//this class is basically a two dimensional ArrayList, a ArrayList containing
//other ArrayLists
//it should store all the elements which build alltogether the map which the robot
//is exploring
public class Map extends ArrayList<ArrayList<MapObject>>{
	private int x=0;
	private int y=0;
	private int width=0;
	private int height=0;
	private MapObject empty = MapObject.EMPTY;
	private MapObject wall = MapObject.WALL;
	
//	private ArrayList<ArrayList<MapObject>> arrayList = new ArrayList<ArrayList<MapObject>>();
	
	//this function initializes empty inner Arrays(rows) of the map
	//depending on the given numberOfRows value
	public void initMap(int numberOfRows){
		for(int i=0;i<numberOfRows;i++){
			this.add(new ArrayList<MapObject>());
		}
		
	};

	//this function builds a map from a one dimensional ArrayList and the given mapWidth
	//the map width regulates how many elements can be placed in one row
	public void buildMapFromOneDimensionalArrayList(ArrayList<MapObject>mapObjects, int mapWidth){

		int row=0;
		int cumulatedRows=mapWidth;
		this.add(new ArrayList<MapObject>());
		for(int i=0; i<mapObjects.size();i++){
			
		    if(i<cumulatedRows){
		    	
		    	this.get(row).add(mapObjects.get(i));
		    	
		    }else{
		    	row+=1;
		    	this.add(new ArrayList<MapObject>());
		    	this.get(row).add(mapObjects.get(i));
		    	cumulatedRows+=mapWidth;
		    }
		}
	}
	//this function builds a map from a one dimensional Array and the given mapWidth
	//the map width regulates how many elements can be placed in one row
	public void buildMapFromOneDimensionalArray(MapObject[]mapObjects, int mapWidth){

		int row=0;
		int cumulatedRows=mapWidth;
		this.add(new ArrayList<MapObject>());
		for(int i=0; i<mapObjects.length;i++){
			
		    if(i<cumulatedRows){
		    	
		    	this.get(row).add(mapObjects[i]);
		    	
		    }else{
		    	row+=1;
		    	this.add(new ArrayList<MapObject>());
		    	this.get(row).add(mapObjects[i]);
		    	cumulatedRows+=mapWidth;
		    }
		}
	}
	
	public void updateMapFromVectors(TestVector vector){
		if(this.height==0){
			this.add(new ArrayList<MapObject>());
			this.height+=1;
		}
		//set height
		if(vector.y!=0 && this.height-1<vector.y+this.y){
			//the difference between the y movement and the height so we know
			//how much additional rows(height-y) we need
		int mapExpansion = (vector.y+this.y) -this.height; 
		for(int i=0;i<mapExpansion;i++){
			this.add(new ArrayList<MapObject>());
			
			for(int k=0;k<this.width;k++){
				this.get(this.size()-1).add(empty);
			}
			
			this.height+=1;
		
		}
		}
		
		//negative y
		if(vector.y <0 && (vector.y+this.y)<0){
			int mapShift=vector.y+this.y;
			for(int i=mapShift+1;i<0;i++){
				this.add(0,new ArrayList<MapObject>());
				this.height+=1;
				for(int j=0;j<this.width;j++){
					if(this.x==j){
						this.get(0).add(wall);
					}else{
					this.get(0).add(empty);
				}
				}
			}
			this.y=0;
			
		}
		
		//negative x
		if(vector.x <0){
			if(vector.x+this.x<0){
				int mapShift=vector.x+this.x;
				for(int i=mapShift;i<0;i++){
			
					for(int j=0;j<this.height;j++){
						if(j==this.y){
							this.get(j).add(0,wall);
						}else{
							this.get(j).add(0,empty);
						}
				
					}
				}
				this.x=0;
			}else{
				System.out.println("JETZT ANFANG: "+vector.x);
				for(int i=vector.x;i<0;i++){
					System.out.println("JETZT: "+(this.x+i));
					this.get(this.y).set(this.x+i, wall);
				}
				this.x+=vector.x;
			}
			
		}
		
		//set maxwidth
		if(vector.x!=0 && this.width<vector.x+this.x){
		//new maxwidth of the map
		this.width=vector.x+this.x;
		//set every row to the maxwidth
		for(int i=0;i<this.height;i++){
			int updatedWidth = this.width-this.get(i).size();
			for(int j=0;j<updatedWidth;j++){
				this.get(i).add(empty);
			}
		}
		}
		
		System.out.println("y "+this.y);
		System.out.println("x "+this.x);
		System.out.println("width "+this.width);
		System.out.println("height "+this.height);
		System.out.println("vectorx "+vector.x);
		System.out.println("vectory "+vector.y);
		
		if(vector.y>0){
			for(int i=0;i<vector.y;i++){
				System.out.println("heightaktiv: "+this.size());
				this.get(this.y+i).set(this.x,wall);
			}
			this.y+=vector.y-1;
		}

		if(vector.x>0){
			for(int i=0;i<vector.x;i++){
				this.get(this.y).set(this.x+i, wall);
				
			}
			this.x+=vector.x-1;;
		}
		
	}
	
	


}
