package com.wjdiankong.parsedex;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.wjdiankong.parsedex.struct.ClassDataItem;
import com.wjdiankong.parsedex.struct.ClassDefItem;
import com.wjdiankong.parsedex.struct.CodeItem;
import com.wjdiankong.parsedex.struct.EncodedField;
import com.wjdiankong.parsedex.struct.EncodedMethod;
import com.wjdiankong.parsedex.struct.FieldIdsItem;
import com.wjdiankong.parsedex.struct.HeaderType;
import com.wjdiankong.parsedex.struct.MapItem;
import com.wjdiankong.parsedex.struct.MapList;
import com.wjdiankong.parsedex.struct.MethodIdsItem;
import com.wjdiankong.parsedex.struct.ProtoIdsItem;
import com.wjdiankong.parsedex.struct.StringIdsItem;
import com.wjdiankong.parsedex.struct.TypeIdsItem;

public class ParseDexUtils {
	
	private static int stringIdOffset = 0;
	private static int stringIdsSize = 0;
	private static int stringIdsOffset = 0;
	private static int typeIdsSize = 0;
	private static int typeIdsOffset = 0;
	private static int protoIdsSize = 0;
	private static int protoIdsOffset = 0;
	private static int fieldIdsSize = 0;
	private static int fieldIdsOffset = 0;
	private static int methodIdsSize = 0;
	private static int methodIdsOffset = 0;
	private static int classIdsSize = 0;
	private static int classIdsOffset = 0;
	private static int dataIdsSize = 0;
	private static int dataIdsOffset = 0;
	
	private static int mapListOffset = 0;
	
	private static List<StringIdsItem> stringIdsList = new ArrayList<StringIdsItem>();
	private static List<TypeIdsItem> typeIdsList = new ArrayList<TypeIdsItem>();
	private static List<ProtoIdsItem> protoIdsList = new ArrayList<ProtoIdsItem>();
	private static List<FieldIdsItem> fieldIdsList = new ArrayList<FieldIdsItem>();
	private static List<MethodIdsItem> methodIdsList = new ArrayList<MethodIdsItem>();
	private static List<ClassDefItem> classIdsList = new ArrayList<ClassDefItem>();
	
	private static List<ClassDataItem> dataItemList = new ArrayList<ClassDataItem>();
	
	private static List<CodeItem> directMethodCodeItemList = new ArrayList<CodeItem>();
	private static List<CodeItem> virtualMethodCodeItemList = new ArrayList<CodeItem>();
	
	private static List<String> stringList = new ArrayList<String>();
	
	//这里的map用来存储code数据，因为一个ClassCode都是以SourceFile为单位的，所以这里的key就是SourceFileName来存储
	private static HashMap<String, ClassDefItem> classDataMap = new HashMap<String, ClassDefItem>();
	
