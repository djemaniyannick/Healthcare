package com.codeimmig.yannick.util;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

//jdk8 static method + default method allowed 
public interface MyCollectionsUtil {
	public static Map<Long, String> convertToMpa(List<Object[]> list){
		Map<Long, String> map=list.stream()
								.collect(Collectors
										.toMap(ob->Long.valueOf(ob[0].toString()), 
												ob->ob[1].toString()));
		return map;
		
	}
	
	public static Map<Long, String> convertToMapIndex(List<Object[]> list) {
		//Java 8 Stream API
		Map<Long,String> map =
				list
				.stream()
				.collect(Collectors.toMap(
						ob->Long.valueOf(
								ob[0].toString()), 
						ob->ob[1].toString()+" "+ob[2].toString()));
		return map;
	}

}
