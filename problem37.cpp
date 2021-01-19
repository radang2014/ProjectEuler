/* problem37.cpp
 * Purpose: This code executes one method for solving Problem number 37 on 
 * projecteuler.net.
 * The problem reads as follows:
 * "The number 3797 has an interesting property. Being prime itself, it is 
 * possible to continuously remove digits from left to right, and remain prime 
 * at each stage: 3797, 797, 97, and 7. Similarly we can work from right to 
 * left: 3797, 379, 37, and 3.
 * Find the sum of the only eleven primes that are both truncatable from left 
 * to right and right to left.
 * NOTE: 2, 3, 5, and 7 are not considered to be truncatable primes."
 * Written by: Randy Dang
 */
#include <iostream>
#include <string>
#include <cmath>
#include <stdlib.h>
#include <fstream>

using namespace std;

bool is_prime(int n);
bool trunc_left(int n);
bool trunc_right(int n);

int main() {
  //Add all primes that are trunctable in both directions.
  int sum = 0;
  
  //Start at 11 because single digit numbers are not trunctable.
  //Only need to check odd numbers because even numbers above 2 are not prime.
  for (int i = 11; i < 1000000; i += 2) {
    if (trunc_left(i) && trunc_right(i)) {
      sum += i;
    }
  }
  
  //Print out the sum.
  cout << sum << endl;
  return 0;
}

/* is_prime 
 * Input: An integer that should be determined whether or not it is prime.
 * Description: Checks if the inputted integer is prime.
 * Output: True if the inputted integer is prime, false otherwise.
 */
bool is_prime(int n) {
  //Numbers below two are not prime.
  if (n < 2) {
    return false;
  }
  
  //Check for integral factors between 2 and the square root of the inputted 
  //integer. If one exists, the number is not prime.
  for (int i = 2; i <= (int)sqrt(n); i++) {
    if (n % i == 0) {
      return false;
    }
  }
  
  //If there is no integral factor besides 1 and the number itself, the number 
  //is prime.
  return true;
}

/* trunc_left
 * Input: An integer that is to be determined whether it is trunctable from 
 * left to right. This integer should be composed of at least 2 digits.
 * Description: Determines whether the inputted integer is a prime that is 
 * trunctable from left to right.
 * Output: True if the inputted integer is trunctable from left to right, 
 * false otherwise.
 */
bool trunc_left(int n) {
  //If the inputted integer is not prime, it is not a trunctable prime.
  if (!is_prime(n)) {
    return false;
  }
  
  //Determine the leftmost digit and the number of times n is divided by 10 
  //before getting the leftmost digit.
  int num_divide = 0;
  int left_digit = n;
  while (left_digit >= 10) {
    left_digit /= 10;
    num_divide++;
  }
  
  //Determine the number to be subtracted from n to truncate the leftmost digit.
  int difference = left_digit * pow(10, num_divide);
  
  //If n is less than 10 and is a prime, it is trunctable from left to right.
  //Note: This condition would never be true on the first time this function 
  //is called.
  if (n < 10) {
    return true;
  }
  
  //Remove the leftmost digit and determine if the result is trunctable from
  //left to right.
  return trunc_left(n - difference);
}

/* trunc_right
 * Input: An integer that is to be determined whether it is trunctable from 
 * right to left. This integer should be composed of at least 2 digits.
 * Description: Determines whether the inputted integer is a prime that is 
 * trunctable from right to left.
 * Output: True if the inputted integer is trunctable from right to left, 
 * false otherwise.
 */
bool trunc_right(int n) {
  //If the inputted integer is not prime, it is not a trunctable prime.
  if (!is_prime(n)) {
    return false;
  }
  
  //If n is less than 10 and is a prime, it is trunctable from right to left.
  //Note: This condition would never be true on the first time this function 
  //is called.
  if (n < 10) {
    return true;
  }
  
  //Remove the rightmost digit and determine if the result is trunctable from
  //left to right.
  return trunc_right(n / 10);
}