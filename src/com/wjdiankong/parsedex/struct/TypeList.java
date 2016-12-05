package com.wjdiankong.parsedex.struct;

import java.util.ArrayList;
import java.util.List;

public class TypeList {
	
	/**
	 * struct type_list
		{
		uint size;
		ushort type_idx[size];
		}
	 */
	
	public int size;//参数的个数
	public List<Short> type_idx = new ArrayList<Short>();//参数的类型
	
}
