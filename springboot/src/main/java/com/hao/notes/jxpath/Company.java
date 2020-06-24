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
public class Company {
	private String name;
	private List<Department> departmentList;

	public Company(String name) {
	    this.name = name;
	}

}
