package com.hao.ds.sort;

import com.hao.ds.utils.Color;
import com.hao.ds.utils.TestObject;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import lombok.SneakyThrows;

@SuppressWarnings("unchecked")
public abstract class Sort<T extends Comparable<T>> implements Comparable<Sort<T>> {
	protected T[] array;
	private int cmpCount;
	private int swapCount;
	private long time;
	private boolean testingStable;
	private DecimalFormat fmt = new DecimalFormat("#.00");

	public void printArray() {
		if (testingStable) return;
		for (T t : array) {
			System.out.print(t + " ");
		}
		System.out.println();
	}
	
	public void sort(T[] array) {
		if (array == null || array.length < 2) return;
		
		this.array = array;
		long begin = System.currentTimeMillis();
		sort();
		time = System.currentTimeMillis() - begin;
	}
	
	@Override
	public int compareTo(Sort<T> o) {
		int result = (int)(time - o.time);
		if (result != 0) return result;
		
		result = cmpCount - o.cmpCount;
		if (result != 0) return result;
		
		return swapCount - o.swapCount;
	}
	
	protected abstract void sort();
	
	/*
	 * 返回值等于0，代表 array[i1] == array[i2]
	 * 返回值小于0，代表 array[i1] < array[i2]
	 * 返回值大于0，代表 array[i1] > array[i2]
	 */
	protected int cmp(int i1, int i2) {
		cmpCount++;
		return array[i1].compareTo(array[i2]);
	}

	protected Comparator compare(T v1) {
		return new Comparator(v1);
	}

	protected Comparator compare(int index) {
		return new Comparator(array[index]);
	}

	protected int cmp(T v1, T v2) {
		cmpCount++;
		return v1.compareTo(v2);
	}
	
	protected void swap(int i1, int i2) {
		swapCount++;
		T tmp = array[i1];
		array[i1] = array[i2];
		array[i2] = tmp;
	}

	public String info() {
		String timeStr =         "耗时：" + (time / 1000.0) + "s(" + time + "ms)";
		String compareCountStr = "比较：" + numberString(cmpCount);
		String swapCountStr =    "交换：" + numberString(swapCount);
		String stableStr =       "稳定：" + (isStable() ? Color.GREEN_BOLD + "✓" + Color.RESET : Color.RED_BOLD + "✗" + Color.RESET);
		return "【" + getClass().getSimpleName() + "】\n "
				+ stableStr + "\t\t"
				+ timeStr + "\t\t"
				+ compareCountStr + "\t\t"
				+ swapCountStr + "\n"
				+ "------------------------------------------------------------------";
	}

	private String numberString(int number) {
		if (number < 10000) return "" + number;
		
		if (number < 100000000) return fmt.format(number / 10000.0) + "万";
		return fmt.format(number / 100000000.0) + "亿";
	}



//	private boolean isStable2() {
//		Student[] students = new Student[20];
//		for (int i = 0; i < students.length; i++) {
//			students[i] = new Student(i * 10, 10);
//		}
//		sort((T[]) students);
//		for (int i = 1; i < students.length; i++) {
//			int score = students[i].score;
//			int prevScore = students[i - 1].score;
//			if (score != prevScore + 10) return false;
//		}
//		return true;
//	}

	@SneakyThrows
	protected boolean isStable3() {
		int size = 1000;
		Integer[] objs = new Integer[size];
		for (int i = 0; i < size; i++) {
			Integer obj = new Integer(Math.random() > 0.5 ? 1 : 0);
			objs[i] = obj;
		}
		Integer value = objs[0];
		List<Integer> seqBeforeSort = new LinkedList<>();
		for (Integer obj : objs) {
			if (Objects.equals(obj, value)) seqBeforeSort.add(obj);
		}
		Sort instance = this.getClass().newInstance();
		instance.testingStable = true;
		instance.sort((T[]) objs);
		List<Integer> seqAfterSort = new LinkedList<>();
		for (Integer obj : objs) {
			if (Objects.equals(obj, value)) seqAfterSort.add(obj);
		}
		for (int i = 0; i < seqBeforeSort.size(); i++) {
			if (seqBeforeSort.get(i) != seqAfterSort.get(i)) return false;
		}
		return true;
	}

	@SneakyThrows
	protected boolean isStable2() {
		int size = 1000;
		TestObject[] objs = new TestObject[size];
		for (int i = 0; i < size; i++) {
			TestObject obj = new TestObject(Math.random() > 0.5 ? 1 : 0);
			objs[i] = obj;
		}
		int value = Arrays.asList(objs).get(0).getValue();
		List<TestObject> seqBeforeSort = new LinkedList<>();
		for (TestObject obj : objs) {
			if (obj.value == value) seqBeforeSort.add(obj);
		}
		Sort instance = this.getClass().newInstance();
		instance.testingStable = true;
		instance.sort((T[]) objs);
		List<TestObject> seqAfterSort = new LinkedList<>();
		for (TestObject obj : objs) {
			if (obj.value == value) seqAfterSort.add(obj);
		}
		for (int i = 0; i < seqBeforeSort.size(); i++) {
			if (seqBeforeSort.get(i) != seqAfterSort.get(i)) return false;
		}
		return true;
	}

	protected boolean isStable() {
		return isStable3();
	}

	/**
	 * 如果相等的2个元素，在排序前后的相对位置保持不变，则此排序算法是稳定的
	 */
//	@SneakyThrows
//	private boolean isStable() {
//		int size = 10;
//		TestObject[] objs = new TestObject[size];
//		Object[] originRefs = new Object[size];
//		for (int i = 0; i < size; i++) {
//			TestObject obj = new TestObject((i + 2) / 2 * 2);
//			objs[i] = obj;
//			originRefs[i] = obj;
//		}
//		Sort instance = this.getClass().newInstance();
//		instance.testingStable = true;
//		instance.sort((T[]) objs);
//		for (int i = 0; i < objs.length; i++) {
//			if (objs[i] != originRefs[i]) return false;
//		}
//		return true;
//	}

	public class Comparator {

		T value;

		public Comparator(T value) {
			this.value = value;
		}

		public boolean gt(T e) {
			return cmp(value, e) > 0;
		}

		public boolean gt(int index) {
			return cmp(value, array[index]) > 0;
		}

		public boolean ge(T e) {
			return cmp(value, e) >= 0;
		}

		public boolean lt(T e) {
			return cmp(value, e) < 0;
		}

		public boolean le(T e) {
			return cmp(value, e) <= 0;
		}

		public boolean lt(int index) {
			return cmp(value, array[index]) < 0;
		}

		public boolean ge(int index) {
			return cmp(value, array[index]) >= 0;
		}
	}
}