package com.wjdiankong.parsedex.struct;

import com.wjdiankong.parsedex.Utils;

public class EncodedField {
	
	/**
	 * struct encoded_field
		{
			uleb128 filed_idx_diff; // index into filed_ids for ID of this filed
			uleb128 access_flags; // access flags like public, static etc.
		}
	 */
	public byte[] filed_idx_diff;
	public byte[] access_flags;
	
	@Override
	public String toString(){
		return "field_idx_diff:"+Utils.bytesToHexString(filed_idx_diff) + ",access_flags:"+Utils.bytesToHexString(filed_idx_diff);
	}

}
