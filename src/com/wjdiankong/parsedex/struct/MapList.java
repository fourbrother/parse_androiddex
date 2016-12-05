package com.wjdiankong.parsedex.struct;

import java.util.ArrayList;
import java.util.List;

public class MapList {
	
	/**
	 * struct maplist
		{
		uint size;
		map_item list [size];
		}
	 */
	
	public int size;
	public List<MapItem> map_item = new ArrayList<MapItem>();

}
