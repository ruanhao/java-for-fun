package com.hao.ds.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class TestObject implements Comparable<TestObject> {
	public int value;


	
	@Override
	public int compareTo(TestObject o) {
		return value - o.value;
	}
}