package mapping;

import java.util.ArrayList;

public class MapTestingClass {
	public static Map map3 = new Map();
//	public static Map map = new Map();
	public static Map map2 = new Map();
	
	public static void main(String[] args) {
		MapTestingClass t = new MapTestingClass();
		System.out.println(t.map3.toString());
	}
	
	
	public MapTestingClass() {

		TestVector vector1 = new TestVector(5,0);
//		vector1.x = 5;
//		vector1.y = 0;
		TestVector vector2 = new TestVector(0,5);
//		vector2.x = 0;
//		vector2.y = 5;
		TestVector vector3 = new TestVector(-6,0);
//		vector3.x = -5;
//		vector3.y = 0;
		TestVector vector4 = new TestVector(0,-6);
//		vector4.x = 0;
//		vector4.y = -5;
		map3.updateMapFromVectors(vector1);
		map3.updateMapFromVectors(vector2);
		map3.updateMapFromVectors(vector3);
		map3.updateMapFromVectors(vector4);
		MapObject wall = MapObject.WALL;
		MapObject empty = MapObject.EMPTY;
		MapObject obstacle = MapObject.OBSTACLE;

//		map3.get(2).set(2, obstacle);

		MapObject robot = MapObject.ROBOT;
		map3.get(2).set(2, obstacle);


		MapObject[] mapObjects = new MapObject[20];
		ArrayList<MapObject> mapObjectsList = new ArrayList<MapObject>();

		for (int i = 20; i > 0; i--) {
			mapObjectsList.add(wall);
		}
		for (int i = 0; i < mapObjects.length; i++) {
			mapObjects[i] = wall;
		}

		mapObjects[3] = empty;
		mapObjects[9] = robot;
		

//		map.buildMapFromOneDimensionalArray(mapObjects, 4);
		map2.buildMapFromOneDimensionalArrayList(mapObjectsList, 4);

		// System.out.println(map.toString());
		// System.out.println(map2.toString());
		// for (ArrayList<MapObject> mappi : map3) {
		// System.out.println(mappi.toString());
		// }

	}
}
