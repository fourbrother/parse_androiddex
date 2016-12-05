package com.wjdiankong.parsedex.struct;

public class MapItem {
	
	/**
	 * struct map_item
		{
		ushort type;
		ushort unuse;
		uint size;
		uint offset;
		}
	 */
	
	public short type;
	public short unuse;
	public int size;
	public int offset;
	
	public static int getSize(){
		return 2 + 2 + 4 + 4;
	}
	
	@Override
	public String toString(){
		return "type:"+type+",unuse:"+unuse+",size:"+size+",offset:"+offset;
	}

}
