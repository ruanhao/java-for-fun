package com.hao.notes.jxpath;


import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Department {
	private List<Employee> employees;
	private String name;

	public Department(String name) {
	    this.name = name;
	}
}
