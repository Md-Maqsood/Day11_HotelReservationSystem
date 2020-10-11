package com.BridgeLabs.hotelReservationSystem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class HotelReservation {
	private static final Logger logger = LogManager.getLogger(HotelReservation.class);
	static Scanner sc = new Scanner(System.in);
	public List<Hotel> hotels;
	private static final Pattern DAY_PATTERN = Pattern.compile("\\([a-z]{3,4}\\)");
	private static final List<String> WEEKENDS = Arrays.asList(new String[] { "(sat)", "(sun)" });

	public HotelReservation() {
		this.hotels = new ArrayList<Hotel>();
	}

	/**
	 *uc5 
	 */
	public void addHotels() {
		do {
			logger.debug(
					"Enter the hotel details in given order -\nName:\nRating:\nWeekday Rate for Regular Customer:\nWeekend Rate for Regular Customer:");
			hotels.add(new Hotel(sc.nextLine(), Integer.parseInt(sc.nextLine()), Integer.parseInt(sc.nextLine()), Integer.parseInt(sc.nextLine())));
			logger.debug("Enter 1 to add another hotel, else enter 0: ");
		} while (sc.nextLine().equals("1"));
	}

	/**
	 * uc4
	 */
	public void getCheapestHotel() {
		logger.debug("Enter the date range in format <date1>, <date2>, <date3>\nEg.:  16Mar2020(mon), 17Mar2020(tues), 18Mar2020(wed)");
		String customerInput = sc.nextLine();
		Matcher dayMatcher = DAY_PATTERN.matcher(customerInput);
		List<String> daysList = new ArrayList<String>();
		while (dayMatcher.find()) {
			daysList.add(dayMatcher.group());
		}
		int numWeekends = (int) daysList.stream().filter(day -> WEEKENDS.contains(day)).count();
		int numWeekdays = daysList.size() - numWeekends;
		Map<Hotel, Integer> hotelToTotalRateMap = hotels.stream().collect(Collectors.toMap(hotel -> hotel,
				hotel -> hotel.getRegularWeekendRate() * numWeekends + hotel.getRegularWeekdayRate() * numWeekdays));
		Hotel cheapestHotel = hotelToTotalRateMap.keySet().stream()
				.min((n1, n2) -> hotelToTotalRateMap.get(n1) - hotelToTotalRateMap.get(n2)).orElse(null);
		logger.debug(cheapestHotel.getName() + ", Total Rates: $" + hotelToTotalRateMap.get(cheapestHotel));
	}

	public static void main(String[] args) {
		HotelReservation hotelReservation = new HotelReservation();
		hotelReservation.addHotels();
		hotelReservation.hotels.forEach(hotel->logger.debug(hotel));
	}
}

class Hotel {
	private String name;
	private int regularWeekdayRate;
	private int regularWeekendRate;
	private int rating;
	
	public Hotel(String name, int regularWeekdayRate, int regularWeekendRate, int rating) {
		super();
		this.name = name;
		this.regularWeekdayRate = regularWeekdayRate;
		this.regularWeekendRate = regularWeekendRate;
		this.rating = rating;
	}

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getRegularWeekdayRate() {
		return regularWeekdayRate;
	}

	public void setRegularWeekdayRate(int regularWeekdayRate) {
		this.regularWeekdayRate = regularWeekdayRate;
	}

	public int getRegularWeekendRate() {
		return regularWeekendRate;
	}

	public void setRegularWeekendRate(int regularWeekendRate) {
		this.regularWeekendRate = regularWeekendRate;
	}

	@Override
	public String toString() {
		return "Hotel [name=" + name + ", regularWeekdayRate=" + regularWeekdayRate + ", regularWeekendRate="
				+ regularWeekendRate + ", rating=" + rating + "]";
	}
}