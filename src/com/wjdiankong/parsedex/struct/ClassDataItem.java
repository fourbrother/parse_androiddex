package com.wjdiankong.parsedex.struct;

public class ClassDataItem {
	
	/**
	 *  uleb128 unsigned little-endian base 128
		struct class_data_item
		{
			uleb128 static_fields_size;
			uleb128 instance_fields_size;
			uleb128 direct_methods_size;
			uleb128 virtual_methods_size;
			encoded_field static_fields [ static_fields_size ];
			encoded_field instance_fields [ instance_fields_size ];
			encoded_method direct_methods [ direct_method_size ];
			encoded_method virtual_methods [ virtual_methods_size ];
		}
	 */
	
	//uleb128只用来编码32位的整型数
	public int static_fields_size;
	public int instance_fields_size;
	public int direct_methods_size;
	public int virtual_methods_size;
	
	public EncodedField[] static_fields;
	public EncodedField[] instance_fields;
	public EncodedMethod[] direct_methods;
	public EncodedMethod[] virtual_methods;
	
	@Override
	public String toString(){
		return "static_fields_size:"+static_fields_size+",instance_fields_size:"
				+instance_fields_size+",direct_methods_size:"+direct_methods_size+",virtual_methods_size:"+virtual_methods_size
				+"\n"+getFieldsAndMethods();
	}
	
	private String getFieldsAndMethods(){
		StringBuilder sb = new StringBuilder();
		sb.append("static_fields:\n");
		for(int i=0;i<static_fields.length;i++){
			sb.append(static_fields[i]+"\n");
		}
		sb.append("instance_fields:\n");
		for(int i=0;i<instance_fields.length;i++){
			sb.append(instance_fields[i]+"\n");
		}
		sb.append("direct_methods:\n");
		for(int i=0;i<direct_methods.length;i++){
			sb.append(direct_methods[i]+"\n");
		}
		sb.append("virtual_methods:\n");
		for(int i=0;i<virtual_methods.length;i++){
			sb.append(virtual_methods[i]+"\n");
		}
		return sb.toString();
	}

}
