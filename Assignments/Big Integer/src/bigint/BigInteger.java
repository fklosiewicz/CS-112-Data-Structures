package bigint;

public class BigInteger {

	boolean negative;
	int numDigits;
	DigitNode front;

	public BigInteger() {
		negative = false;
		numDigits = 0;
		front = null;
	}

	public static BigInteger parse(String integer)
			throws IllegalArgumentException {

		int a = 0;
		int b = 0;
		int length = integer.length();
		char c;

		BigInteger bigint = new BigInteger();

		while (a < integer.length()) {
			c = integer.charAt(a);
			if (c == '-' && a == 0) {
				bigint.negative = true;
				a++;
				continue;
			}
			if (c == '+' && a == 0) {
				a++;
				continue;
			}

			if (c == ' ') {
				if (bigint.numDigits == 0) {
					a++;
					continue;
				}
				else {
					throw new IllegalArgumentException();
				}

			}

			if (Character.isDigit(c)) {
				if(c == '0' && bigint.numDigits == 0) {
					a++;
				}
				else {
					bigint.numDigits = bigint.numDigits + 1;
					b = Character.getNumericValue(c);
					DigitNode newNode = new DigitNode(b,null);
					newNode.next = bigint.front;
					bigint.front = newNode;
					a++;
				}

			}
			else {
				throw new IllegalArgumentException();
			}
		}
		return bigint;
	}

