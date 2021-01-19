/* problem19.cpp
 * Purpose: This code executes one method for solving Problem number 19 on 
 * projecteuler.net.
 * The problem reads as follows:
 * "You are given the following information, but you may prefer to do some 
 * research for yourself.
 * 1 Jan 1900 was a Monday.
 * Thirty days has September,
 * April, June and November.
 * All the rest have thirty-one,
 * Saving February alone,
 * Which has twenty-eight, rain or shine.
 * And on leap years, twenty-nine.
 * A leap year occurs on any year evenly divisible by 4, but not on a century 
 * unless it is divisible by 400. How many Sundays fell on the first of the 
 * month during the twentieth century (1 Jan 1901 to 31 Dec 2000)?
 * Written by: Randy Dang
 */

#include <iostream>
#include <string>
#include <fstream>
#include <stdlib.h>

using namespace std;

//Day class includes variables for each piece of information for a given day.
class Day {
public:
  //Pieces of information for a given day.
  int day_of_week; //0 = Sunday, 1 = Monday, 2 = Tuesday, etc.
  int month; //1 = January, 2 = February, 3 = March, etc.
  int date;
  int year;
  
  //Constructor
  Day();
};

/* Input: None
 * Description: Constructs a Day object setting all instance variables to 
 * default values of 0.
 * Output: The created Day object.
 */
Day::Day() {
  day_of_week = 0;
  month = 0;
  date = 0;
  year = 0;
}

int num_days(int month, int year);
bool is_leap_year(int year);

int main() {
  //Counts the total number of days between January 1st, 1900 and December 31st,
  //2000, inclusive.
  int total_days = 0;
  for (int year = 1900; year <= 2000; year++) {
    if (is_leap_year(year)) {
      total_days += 366;
    } else {
      total_days += 365;
    }
  }
  
  //Declare array of Days storing memory on the heap.
  Day *days = new Day[total_days];
  
  //Set the current day of week to Monday because January 1, 1900 is a Monday.
  int curr_day_of_week = 1;
  
  //Loop through all days between 1900 and 2000, inclusive, and create Day 
  //objects for each day.
  int index = 0;
  for (int year = 1900; year <= 2000; year++) {
    for (int month = 1; month <= 12; month++) {
      for (int date = 1; date <= num_days(month, year); date++) {
        //Set instance variables to appropriate values.
        days[index].day_of_week = curr_day_of_week;
        days[index].month = month;
        days[index].date = date;
        days[index].year = year;
        
        //Update the current day of the week and the index of the Day array.
        curr_day_of_week = (curr_day_of_week + 1) % 7;
        index++;
      }
    }
  }
  
  //Counts the total number of Sundays on the first of the month between 1901 
  //and 2000, inclusive.
  int num_meeting_criteria = 0;
  for (int i = 0; i < total_days; i++) {
    if (days[i].year >= 1901 && days[i].year <= 2000) {
      if (days[i].day_of_week == 0 && days[i].date == 1) {
        num_meeting_criteria++;
      }
    }
  }
  
  //Delete memory allocated to the heap.
  delete [] days;
  
  
  //Prints out the total number of Sundays on the first of the month between 
  //1901 and 2000.
  cout << num_meeting_criteria << endl;
  return 0;
}

/* num_days
 * Input: Integer representing the month (1-12) and year that the user is 
 * trying to find the number of days in.
 * Description: Finds the number of days in the user inputted month and year.
 * Output: The number of days in the inputted month of the inputted year.
 */
int num_days(int month, int year) {
  //April, June, September, and November each have 30 days.
  //February has 29 days if the inputted year is a leap year, 28 days otherwise.
  //All other months have 31 days.
  if (month == 4 || month == 6 || month == 9 || month == 11) {
    return 30;
  } else if (month == 2) {
    if (is_leap_year(year)) {
      return 29;
    } else {
      return 28;
    }
  } else {
    return 31;
  }
}

/* is_leap_year
 * Input: Integer representing the year that the user is trying to determine if 
 * it is a leap year.
 * Description: Determines if the user inputted year is a leap year.
 * Output: True if the inputted year is a leap year, false otherwise.
 */
bool is_leap_year(int year) {
  if (year % 400 == 0) {
    return true;
  } else if (year % 100 == 0) {
    return false;
  } else if (year % 4 == 0) {
    return true;
  }
  return false;
}