	public static void praseDexHeader(byte[] byteSrc){
		HeaderType headerType = new HeaderType();
		//解析魔数
		byte[] magic = Utils.copyByte(byteSrc, 0, 8);
		headerType.magic = magic;
		
		//解析checksum
		byte[] checksumByte = Utils.copyByte(byteSrc, 8, 4);
		headerType.checksum = Utils.byte2int(checksumByte);
		
		//解析siganature
		byte[] siganature = Utils.copyByte(byteSrc, 12, 20);
		headerType.siganature = siganature;
		
		//解析file_size
		byte[] fileSizeByte = Utils.copyByte(byteSrc, 32, 4);
		headerType.file_size = Utils.byte2int(fileSizeByte);
		
		//解析header_size
		byte[] headerSizeByte = Utils.copyByte(byteSrc, 36, 4);
		headerType.header_size = Utils.byte2int(headerSizeByte);
		
		//解析endian_tag
		byte[] endianTagByte = Utils.copyByte(byteSrc, 40, 4);
		headerType.endian_tag = Utils.byte2int(endianTagByte);
		
		//解析link_size
		byte[] linkSizeByte = Utils.copyByte(byteSrc, 44, 4);
		headerType.link_size = Utils.byte2int(linkSizeByte);
		
		//解析link_off
		byte[] linkOffByte = Utils.copyByte(byteSrc, 48, 4);
		headerType.link_off = Utils.byte2int(linkOffByte);
		
		//解析map_off
		byte[] mapOffByte = Utils.copyByte(byteSrc, 52, 4);
		headerType.map_off = Utils.byte2int(mapOffByte);
		
		//解析string_ids_size
		byte[] stringIdsSizeByte = Utils.copyByte(byteSrc, 56, 4);
		headerType.string_ids_size = Utils.byte2int(stringIdsSizeByte);
		
		//解析string_ids_off
		byte[] stringIdsOffByte = Utils.copyByte(byteSrc, 60, 4);
		headerType.string_ids_off = Utils.byte2int(stringIdsOffByte);
		
		//解析type_ids_size
		byte[] typeIdsSizeByte = Utils.copyByte(byteSrc, 64, 4);
		headerType.type_ids_size = Utils.byte2int(typeIdsSizeByte);
		
		//解析type_ids_off
		byte[] typeIdsOffByte = Utils.copyByte(byteSrc, 68, 4);
		headerType.type_ids_off = Utils.byte2int(typeIdsOffByte);
		
		//解析proto_ids_size
		byte[] protoIdsSizeByte = Utils.copyByte(byteSrc, 72, 4);
		headerType.proto_ids_size = Utils.byte2int(protoIdsSizeByte);
		
		//解析proto_ids_off
		byte[] protoIdsOffByte = Utils.copyByte(byteSrc, 76, 4);
		headerType.proto_ids_off = Utils.byte2int(protoIdsOffByte);
		
		//解析field_ids_size
		byte[] fieldIdsSizeByte = Utils.copyByte(byteSrc, 80, 4);
		headerType.field_ids_size = Utils.byte2int(fieldIdsSizeByte);
		
		//解析field_ids_off
		byte[] fieldIdsOffByte = Utils.copyByte(byteSrc, 84, 4);
		headerType.field_ids_off = Utils.byte2int(fieldIdsOffByte);
		
		//解析method_ids_size
		byte[] methodIdsSizeByte = Utils.copyByte(byteSrc, 88, 4);
		headerType.method_ids_size = Utils.byte2int(methodIdsSizeByte);
		
		//解析method_ids_off
		byte[] methodIdsOffByte = Utils.copyByte(byteSrc, 92, 4);
		headerType.method_ids_off = Utils.byte2int(methodIdsOffByte);
		
		//解析class_defs_size
		byte[] classDefsSizeByte = Utils.copyByte(byteSrc, 96, 4);
		headerType.class_defs_size = Utils.byte2int(classDefsSizeByte);
		
		//解析class_defs_off
		byte[] classDefsOffByte = Utils.copyByte(byteSrc, 100, 4);
		headerType.class_defs_off = Utils.byte2int(classDefsOffByte);
		
		//解析data_size
		byte[] dataSizeByte = Utils.copyByte(byteSrc, 104, 4);
		headerType.data_size = Utils.byte2int(dataSizeByte);
		
		//解析data_off
		byte[] dataOffByte = Utils.copyByte(byteSrc, 108, 4);
		headerType.data_off = Utils.byte2int(dataOffByte);
		
		System.out.println("header:"+headerType);
		
		stringIdOffset = headerType.header_size;//header之后就是string ids
		
		stringIdsSize = headerType.string_ids_size;
		stringIdsOffset = headerType.string_ids_off;
		typeIdsSize = headerType.type_ids_size;
		typeIdsOffset = headerType.type_ids_off;
		fieldIdsSize = headerType.field_ids_size;
		fieldIdsOffset = headerType.field_ids_off;
		protoIdsSize = headerType.proto_ids_size;
		protoIdsOffset = headerType.proto_ids_off;
		methodIdsSize = headerType.method_ids_size;
		methodIdsOffset = headerType.method_ids_off;
		classIdsSize = headerType.class_defs_size;
		classIdsOffset = headerType.class_defs_off;
		
		mapListOffset = headerType.map_off;
		
	}
	
