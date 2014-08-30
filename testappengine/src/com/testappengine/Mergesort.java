package com.testappengine;

import java.util.ArrayList;
import java.util.Date;

import com.google.api.client.util.DateTime;
import com.testappengine.ideaendpoint.model.Idea;

public class Mergesort {
	public static Idea[] topIdeas, bottomIdeas, olderIdeas, newerIdeas,
			hotIdeas, coldIdeas;
	private static Idea[] helperIdeas, currentIdeas;
	public static ArrayList<Idea> catIdeas, topFilteredIdeas;
	private static int[] numbers, helper;
	private static int number;
	private static String currentCat = "Movie";
	private static String currentTop = "All Time";
	private static final long DAY_LENGTH = 86400000L;
	private static final long WEEK_LENGTH = DAY_LENGTH*7L;
	private static final long MONTH_LENGTH = DAY_LENGTH*30L;
	private static final long YEAR_LENGTH = DAY_LENGTH*365L;

	public static void sort(ArrayList<Idea> values) {
		number = values.size();
		createNewOldIdeas(values);
		createTopBotIdeas(values);
		createHotColdIdeas(values);
		catSort("Movie");
		topSort("All Time");
	}
	
	private static void createNewOldIdeas(ArrayList<Idea> values) {
		currentIdeas = new Idea[number];
		olderIdeas = new Idea[number];
		newerIdeas = new Idea[number];
		helperIdeas = new Idea[number];
		numbers = new int[number];
		helper = new int[number];
		values.toArray(currentIdeas);
		values.toArray(olderIdeas);
		for (int i = 0; i < values.size(); i++) {
			String tempStr = values.get(i).getPostDate().toString();
			numbers[i] = Integer.parseInt(tempStr.substring(0, 4)
					+ tempStr.substring(5, 7) + tempStr.substring(8, 10));
		}
		mergesort(0, number - 1, olderIdeas);
		reverseArray(olderIdeas, newerIdeas);
	}
	
	private static void createTopBotIdeas(ArrayList<Idea> values) {
		topIdeas = new Idea[number];
		bottomIdeas = new Idea[number];
		helperIdeas = new Idea[number];
		numbers = new int[number];
		helper = new int[number];
		values.toArray(bottomIdeas);
		for (int i = 0; i < values.size(); i++) {
			int tempUp = values.get(i).getUpvotes();
			int tempDown = values.get(i).getDownvotes();
			int tempProm = values.get(i).getPromotions();
			if (tempDown == 0) {
				if (tempUp == 0) {
					numbers[i] = 5000;
				} else {
					numbers[i] = 10000 + values.get(i).getUpvotes();
				}
			} else {
				numbers[i] = (int) (((tempUp / ((float) (tempUp + tempDown))) * 10000)
						+ tempUp - tempDown);
			}
			if (tempDown + tempUp < 4) {
				numbers[i] -= 10000;
			}
			numbers[i] += tempProm * 100;
		}
		mergesort(0, number - 1, bottomIdeas);
		reverseArray(bottomIdeas, topIdeas);
	}

	private static void createHotColdIdeas(ArrayList<Idea> values) {
		coldIdeas = new Idea[number];
		hotIdeas = new Idea[number];
		helperIdeas = new Idea[number];
		numbers = new int[number];
		helper = new int[number];
		values.toArray(coldIdeas);
		for (int i = 0; i < values.size(); i++) {
			int tempUp = values.get(i).getUpvotes();
			int tempDown = values.get(i).getDownvotes();
			int tempProm = values.get(i).getPromotions();
			long postDate = values.get(i).getPostDate().getValue();
			long currentDate = new Date().getTime();
			if (tempDown == 0) {
				if (tempUp == 0) {
					numbers[i] = 5000;
				} else {
					numbers[i] = 10000 + values.get(i).getUpvotes();
				}
			} else {
				numbers[i] = (int) (((tempUp / ((float) (tempUp + tempDown))) * 10000)
						+ tempUp - tempDown);
			}
			numbers[i] += tempProm * 500;
			numbers[i] -= (currentDate - postDate) / 100000;
		}
		mergesort(0, number - 1, coldIdeas);
		reverseArray(coldIdeas, hotIdeas);
	}
	
