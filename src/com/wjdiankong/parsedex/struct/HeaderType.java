package com.wjdiankong.parsedex.struct;

import com.wjdiankong.parsedex.Utils;

public class HeaderType {
	
	/**
	 * struct header_item
		{
		ubyte[8] magic;
		unit checksum;
		ubyte[20] siganature;
		uint file_size;
		uint header_size;
		unit endian_tag;
		uint link_size;
		uint link_off;
		uint map_off;
		uint string_ids_size;
		uint string_ids_off;
		uint type_ids_size;
		uint type_ids_off;
		uint proto_ids_size;
		uint proto_ids_off;
		uint method_ids_size;
		uint method_ids_off;
		uint class_defs_size;
		uint class_defs_off;
		uint data_size;
		uint data_off;
		}
	 */
	public byte[] magic = new byte[8];
	public int checksum;
	public byte[] siganature = new byte[20];
	public int file_size;
	public int header_size;
	public int endian_tag;
	public int link_size;
	public int link_off;
	public int map_off;
	public int string_ids_size;
	public int string_ids_off;
	public int type_ids_size;
	public int type_ids_off;
	public int proto_ids_size;
	public int proto_ids_off;
	public int field_ids_size;
	public int field_ids_off;
	public int method_ids_size;
	public int method_ids_off;
	public int class_defs_size;
	public int class_defs_off;
	public int data_size;
	public int data_off;
	
	@Override
	public String toString(){
		return "magic:"+Utils.bytesToHexString(magic)+"\n"
				+ "checksum:"+checksum + "\n"
				+ "siganature:"+Utils.bytesToHexString(siganature) + "\n"
				+ "file_size:"+file_size + "\n"
				+ "header_size:"+header_size + "\n"
				+ "endian_tag:"+endian_tag + "\n"
				+ "link_size:"+link_size + "\n"
				+ "link_off:"+Utils.bytesToHexString(Utils.int2Byte(link_off)) + "\n"
				+ "map_off:"+Utils.bytesToHexString(Utils.int2Byte(map_off)) + "\n"
				+ "string_ids_size:"+string_ids_size + "\n"
				+ "string_ids_off:"+Utils.bytesToHexString(Utils.int2Byte(string_ids_off)) + "\n"
				+ "type_ids_size:"+type_ids_size + "\n"
				+ "type_ids_off:"+Utils.bytesToHexString(Utils.int2Byte(type_ids_off)) + "\n"
				+ "proto_ids_size:"+proto_ids_size + "\n"
				+ "proto_ids_off:"+Utils.bytesToHexString(Utils.int2Byte(proto_ids_off)) + "\n"
				+ "field_ids_size:"+field_ids_size + "\n"
				+ "field_ids_off:"+Utils.bytesToHexString(Utils.int2Byte(field_ids_off)) + "\n"
				+ "method_ids_size:"+method_ids_size + "\n"
				+ "method_ids_off:"+Utils.bytesToHexString(Utils.int2Byte(method_ids_off)) + "\n"
				+ "class_defs_size:"+class_defs_size + "\n"
				+ "class_defs_off:"+Utils.bytesToHexString(Utils.int2Byte(class_defs_off)) + "\n"
				+ "data_size:"+data_size + "\n"
				+ "data_off:"+Utils.bytesToHexString(Utils.int2Byte(data_off));
				
				
	}

}