	/************************解析字符串********************************/
	public static void parseStringIds(byte[] srcByte){
		int idSize = StringIdsItem.getSize();
		int countIds = stringIdsSize;
		for(int i=0;i<countIds;i++){
			stringIdsList.add(parseStringIdsItem(Utils.copyByte(srcByte, stringIdsOffset+i*idSize, idSize)));
		}
		System.out.println("string size:"+stringIdsList.size());
	}
	
	public static void parseStringList(byte[] srcByte){
		//第一个字节还是字符串的长度
		for(StringIdsItem item : stringIdsList){
			String str = getString(srcByte, item.string_data_off);
			System.out.println("str:"+str);
			stringList.add(str);
		}
	}
	
	/***************************解析类型******************************/
	public static void parseTypeIds(byte[] srcByte){
		int idSize = TypeIdsItem.getSize();
		int countIds = typeIdsSize;
		for(int i=0;i<countIds;i++){
			typeIdsList.add(parseTypeIdsItem(Utils.copyByte(srcByte, typeIdsOffset+i*idSize, idSize)));
		}
		
		//这里的descriptor_idx就是解析之后的字符串中的索引值
		for(TypeIdsItem item : typeIdsList){
			System.out.println("typeStr:"+stringList.get(item.descriptor_idx));
		}
	}
	
	/***************************解析Proto***************************/
	public static void parseProtoIds(byte[] srcByte){
		int idSize = ProtoIdsItem.getSize();
		int countIds = protoIdsSize;
		for(int i=0;i<countIds;i++){
			protoIdsList.add(parseProtoIdsItem(Utils.copyByte(srcByte, protoIdsOffset+i*idSize, idSize)));
		}
		
		for(ProtoIdsItem item : protoIdsList){
			System.out.println("proto:"+stringList.get(item.shorty_idx)+","+stringList.get(item.return_type_idx));
			//有的方法没有参数，这个值就是0
			if(item.parameters_off != 0){
				item = parseParameterTypeList(srcByte, item.parameters_off, item);
			}
		}
	}
	
	//解析方法的所有参数类型
	private static ProtoIdsItem parseParameterTypeList(byte[] srcByte, int startOff, ProtoIdsItem item){
		//解析size和size大小的List中的内容
		byte[] sizeByte = Utils.copyByte(srcByte, startOff, 4);
		int size = Utils.byte2int(sizeByte);
		List<String> parametersList = new ArrayList<String>();
		List<Short> typeList = new ArrayList<Short>(size);
		for(int i=0;i<size;i++){
			byte[] typeByte = Utils.copyByte(srcByte, startOff+4+2*i, 2);
			typeList.add(Utils.byte2Short(typeByte));
		}
		System.out.println("param count:"+size);
		for(int i=0;i<typeList.size();i++){
			System.out.println("type:"+stringList.get(typeList.get(i)));
			int index = typeIdsList.get(typeList.get(i)).descriptor_idx;
			parametersList.add(stringList.get(index));
		}
		
		item.parameterCount = size;
		item.parametersList = parametersList;
		
		return item;
	}
	
	/***************************解析字段****************************/
	public static void parseFieldIds(byte[] srcByte){
		int idSize = FieldIdsItem.getSize();
		int countIds = fieldIdsSize;
		for(int i=0;i<countIds;i++){
			fieldIdsList.add(parseFieldIdsItem(Utils.copyByte(srcByte, fieldIdsOffset+i*idSize, idSize)));
		}
		
		for(FieldIdsItem item : fieldIdsList){
			int classIndex = typeIdsList.get(item.class_idx).descriptor_idx;
			int typeIndex = typeIdsList.get(item.type_idx).descriptor_idx;
			System.out.println("class:"+stringList.get(classIndex)+",name:"+stringList.get(item.name_idx)+",type:"+stringList.get(typeIndex));
		}
	}
	