	public static void catSort(String category) {
		currentCat = category;
		catIdeas = new ArrayList<Idea>();
		for (int i = 0; i < currentIdeas.length; i++) {
			if (currentIdeas[i].getCategory().equals(category)) {
				catIdeas.add(currentIdeas[i]);
			}
		}
		for (int i = 0; i < currentIdeas.length; i++) {
			if (!currentIdeas[i].getCategory().equals(category)) {
				catIdeas.add(currentIdeas[i]);
			}
		}
	}

	public static void topSort(String topfilter) {
		currentTop = topfilter;
		topFilteredIdeas = new ArrayList<Idea>();
		DateTime now = new DateTime(new Date());
		if (topfilter.equals("Today")) {
			for (int i = 0; i < topIdeas.length; i++) {
				if((now.getValue() - topIdeas[i].getPostDate().getValue()) < DAY_LENGTH) {
					topFilteredIdeas.add(topIdeas[i]);
				} else {
					topFilteredIdeas.add(0, topIdeas[i]);
				}
			}
		} else if (topfilter.equals("This Week")) {
			for (int i = 0; i < topIdeas.length; i++) {
				if((now.getValue() - topIdeas[i].getPostDate().getValue()) < WEEK_LENGTH) {
					topFilteredIdeas.add(topIdeas[i]);
				} else {
					topFilteredIdeas.add(0, topIdeas[i]);
				}
			}
		} else if (topfilter.equals("This Month")) {
			for (int i = 0; i < topIdeas.length; i++) {
				if((now.getValue() - topIdeas[i].getPostDate().getValue()) < MONTH_LENGTH) {
					topFilteredIdeas.add(topIdeas[i]);
				} else {
					topFilteredIdeas.add(0, topIdeas[i]);
				}
			}
		} else if (topfilter.equals("This Year")) {
			for (int i = 0; i < topIdeas.length; i++) {
				if((now.getValue() - topIdeas[i].getPostDate().getValue()) < YEAR_LENGTH) {
					topFilteredIdeas.add(topIdeas[i]);
				} else {
					topFilteredIdeas.add(0, topIdeas[i]);
				}
			}
		} else if(topfilter.equals("All Time")) {
			for (int i = 0; i < topIdeas.length; i++) {
				topFilteredIdeas.add(topIdeas[i]);
			}
		}
	}

	private static <T> void reverseArray(T[] origin, T[] destination) {
		for (int i = 0; i < origin.length; i++) {
			destination[i] = origin[origin.length - (i + 1)];
		}
	}

	private static void mergesort(int low, int high, Idea[] ideas) {
		// check if low is smaller then high, if not then the array is sorted
		if (low < high) {
			// Get the index of the element which is in the middle
			int middle = low + (high - low) / 2;
			// Sort the left side of the array
			mergesort(low, middle, ideas);
			// Sort the right side of the array
			mergesort(middle + 1, high, ideas);
			// Combine them both
			merge(low, middle, high, ideas);
		}
	}

	private static void merge(int low, int middle, int high, Idea[] ideas) {

		// Copy both parts into the helper array
		for (int i = low; i <= high; i++) {
			helper[i] = numbers[i];
			helperIdeas[i] = ideas[i];
		}

		int i = low;
		int j = middle + 1;
		int k = low;
		// Copy the smallest values from either the left or the right side back
		// to the original array
		while (i <= middle && j <= high) {
			if (helper[i] <= helper[j]) {
				numbers[k] = helper[i];
				ideas[k] = helperIdeas[i];
				i++;
			} else {
				numbers[k] = helper[j];
				ideas[k] = helperIdeas[j];
				j++;
			}
			k++;
		}
		// Copy the rest of the left side of the array into the target array
		while (i <= middle) {
			numbers[k] = helper[i];
			ideas[k] = helperIdeas[i];
			k++;
			i++;
		}

	}
}