	public static BigInteger add(BigInteger first, BigInteger second) {

		BigInteger firsttemp = first;
		BigInteger secondtemp = second;

		int addnext;
		int remainder = 0;
		int carryover = 0;
		int borrow = 0;
		int temp1;
		int temp2;

		BigInteger sum = new BigInteger();

		if(first.negative == true && second.negative == false) {

			if (first.numDigits > second.numDigits) {
				sum.negative = true;
				addnext = 0;
				while (firsttemp.front != null && secondtemp.front != null) {

					int LastOfFirst = 0;
					if(first.front.digit != 0) {
						DigitNode lastNode1 = firsttemp.front;
						DigitNode next1 = firsttemp.front.next;
						while (next1 != null) {
							lastNode1 = next1;
							next1 = next1.next;
						}
						LastOfFirst = lastNode1.digit;
					}

					int LastOfSecond = 0;
					if(second.front.digit != 0) {
						DigitNode lastNode2 = secondtemp.front;
						DigitNode next2 = secondtemp.front.next;
						while (next2 != null) {
							lastNode2 = next2;
							next2 = next2.next;
						}
						LastOfSecond = lastNode2.digit;
					}

					temp1 = firsttemp.front.digit - borrow;
					temp2 = secondtemp.front.digit;
					borrow = 0;
					if (temp1 < temp2) {
						borrow = 1;
						addnext = (temp1 + 10) - temp2;
						if (LastOfFirst != 1 || addnext != 0) {
							DigitNode newNode = new DigitNode(addnext, null);
							newNode.next = sum.front;
							sum.front = newNode;
						}
					}
					else if (temp1 == temp2) {
						DigitNode newNode = new DigitNode(0, null);
						newNode.next = sum.front;
						sum.front = newNode;
					}
					else {
						DigitNode newNode = new DigitNode(temp1-temp2,null);
						newNode.next = sum.front;
						sum.front = newNode;
					}
					firsttemp.front = firsttemp.front.next;
					secondtemp.front = secondtemp.front.next;
				}
				while (firsttemp.front != null && secondtemp.front == null) {

//					int LastOfFirst = 0;
//					if(first.front.digit != 0) {
//						DigitNode lastNode1 = firsttemp.front;
//						DigitNode next1 = firsttemp.front.next;
//						while (next1 != null) {
//							lastNode1 = next1;
//							next1 = next1.next;
//						}
//						LastOfFirst = lastNode1.digit;
//					}
//
//					int LastOfSecond = 0;
//					if(second.front.digit != 0) {
//						DigitNode lastNode2 = secondtemp.front;
//						DigitNode next2 = secondtemp.front.next;
//						while (next2 != null) {
//							lastNode2 = next2;
//							next2 = next2.next;
//						}
//						LastOfSecond = lastNode2.digit;
//					}

					temp1 = firsttemp.front.digit - borrow;
					borrow = 0;
					if(temp1 < 0) {
						temp1 = 9;
						borrow = 1;
					}
					if(temp1 == 0 && firsttemp.front.next == null) {
						break;
					}
					DigitNode newNode = new DigitNode(temp1, null);
					newNode.next = sum.front;
					sum.front = newNode;
					firsttemp.front = firsttemp.front.next;
				}
			}
		}

		if(firsttemp.negative == false && secondtemp.negative == true) {

			if (firsttemp.numDigits > secondtemp.numDigits) {
				sum.negative = false;
				addnext = 0;
				while (firsttemp.front != null && secondtemp.front != null) {

					int LastOfFirst = 0;
					if(first.front.digit != 0) {
						DigitNode lastNode1 = firsttemp.front;
						DigitNode next1 = firsttemp.front.next;
						while (next1 != null) {
							lastNode1 = next1;
							next1 = next1.next;
						}
						LastOfFirst = lastNode1.digit;
					}

					int LastOfSecond = 0;
					if(second.front.digit != 0) {
						DigitNode lastNode2 = secondtemp.front;
						DigitNode next2 = secondtemp.front.next;
						while (next2 != null) {
							lastNode2 = next2;
							next2 = next2.next;
						}
						LastOfSecond = lastNode2.digit;
					}

					temp1 = firsttemp.front.digit - borrow;
					temp2 = secondtemp.front.digit;
					borrow = 0;
					if (temp1 < temp2) {
						borrow = 1;
						addnext = (temp1 + 10) - temp2;
						if (LastOfFirst != 1 || addnext != 0) {
							DigitNode newNode = new DigitNode(addnext, null);
							newNode.next = sum.front;
							sum.front = newNode;
						}
					}
					else if (temp1 == temp2) {
						DigitNode newNode = new DigitNode(0, null);
						newNode.next = sum.front;
						sum.front = newNode;
					}
					else {
						DigitNode newNode = new DigitNode(temp1-temp2,null);
						newNode.next = sum.front;
						sum.front = newNode;
					}
					firsttemp.front = firsttemp.front.next;
					secondtemp.front = secondtemp.front.next;
				}
				while (firsttemp.front != null && secondtemp.front == null) {

					temp1 = firsttemp.front.digit - borrow;
					borrow = 0;
					if(temp1 < 0) {
						temp1 = 9;
						borrow = 1;
					}
					if(temp1 == 0 && firsttemp.front.next == null) {
						break;
					}
						DigitNode newNode = new DigitNode(temp1, null);
						newNode.next = sum.front;
						sum.front = newNode;
						firsttemp.front = firsttemp.front.next;
				}
			}
		}

		if(firsttemp.negative == false && secondtemp.negative == true) {

			if (firsttemp.numDigits < secondtemp.numDigits) {
				sum.negative = true;
				addnext = 0;
				while (firsttemp.front != null && secondtemp.front != null) {

					int LastOfFirst = 0;
					if(first.front.digit != 0) {
						DigitNode lastNode1 = firsttemp.front;
						DigitNode next1 = firsttemp.front.next;
						while (next1 != null) {
							lastNode1 = next1;
							next1 = next1.next;
						}
						LastOfFirst = lastNode1.digit;
					}

					int LastOfSecond = 0;
					if(second.front.digit != 0) {
						DigitNode lastNode2 = secondtemp.front;
						DigitNode next2 = secondtemp.front.next;
						while (next2 != null) {
							lastNode2 = next2;
							next2 = next2.next;
						}
						LastOfSecond = lastNode2.digit;
					}

					temp1 = firsttemp.front.digit;
					temp2 = secondtemp.front.digit - borrow;
					borrow = 0;
					if (temp1 < temp2) {
						borrow = 1;
						addnext = (temp2 + 10) - temp1;
						if(LastOfSecond != 1 || addnext != 0) {
							DigitNode newNode = new DigitNode(addnext, null);
							newNode.next = sum.front;
							sum.front = newNode;
						}
					}
					else if (temp1 == temp2) {
						DigitNode newNode = new DigitNode(0, null);
						newNode.next = sum.front;
						sum.front = newNode;
					}
					else {
						DigitNode newNode = new DigitNode(Math.abs(temp2-temp1),null);
						newNode.next = sum.front;
						sum.front = newNode;
					}
					firsttemp.front = firsttemp.front.next;
					secondtemp.front = secondtemp.front.next;
				}
				while (firsttemp.front == null && secondtemp.front != null) {

//					int LastOfFirst = 0;
//					if(first.front.digit != 0) {
//						DigitNode lastNode1 = firsttemp.front;
//						DigitNode next1 = firsttemp.front.next;
//						while (next1 != null) {
//							lastNode1 = next1;
//							next1 = next1.next;
//						}
//						LastOfFirst = lastNode1.digit;
//					}
//
//					int LastOfSecond = 0;
//					if(second.front.digit != 0) {
//						DigitNode lastNode2 = secondtemp.front;
//						DigitNode next2 = secondtemp.front.next;
//						while (next2 != null) {
//							lastNode2 = next2;
//							next2 = next2.next;
//						}
//						LastOfSecond = lastNode2.digit;
//					}

					temp2 = secondtemp.front.digit - borrow;
					borrow = 0;
					if (temp2 < 0) {
						temp2 = 9;
						borrow = 1;
					}
					if(temp2 == 0 && secondtemp.front.next == null) {
						break;
					}
					DigitNode newNode = new DigitNode(temp2, null);
					newNode.next = sum.front;
					sum.front = newNode;
					secondtemp.front = secondtemp.front.next;
				}
			}
			if (firsttemp.numDigits == secondtemp.numDigits) {
				addnext = 0;
				while (firsttemp.front != null && secondtemp.front != null) {

					int LastOfFirst = 0;
					if(first.front.digit != 0) {
						DigitNode lastNode1 = firsttemp.front;
						DigitNode next1 = firsttemp.front.next;
						while (next1 != null) {
							lastNode1 = next1;
							next1 = next1.next;
						}
						LastOfFirst = lastNode1.digit;
					}

					int LastOfSecond = 0;
					if(second.front.digit != 0) {
						DigitNode lastNode2 = secondtemp.front;
						DigitNode next2 = secondtemp.front.next;
						while (next2 != null) {
							lastNode2 = next2;
							next2 = next2.next;
						}
						LastOfSecond = lastNode2.digit;
					}

					if((firsttemp.front.digit - secondtemp.front.digit < 0)) {
						sum.negative = true;
					}
					// 2nd neg
					temp1 = firsttemp.front.digit;
					temp2 = secondtemp.front.digit - borrow;
					borrow = 0;
					if (temp2 < temp1) {
						if(firsttemp.front.next != null) {
							borrow = 1;
							addnext = (temp2 + 10) - temp1;
						} else {
							addnext = temp2 - temp1;
						}
						if((LastOfSecond - LastOfFirst) != 0 || addnext != 0) {
							DigitNode newNode = new DigitNode(Math.abs(addnext), null);
							newNode.next = sum.front;
							sum.front = newNode;
						}
					}
					else if (temp1 == temp2) {
						if((LastOfSecond - LastOfFirst) != 0 || ((temp1 - temp2) == 0)) {
							DigitNode newNode = new DigitNode(0, null);
							newNode.next = sum.front;
							sum.front = newNode;
						}
					}
					else {
						if ((LastOfSecond - LastOfFirst) != 0 || ((temp1 - temp2) != 0)) {
							if ((temp1-temp2) < 0) {
								sum.negative = true;
							}
							DigitNode newNode = new DigitNode(Math.abs(temp1 - temp2), null);
							newNode.next = sum.front;
							sum.front = newNode;
						}
					}
					firsttemp.front = firsttemp.front.next;
					secondtemp.front = secondtemp.front.next;
					if (firsttemp.front == null && secondtemp.front == null) {
						if ((temp1-temp2) < 0) {
							sum.negative = true;
						}
					}
				}
			}
		}

		if(firsttemp.negative == true && secondtemp.negative == false) {

			if (firsttemp.numDigits < secondtemp.numDigits) {
				sum.negative = false;
				addnext = 0;
				while (firsttemp.front != null && secondtemp.front != null) {

					int LastOfFirst = 0;
					if(first.front.digit != 0) {
						DigitNode lastNode1 = firsttemp.front;
						DigitNode next1 = firsttemp.front.next;
						while (next1 != null) {
							lastNode1 = next1;
							next1 = next1.next;
						}
						LastOfFirst = lastNode1.digit;
					}

					int LastOfSecond = 0;
					if(second.front.digit != 0) {
						DigitNode lastNode2 = secondtemp.front;
						DigitNode next2 = secondtemp.front.next;
						while (next2 != null) {
							lastNode2 = next2;
							next2 = next2.next;
						}
						LastOfSecond = lastNode2.digit;
					}

					temp1 = firsttemp.front.digit;
					temp2 = secondtemp.front.digit - borrow;
					borrow = 0;
					if (temp1 > temp2) {
						borrow = 1;
						addnext = (temp2 + 10) - temp1;
						if(LastOfSecond != 1 || addnext != 0) {
							DigitNode newNode = new DigitNode(addnext, null);
							newNode.next = sum.front;
							sum.front = newNode;
						}
					}
					else if (temp1 == temp2) {
						DigitNode newNode = new DigitNode(0, null);
						newNode.next = sum.front;
						sum.front = newNode;
					}
					else {
						DigitNode newNode = new DigitNode(temp2-temp1,null);
						newNode.next = sum.front;
						sum.front = newNode;
					}
					firsttemp.front = firsttemp.front.next;
					secondtemp.front = secondtemp.front.next;
				}
				while (firsttemp.front == null && secondtemp.front != null) {

//					int LastOfFirst = 0;
//					if(first.front.digit != 0) {
//						DigitNode lastNode1 = firsttemp.front;
//						DigitNode next1 = firsttemp.front.next;
//						while (next1 != null) {
//							lastNode1 = next1;
//							next1 = next1.next;
//						}
//						LastOfFirst = lastNode1.digit;
//					}
//
//					int LastOfSecond = 0;
//					if(second.front.digit != 0) {
//						DigitNode lastNode2 = secondtemp.front;
//						DigitNode next2 = secondtemp.front.next;
//						while (next2 != null) {
//							lastNode2 = next2;
//							next2 = next2.next;
//						}
//						LastOfSecond = lastNode2.digit;
//					}

					temp2 = secondtemp.front.digit - borrow;
					borrow = 0;
					if (temp2 < 0) {
						temp2 = 9;
						borrow = 1;
					}
					if(temp2 == 0 && secondtemp.front.next == null) {
						break;
					}
					DigitNode newNode = new DigitNode(temp2, null);
					newNode.next = sum.front;
					sum.front = newNode;
					secondtemp.front = secondtemp.front.next;
				}
			}
			if (firsttemp.numDigits == secondtemp.numDigits) {
				addnext = 0;
				while (firsttemp.front != null && secondtemp.front != null) {

					int LastOfFirst = 0;
					if(first.front.digit != 0) {
						DigitNode lastNode1 = firsttemp.front;
						DigitNode next1 = firsttemp.front.next;
						while (next1 != null) {
							lastNode1 = next1;
							next1 = next1.next;
						}
						LastOfFirst = lastNode1.digit;
					}

					int LastOfSecond = 0;
					if(second.front.digit != 0) {
						DigitNode lastNode2 = secondtemp.front;
						DigitNode next2 = secondtemp.front.next;
						while (next2 != null) {
							lastNode2 = next2;
							next2 = next2.next;
						}
						LastOfSecond = lastNode2.digit;
					}
// 1st neg
					if((secondtemp.front.digit - firsttemp.front.digit < 0)) {
							sum.negative = true;
					}
					temp1 = firsttemp.front.digit - borrow;
					temp2 = secondtemp.front.digit;
					borrow = 0;
					if (temp1 < temp2) {
						if(firsttemp.front.next != null) {
							borrow = 1;
							addnext = (temp1 + 10) - temp2;
						} else {
							addnext = temp1 - temp2;
						}
						if((LastOfSecond - LastOfFirst) != 0|| addnext != 0) {
							DigitNode newNode = new DigitNode(Math.abs(addnext), null);
							newNode.next = sum.front;
							sum.front = newNode;
						}
					}
					else if (temp1 == temp2) {
						if((LastOfSecond - LastOfFirst)!= 0 || ((temp2 - temp1) == 0)) {
							DigitNode newNode = new DigitNode(0, null);
							newNode.next = sum.front;
							sum.front = newNode;
						}
					}
					else {
						if ((LastOfSecond - LastOfFirst) != 0 || ((temp2 - temp1) != 0)) {
							if ((temp2-temp1) < 0) {
								sum.negative = true;
							}
							DigitNode newNode = new DigitNode(Math.abs(temp2 - temp1), null);
							newNode.next = sum.front;
							sum.front = newNode;
						}
					}
					firsttemp.front = firsttemp.front.next;
					secondtemp.front = secondtemp.front.next;
					if (firsttemp.front == null && secondtemp.front == null) {
						if ((temp2-temp1) < 0) {
							sum.negative = true;
						}
					}
				}
			}
		}

		if((firsttemp.negative == true && secondtemp.negative == true) || (firsttemp.negative == false && secondtemp.negative == false)) {
			if((firsttemp.negative == true && secondtemp.negative == true)) {
				sum.negative = true;
			}
			else {
				sum.negative = false;
			}
			while (firsttemp.front != null && secondtemp.front != null) {
				addnext = 0;
				addnext = firsttemp.front.digit + secondtemp.front.digit + carryover;
				if (addnext > 9) {
					remainder = addnext % 10;
					carryover = addnext / 10;
					DigitNode newNode = new DigitNode(remainder, null);
					newNode.next = sum.front;
					sum.front = newNode;
				} else {
					DigitNode newNode = new DigitNode(addnext, null);
					newNode.next = sum.front;
					sum.front = newNode;
					carryover = 0;
				}
				firsttemp.front = firsttemp.front.next;
				secondtemp.front = secondtemp.front.next;
				if((firsttemp.front == null && secondtemp.front == null) && (firsttemp.numDigits == secondtemp.numDigits) && (carryover != 0)) {
					DigitNode newNode = new DigitNode(carryover,null);
					newNode.next = sum.front;
					sum.front = newNode;
				}
			}
			while (firsttemp.front == null && secondtemp.front != null) {
				addnext = 0;
				addnext = secondtemp.front.digit + carryover;
				if (addnext > 9) {
					remainder = addnext % 10;
					carryover = addnext / 10;
					DigitNode newNode = new DigitNode(remainder, null);
					newNode.next = sum.front;
					sum.front = newNode;
				} else {
					DigitNode newNode = new DigitNode(addnext, null);
					newNode.next = sum.front;
					sum.front = newNode;
					carryover = 0;
				}
				secondtemp.front = secondtemp.front.next;
				if((firsttemp.front == null && secondtemp.front == null) && (carryover != 0)) {
					DigitNode newNode = new DigitNode(carryover,null);
					newNode.next = sum.front;
					sum.front = newNode;
				}
			}

			while (secondtemp.front == null && firsttemp.front != null) {
				addnext = 0;
				addnext = firsttemp.front.digit + carryover;
				if (addnext > 9) {
					remainder = addnext % 10;
					carryover = addnext / 10;
					DigitNode newNode = new DigitNode(remainder, null);
					newNode.next = sum.front;
					sum.front = newNode;
				} else {
					DigitNode newNode = new DigitNode(addnext, null);
					newNode.next = sum.front;
					sum.front = newNode;
					carryover = 0;
				}
				firsttemp.front = firsttemp.front.next;
				if((firsttemp.front == null && secondtemp.front == null) && (carryover != 0)) {
					DigitNode newNode = new DigitNode(carryover,null);
					newNode.next = sum.front;
					sum.front = newNode;
				}
			}
		}

		BigInteger reversedSum = new BigInteger();
		reversedSum.negative = sum.negative;
		while (sum.front != null) {
			DigitNode newNode = new DigitNode(sum.front.digit, null);
			newNode.next = reversedSum.front;
			reversedSum.front = newNode;
			sum.front = sum.front.next;
			reversedSum.numDigits = reversedSum.numDigits + 1;
		}
		return reversedSum;
		}


	public static BigInteger multiply(BigInteger first, BigInteger second) {

		BigInteger dummy1 = first;
		BigInteger dummy2 = second;
		BigInteger product = BigInteger.parse("0");
		BigInteger sum = new BigInteger();
		BigInteger zero = BigInteger.parse("0");
		dummy2.negative = zero.negative;
		BigInteger negone = BigInteger.parse("-1");


		if (dummy1 == zero || dummy2 == zero) {
			return zero;
		} else {
			while (dummy2 != zero) {
				sum = BigInteger.add(product, dummy1);
				dummy2 = BigInteger.add(dummy2, negone);
				product = sum;
			}
		}
		return product;
	}


	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		if (front == null) {
			return "0";
		}
		String retval = front.digit + "";
		for (DigitNode curr = front.next; curr != null; curr = curr.next) {
				retval = curr.digit + retval;
		}
		
		if (negative) {
			retval = '-' + retval;
		}
		return retval;
	}
}