	/***************************解析方法*****************************/
	public static void parseMethodIds(byte[] srcByte){
		int idSize = MethodIdsItem.getSize();
		int countIds = methodIdsSize;
		for(int i=0;i<countIds;i++){
			methodIdsList.add(parseMethodIdsItem(Utils.copyByte(srcByte, methodIdsOffset+i*idSize, idSize)));
		}
		
		for(MethodIdsItem item : methodIdsList){
			int classIndex = typeIdsList.get(item.class_idx).descriptor_idx;
			int returnIndex = protoIdsList.get(item.proto_idx).return_type_idx;
			String returnTypeStr = stringList.get(typeIdsList.get(returnIndex).descriptor_idx);
			int shortIndex = protoIdsList.get(item.proto_idx).shorty_idx;
			String shortStr = stringList.get(shortIndex);
			List<String> paramList = protoIdsList.get(item.proto_idx).parametersList;
			StringBuilder parameters = new StringBuilder();
			parameters.append(returnTypeStr+"(");
			for(String str : paramList){
				parameters.append(str+",");
			}
			parameters.append(")"+shortStr);
			System.out.println("class:"+stringList.get(classIndex)+",name:"+stringList.get(item.name_idx)+",proto:"+parameters);
		}
		
	}
	
	/****************************解析类*****************************/
	public static void parseClassIds(byte[] srcByte){
		System.out.println("classIdsOffset:"+Utils.bytesToHexString(Utils.int2Byte(classIdsOffset)));
		System.out.println("classIds:"+classIdsSize);
		int idSize = ClassDefItem.getSize();
		int countIds = classIdsSize;
		for(int i=0;i<countIds;i++){
			classIdsList.add(parseClassDefItem(Utils.copyByte(srcByte, classIdsOffset+i*idSize, idSize)));
		}
		for(ClassDefItem item : classIdsList){
			System.out.println("item:"+item);
			int classIdx = item.class_idx;
			TypeIdsItem typeItem = typeIdsList.get(classIdx);
			System.out.println("classIdx:"+stringList.get(typeItem.descriptor_idx));
			int superClassIdx = item.superclass_idx;
			TypeIdsItem superTypeItem = typeIdsList.get(superClassIdx);
			System.out.println("superitem:"+stringList.get(superTypeItem.descriptor_idx));
			int sourceIdx = item.source_file_idx;
			String sourceFile = stringList.get(sourceIdx);
			System.out.println("sourceFile:"+sourceFile);
			classDataMap.put(sourceFile, item);
		}
	}
	
	/***************************解析ClassData***************************/
	public static void parseClassData(byte[] srcByte){
		for(String key : classDataMap.keySet()){
			int dataOffset = classDataMap.get(key).class_data_off;
			System.out.println("data offset:"+Utils.bytesToHexString(Utils.int2Byte(dataOffset)));
			ClassDataItem item = parseClassDataItem(srcByte, dataOffset);
			dataItemList.add(item);
			System.out.println("class item:"+item);
		}
	}
	
