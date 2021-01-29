package com.hao.ds.utils.printer;

import com.hao.ds.utils.Color;

public abstract class Printer {
	/**
	 * 二叉树的基本信息
	 */
	protected BinaryTreeInfo tree;
	
	public Printer(BinaryTreeInfo tree) {
		this.tree = tree;
	}
	
	/**
	 * 生成打印的字符串
	 */
	public abstract String printString();
	
	/**
	 * 打印后换行
	 */
	public void println() {
		print();
		System.out.println();
	}

	public void printlnWithColor() {
		printWithColor();
		System.out.println();
	}
	
	/**
	 * 打印
	 */
	public void print() {
		System.out.print(printString());
	}

	public void printWithColor() {
		String output = printString();
		output = output.replace("R", Color.RED_BOLD + "R" + Color.RESET);
		output = output.replace("B", Color.BLACK_BOLD + "B" + Color.RESET);

		System.out.print(output);
	}
}
