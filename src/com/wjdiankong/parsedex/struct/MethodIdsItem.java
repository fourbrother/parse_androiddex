package com.wjdiankong.parsedex.struct;

public class MethodIdsItem {
	
	/**
	 * struct filed_id_item
		{
		ushort class_idx;
		ushort proto_idx;
		uint name_idx;
		}
	 */
	
	public short class_idx;
	public short proto_idx;
	public int name_idx;
	
	public static int getSize(){
		return 2 + 2 + 4;
	}
	
	@Override
	public String toString(){
		return "class_idx:"+class_idx+",proto_idx:"+proto_idx+",name_idx:"+name_idx;
	}

}