	private static ClassDataItem parseClassDataItem(byte[] srcByte, int offset){
		ClassDataItem item = new ClassDataItem();
		for(int i=0;i<4;i++){
			byte[] byteAry = Utils.readUnsignedLeb128(srcByte, offset);
			offset += byteAry.length;
			int size = 0;
			if(byteAry.length == 1){
				size = byteAry[0];
			}else if(byteAry.length == 2){
				size = Utils.byte2Short(byteAry);
			}else if(byteAry.length == 4){
				size = Utils.byte2int(byteAry);
			}
			if(i == 0){
				item.static_fields_size = size;
			}else if(i == 1){
				item.instance_fields_size = size;
			}else if(i == 2){
				item.direct_methods_size = size;
			}else if(i == 3){
				item.virtual_methods_size = size;
			}
		}
		
		
		//解析static_fields数组
		EncodedField[] staticFieldAry = new EncodedField[item.static_fields_size];
		for(int i=0;i<item.static_fields_size;i++){
			/**
			 *  public int filed_idx_diff;
				public int access_flags;
			 */
			EncodedField staticField = new EncodedField();
			staticField.filed_idx_diff = Utils.readUnsignedLeb128(srcByte, offset);
			offset += staticField.filed_idx_diff.length;
			staticField.access_flags = Utils.readUnsignedLeb128(srcByte, offset);
			offset += staticField.access_flags.length;
			staticFieldAry[i] = staticField;
		}
		
		//解析instance_fields数组
		EncodedField[] instanceFieldAry = new EncodedField[item.instance_fields_size];
		for(int i=0;i<item.instance_fields_size;i++){
			/**
			 *  public int filed_idx_diff;
				public int access_flags;
			 */
			EncodedField instanceField = new EncodedField();
			instanceField.filed_idx_diff = Utils.readUnsignedLeb128(srcByte, offset);
			offset += instanceField.filed_idx_diff.length;
			instanceField.access_flags = Utils.readUnsignedLeb128(srcByte, offset);
			offset += instanceField.access_flags.length;
			instanceFieldAry[i] = instanceField;
		}
		
		//解析static_methods数组
		EncodedMethod[] staticMethodsAry = new EncodedMethod[item.direct_methods_size];
		for(int i=0;i<item.direct_methods_size;i++){
			/**
			 *  public byte[] method_idx_diff;
				public byte[] access_flags;
				public byte[] code_off;
			 */
			EncodedMethod directMethod = new EncodedMethod();
			directMethod.method_idx_diff = Utils.readUnsignedLeb128(srcByte, offset);
			offset += directMethod.method_idx_diff.length;
			directMethod.access_flags = Utils.readUnsignedLeb128(srcByte, offset);
			offset += directMethod.access_flags.length;
			directMethod.code_off = Utils.readUnsignedLeb128(srcByte, offset);
			offset += directMethod.code_off.length;
			staticMethodsAry[i] = directMethod;
		}
		
		//解析virtual_methods数组
		EncodedMethod[] instanceMethodsAry = new EncodedMethod[item.virtual_methods_size];
		for(int i=0;i<item.virtual_methods_size;i++){
			/**
			 *  public byte[] method_idx_diff;
				public byte[] access_flags;
				public byte[] code_off;
			 */
			EncodedMethod instanceMethod = new EncodedMethod();
			instanceMethod.method_idx_diff = Utils.readUnsignedLeb128(srcByte, offset);
			offset += instanceMethod.method_idx_diff.length;
			instanceMethod.access_flags = Utils.readUnsignedLeb128(srcByte, offset);
			offset += instanceMethod.access_flags.length;
			instanceMethod.code_off = Utils.readUnsignedLeb128(srcByte, offset);
			offset += instanceMethod.code_off.length;
			instanceMethodsAry[i] = instanceMethod;
		}
		
		item.static_fields = staticFieldAry;
		item.instance_fields = instanceFieldAry;
		item.direct_methods = staticMethodsAry;
		item.virtual_methods = instanceMethodsAry;
		
		return item;
	}
	
	/***************************解析代码内容***************************/
	public static void parseCode(byte[] srcByte){
		for(ClassDataItem item : dataItemList){
			
			for(EncodedMethod item1 : item.direct_methods){
				int offset = Utils.decodeUleb128(item1.code_off);
				CodeItem items = parseCodeItem(srcByte, offset);
				directMethodCodeItemList.add(items);
				System.out.println("direct method item:"+items);
			}
			
			for(EncodedMethod item1 : item.virtual_methods){
				int offset = Utils.decodeUleb128(item1.code_off);
				CodeItem items = parseCodeItem(srcByte, offset);
				virtualMethodCodeItemList.add(items);
				System.out.println("virtual method item:"+items);
			}
			
		}
	}
	
