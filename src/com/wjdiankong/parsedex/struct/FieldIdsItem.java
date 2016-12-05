package com.wjdiankong.parsedex.struct;

public class FieldIdsItem {
	
	/**
	 * struct filed_id_item
		{
		ushort class_idx;
		ushort type_idx;
		uint name_idx;
		}
	 */
	
	public short class_idx;
	public short type_idx;
	public int name_idx;
	
	public static int getSize(){
		return 2 + 2 + 4;
	}

	@Override
	public String toString(){
		return "class_idx:"+class_idx+",type_idx:"+type_idx+",name_idx:"+name_idx;
	}
	
}