	private static CodeItem parseCodeItem(byte[] srcByte, int offset){
		CodeItem item = new CodeItem();
		
		/**
		 *  public short registers_size;
			public short ins_size;
			public short outs_size;
			public short tries_size;
			public int debug_info_off;
			public int insns_size;
			public short[] insns;
		 */
		byte[] regSizeByte = Utils.copyByte(srcByte, offset, 2);
		item.registers_size = Utils.byte2Short(regSizeByte);
		
		byte[] insSizeByte = Utils.copyByte(srcByte, offset+2, 2);
		item.ins_size = Utils.byte2Short(insSizeByte);
		
		byte[] outsSizeByte = Utils.copyByte(srcByte, offset+4, 2);
		item.outs_size = Utils.byte2Short(outsSizeByte);
		
		byte[] triesSizeByte = Utils.copyByte(srcByte, offset+6, 2);
		item.tries_size = Utils.byte2Short(triesSizeByte);
		
		byte[] debugInfoByte = Utils.copyByte(srcByte, offset+8, 4);
		item.debug_info_off = Utils.byte2int(debugInfoByte);
		
		byte[] insnsSizeByte = Utils.copyByte(srcByte, offset+12, 4);
		item.insns_size = Utils.byte2int(insnsSizeByte);
		
		short[] insnsAry = new short[item.insns_size];
		int aryOffset = offset + 16;
		for(int i=0;i<item.insns_size;i++){
			byte[] insnsByte = Utils.copyByte(srcByte, aryOffset+i*2, 2);
			insnsAry[i] = Utils.byte2Short(insnsByte);
		}
		item.insns = insnsAry;
		
		return item;
	}
	
	public static void parseMapItemList(byte[] srcByte){
		MapList mapList = new MapList();
		byte[] sizeByte = Utils.copyByte(srcByte, mapListOffset, 4);
		int size = Utils.byte2int(sizeByte);
		for(int i=0;i<size;i++){
			mapList.map_item.add(parseMapItem(Utils.copyByte(srcByte, mapListOffset+4+i*MapItem.getSize(), MapItem.getSize())));
		}
	}
	
	private static StringIdsItem parseStringIdsItem(byte[] srcByte){
		StringIdsItem item = new StringIdsItem();
		byte[] idsByte = Utils.copyByte(srcByte, 0, 4);
		item.string_data_off = Utils.byte2int(idsByte);
		return item;
	}
	
	private static TypeIdsItem parseTypeIdsItem(byte[] srcByte){
		TypeIdsItem item = new TypeIdsItem();
		byte[] descriptorIdxByte = Utils.copyByte(srcByte, 0, 4);
		item.descriptor_idx = Utils.byte2int(descriptorIdxByte);
		return item;
	}
	
	private static ProtoIdsItem parseProtoIdsItem(byte[] srcByte){
		ProtoIdsItem item = new ProtoIdsItem();
		byte[] shortyIdxByte = Utils.copyByte(srcByte, 0, 4);
		item.shorty_idx = Utils.byte2int(shortyIdxByte);
		byte[] returnTypeIdxByte = Utils.copyByte(srcByte, 4, 8);
		item.return_type_idx = Utils.byte2int(returnTypeIdxByte);
		byte[] parametersOffByte = Utils.copyByte(srcByte, 8, 4);
		item.parameters_off = Utils.byte2int(parametersOffByte);
		return item;
	}
	
	
	private static FieldIdsItem parseFieldIdsItem(byte[] srcByte){
		FieldIdsItem item = new FieldIdsItem();
		byte[] classIdxByte = Utils.copyByte(srcByte, 0, 2);
		item.class_idx = Utils.byte2Short(classIdxByte);
		byte[] typeIdxByte = Utils.copyByte(srcByte, 2, 2);
		item.type_idx = Utils.byte2Short(typeIdxByte);
		byte[] nameIdxByte = Utils.copyByte(srcByte, 4, 4);
		item.name_idx = Utils.byte2int(nameIdxByte);
		return item;
	}
	
	private static MethodIdsItem parseMethodIdsItem(byte[] srcByte){
		MethodIdsItem item = new MethodIdsItem();
		byte[] classIdxByte = Utils.copyByte(srcByte, 0, 2);
		item.class_idx = Utils.byte2Short(classIdxByte);
		byte[] protoIdxByte = Utils.copyByte(srcByte, 2, 2);
		item.proto_idx = Utils.byte2Short(protoIdxByte);
		byte[] nameIdxByte = Utils.copyByte(srcByte, 4, 4);
		item.name_idx = Utils.byte2int(nameIdxByte);
		return item;
	}
	
	private static ClassDefItem parseClassDefItem(byte[] srcByte){
		ClassDefItem item = new ClassDefItem();
		byte[] classIdxByte = Utils.copyByte(srcByte, 0, 4);
		item.class_idx = Utils.byte2int(classIdxByte);
		byte[] accessFlagsByte = Utils.copyByte(srcByte, 4, 4);
		item.access_flags = Utils.byte2int(accessFlagsByte);
		byte[] superClassIdxByte = Utils.copyByte(srcByte, 8, 4);
		item.superclass_idx = Utils.byte2int(superClassIdxByte);
		//这里如果class没有interfaces的话，这里就为0
		byte[] iterfacesOffByte = Utils.copyByte(srcByte, 12, 4);
		item.iterfaces_off = Utils.byte2int(iterfacesOffByte);
		//如果此项信息缺失，值为0xFFFFFF
		byte[] sourceFileIdxByte = Utils.copyByte(srcByte, 16, 4);
		item.source_file_idx = Utils.byte2int(sourceFileIdxByte);
		
		byte[] annotationsOffByte = Utils.copyByte(srcByte, 20, 4);
		item.annotations_off = Utils.byte2int(annotationsOffByte);
		
		byte[] classDataOffByte = Utils.copyByte(srcByte, 24, 4);
		item.class_data_off = Utils.byte2int(classDataOffByte);
		
		byte[] staticValueOffByte = Utils.copyByte(srcByte, 28, 4);
		item.static_value_off = Utils.byte2int(staticValueOffByte);
		return item;
	}
	
	private static MapItem parseMapItem(byte[] srcByte){
		MapItem item = new MapItem();
		byte[] typeByte = Utils.copyByte(srcByte, 0, 2);
		item.type = Utils.byte2Short(typeByte);
		byte[] unuseByte = Utils.copyByte(srcByte, 2, 2);
		item.unuse = Utils.byte2Short(unuseByte);
		byte[] sizeByte = Utils.copyByte(srcByte, 4, 4);
		item.size = Utils.byte2int(sizeByte);
		byte[] offsetByte = Utils.copyByte(srcByte, 8, 4);
		item.offset = Utils.byte2int(offsetByte);
		return item;
	}
	
	/**
	 * 这里是解析一个字符串
	 * 有两种方式
	 * 1、第一个字节就是字符串的长度
	 * 2、每个字符串的结束符是00
	 * @param srcByte
	 * @param startOff
	 * @return
	 */
	private static String getString(byte[] srcByte, int startOff){
		
		//第一种方式
		byte size = srcByte[startOff];
		byte[] strByte = Utils.copyByte(srcByte, startOff+1, size);
		String result = "";
		try{
			result = new String(strByte, "UTF-8");
		}catch(Exception e){
		}
		return result;
		
		//第二种方式
		/*System.out.println("off:"+Utils.bytesToHexString(Utils.int2Byte(startOff)));
		List<Byte> byteList = new ArrayList<Byte>();
		//第一个字节是长度，所以过滤一下
		byte b = srcByte[startOff+1];
		int index = 0;
		while(b != 0){
			byteList.add(b);
			index++;
			b = srcByte[startOff+index];
		}
		byte[] byteAry = new byte[byteList.size()+1];
		for(int i=0;i<byteList.size();i++){
			byteAry[i] = byteList.get(i);
		}
		byteAry[byteList.size()] = 0;
		String result = "";
		try{
			result = new String(byteAry, "UTF-8");
		}catch(Exception e){
		}
		return result;*/
		
	}
	
}
