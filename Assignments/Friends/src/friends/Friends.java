package friends;

import java.util.ArrayList;

/** Some helpful ArrayList functions that are defined in the ArrayList class in Java, so I don't have to tab back out to the website with these functions listed
 * contains​(Object o): Returns true if this list contains the specified element.
 * get​(int index): Returns the element at the specified position in this list.
 * set​(int index, E element): Replaces the element at the specified position in this list with the specified element.
 * size​(): Returns the number of elements in this list.
 * isEmpty​(): Returns true if this list contains no elements.
 * void add(int index, Object element): This method is used to insert a specific element at a specific position index in a list.
 * remove​(int index): Removes the element at the specified position in this list.
 * remove​(Object o): Removes the first occurrence of the specified element from this list, if it is present.
 */

import structures.Queue;
import structures.Stack;

public class Friends {

	// I'm tired of constantly having to confuse myself with the member/map category of graphs, so I made these for my own convenience

	private static String getName(Graph g, int index) {
		return g.members[index].name;
	}

	private static int getIndex(Graph g, Person person) {
		return g.map.get(person.name);
	}

	private static Person getPerson(Graph g, int index) {
		return g.members[index];
	}

	private static int getIndexOfName(Graph g, String name) {
		int dude = g.map.get(name);
		Person Dude = g.members[dude];
		return getIndex(g, Dude);
	}

	public static ArrayList<String> shortestChain(Graph g, String p1, String p2) {

		Queue<ArrayList<String>> QueueOfChains = new Queue<>();
		Queue<Person> QueueOfPeople = new Queue<>();

		int length = g.members.length;

		if (g.members.length == 0 || g == null || p1 == null || p2 == null) { //in case graph has nothing
			return null;
		}

		if (g.members.length == 1) {
			return null; // If there's one dude in the graph, you can't connect to anything whatsoever cause there's nothing else there
		} else if (g.members.length == 2) { // in case graph has two terms, and if they are friends they will return that chain, if not it means they are islands, so return null
			if (getPerson(g, getIndexOfName(g, p1)).first != null && getPerson(g, getIndexOfName(g, p2)).first != null) {
				ArrayList<String> TwoMemberChain = new ArrayList<>();
				TwoMemberChain.add(getPerson(g, getIndexOfName(g, p1)).name);
				TwoMemberChain.add(getPerson(g, getIndexOfName(g, p2)).name);
				return TwoMemberChain;
			} else {
				return null;
			}
		} else if (g.members.length > 2) {

			ArrayList<String> MainChain = new ArrayList<>();
			QueueOfPeople.enqueue(getPerson(g, getIndexOfName(g, p1)));
			MainChain.add(getName(g, getIndexOfName(g, p1)));
			QueueOfChains.enqueue(MainChain);

			boolean[] checkednodes = new boolean[length]; // define whether the person was visited

			while (QueueOfPeople.isEmpty() == false) {
				Person FirstInQueueOfPeople = QueueOfPeople.dequeue();
				ArrayList<String> Path = QueueOfChains.dequeue();

				Friend OfFirstInQueue = g.members[g.map.get(FirstInQueueOfPeople.name)].first;

				while (OfFirstInQueue != null) {

					checkednodes[g.map.get(FirstInQueueOfPeople.name)] = true;   // fnum and their position in the graph is the same value for the same friend/person

					// Go through the linked list of the first person, the linked list represents all the dude's friends
					if (checkednodes[OfFirstInQueue.fnum] == false) {
						ArrayList<String> PathToAdd = new ArrayList<>(Path); // instead of setting PathToAdd to Path, we make a duplicate of path instead
						// directly copying path seems to join in all paths, and then resulting chains are disrupted
						PathToAdd.add(g.members[OfFirstInQueue.fnum].name);

						if (g.members[OfFirstInQueue.fnum].name.compareTo(p2) == 0) { // the compareTo checks if the name is the same, 0 means yes
							if (PathToAdd != null && PathToAdd.size() > 0) {
								return PathToAdd;
							}
						}

						if (g.members[OfFirstInQueue.fnum].name.compareTo(p2) != 0) { // the compareTo checks if the name is the same, 0 means yes
							QueueOfPeople.enqueue(g.members[OfFirstInQueue.fnum]);
							QueueOfChains.enqueue(PathToAdd);
						}
						OfFirstInQueue = OfFirstInQueue.next;
					} else if (checkednodes[OfFirstInQueue.fnum] == true) {
						OfFirstInQueue = OfFirstInQueue.next;
					} else {
						OfFirstInQueue = OfFirstInQueue.next;
					}

					// proceed to the next link up until we find a match, or we don't so DFS will put this path at the end of the queue, and we start from the person
					// we just found in this list with their fnum, and that is in the queue instead and we go on that path now, etc. etc.

				}
			}
		} else {
			return null;
		}
		return null;
	}

	// Iterative DFS Implementation using a Stack Data Structure

	// Instead of using recursion, I'm gonna use the STACK approach below since it's cleaner and doesn't fail test cases

	public static ArrayList<ArrayList<String>> cliques(Graph g, String school) {

		Stack<Person> StackOfPeople = new Stack<>();
		ArrayList<ArrayList<String>> ArrayOfCliques = new ArrayList<>();
		int length = g.members.length;

		/** These checks avoid the rest of the algorithm in cases of specific graph length, or in cases of nullity
		 *
		 **/

		if (g.members.length == 0 || g == null || school == null) { //in case graph has nothing
			return null;
		}

		if (g.members.length == 1 && getPerson(g, 0).school != null && getPerson(g, 0).school.compareTo(school) == 0 && school != null) {
			ArrayList<String> LonelyBoi = new ArrayList<>(); // in case graph has one term
			Person ToAdd = getPerson(g, 0);
			LonelyBoi.add(ToAdd.name);
			ArrayOfCliques.add(LonelyBoi);
			return ArrayOfCliques;
		} else if (g.members.length == 2 && school != null) { // in case graph has two terms, and if they are friends they will return that chain, if not it means they are islands, so return null
			if (getPerson(g, 0).school != null && getPerson(g, 0).school.compareTo(school) == 0) {
				ArrayList<String> MemberChain = new ArrayList<>();
				Person ToAdd = getPerson(g, 0);
				MemberChain.add(ToAdd.name);
				if (ToAdd.first != null && getPerson(g, ToAdd.first.fnum).school != null && getPerson(g, ToAdd.first.fnum).school.compareTo(school) == 0) {
					Person ToAdd2 = getPerson(g, ToAdd.first.fnum);
					MemberChain.add(ToAdd2.name);
					ArrayOfCliques.add(MemberChain);
				} else if (ToAdd.first == null && g.members[1].school != null && g.members[1].school.compareTo(school) == 0) {
					ArrayList<String> LonelyBoiChain = new ArrayList<>();
					ArrayOfCliques.add(LonelyBoiChain);
				}
				return ArrayOfCliques;
			} else if (getPerson(g, 1).school != null && getPerson(g, 1).school.compareTo(school) == 0) {
				ArrayList<String> LonelyBoi = new ArrayList<>();
				Person ToAdd = getPerson(g, 1);
				LonelyBoi.add(ToAdd.name);
				ArrayOfCliques.add(LonelyBoi);
				return ArrayOfCliques;
			} else {
				return null;
			}
		}

		boolean[] checkednodes = new boolean[length];

		int counter = 0;

		// Iterative DFS Implementation using a Stack Data Structure

		int nullitycheck = 0;

		for (counter = 0; counter < length; counter++) {

			nullitycheck = nullitycheck + 1;

			ArrayList<String> PeopleInClique = new ArrayList<>();

			Person FirstInGraph = g.members[counter];

			if (FirstInGraph.student == false || checkednodes[counter] == true || FirstInGraph.school.compareTo(school) != 0) {
				continue;
			}
			if (FirstInGraph.student == true && checkednodes[counter] == false && FirstInGraph.school.compareTo(school) == 0) {
				//System.out.println("Person WEE push is: " + FirstInGraph.name);
				StackOfPeople.push(FirstInGraph);
			}

//			if(StackOfPeople.isEmpty() == false) {
//				System.out.println("The person in the stack is: " + StackOfPeople.peek().name);
//			}

			while (StackOfPeople.isEmpty() == false) {

				Person PersonToPop = StackOfPeople.pop();
				//System.out.println("Person popped is: " + PersonToPop.name);

				int thecounter = g.map.get(PersonToPop.name);

				if (PersonToPop.student == true && PersonToPop.school.compareTo(school) == 0 && checkednodes[thecounter] == false) {
					PeopleInClique.add(PersonToPop.name);
					checkednodes[thecounter] = true;

					if (PersonToPop.first != null && checkednodes[PersonToPop.first.fnum] == false) {
						int nextcounter = PersonToPop.first.fnum;
						Person PersonToPush1 = g.members[nextcounter];
						if (PersonToPush1.student == true && PersonToPush1.school.compareTo(school) == 0 && checkednodes[nextcounter] == false) {
							//System.out.println("Push Scenario 1, we push: " + PersonToPush1.name);
							StackOfPeople.push(PersonToPush1);
							//System.out.println("PEEK CHECKER: " + StackOfPeople.peek().name);
						}
					}
					if (PersonToPop.first.next != null && checkednodes[PersonToPop.first.next.fnum] == false) {
						int nextcounter2 = PersonToPop.first.next.fnum;
						Person PersonToPush2 = g.members[nextcounter2];
						if (PersonToPush2.student == true && PersonToPush2.school.compareTo(school) == 0 && checkednodes[nextcounter2] == false) {
							//System.out.println("Push Scenario 2, we push: " + PersonToPush2.name);
							StackOfPeople.push(PersonToPush2);
						} else {
							continue;
						}
					} else {
						continue;
					}
				}
			}

			if (PeopleInClique.size() != 0) {
				ArrayOfCliques.add(PeopleInClique);
			}
		}

		// Names of all cliques, in any order, returned in an array list (as stated in the assignment, sorting isn't required)

		if (nullitycheck == length) {
			if (ArrayOfCliques != null || ArrayOfCliques.size() > 0) {
				// Names of all connectors, in any order, returned in an array list (as stated in the assignment, sorting isn't required)
				return ArrayOfCliques;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	// Recursive DFS Implementation using arrays

	private static ArrayList<String> ConnectorsViaRecursion(ArrayList<String> GraphConnectors, ArrayList<String> GraphConnected, Graph g, Person FirstInGraph,
															boolean[] checkednodes, int val1, int val2, int[] dfsnumv, int[] backnumv, int length, boolean FirstDude, int counter) {

		if (length == 0) {
			return null;
		} else if (length == 1) {
			GraphConnectors.add(getName(g, 0));
			return GraphConnectors;
		} else if (length == 2) {
			Person member = getPerson(g, 0);
			GraphConnectors.add(member.name);
			int num = member.first.fnum;
			Person member2 = g.members[num];
			GraphConnectors.add(member2.name);
			return GraphConnectors;
		} else if (length > 2) {

			dfsnumv[getIndex(g, FirstInGraph)] = val1;
			backnumv[getIndex(g, FirstInGraph)] = val2;

			checkednodes[getIndex(g, FirstInGraph)] = true;

			Friend OfFirstInGraph = FirstInGraph.first;

			// dfsnum(v): This is the dfs number, assigned when a vertex is visited, dealt out in increasing order.
			// back(v): This is a number that is initially assigned when a vertex is visited, and is equal to dfsnum, but can be changed later as follows:
			// When the DFS backs up from a neighbor, w, to v, if dfsnum(v) > back(w), then back(v) is set to min(back(v),back(w))
			// If a neighbor, w, is already visited then back(v) is set to min(back(v),dfsnum(w))

			// Posting this here so I don't have to keep looking back to the webpage

			while (counter < length) {

				while (OfFirstInGraph != null) { // keeps checking the linked list of friends of the person

					if (checkednodes[OfFirstInGraph.fnum] == false) { // if this passes, the dude with the specific fnum wasn't checked

						Person OfFirstInGraphPerson = getPerson(g, OfFirstInGraph.fnum); // Store this for recursive call
						int FriendIndex = OfFirstInGraph.fnum;

						val1 = val1 + 1; // This is the number that increments dfsnumv
						val2 = val2 + 1; // This is the number that increments backnumv

						GraphConnectors = ConnectorsViaRecursion(GraphConnectors, GraphConnected, g, OfFirstInGraphPerson,
								checkednodes, val1, val2, dfsnumv, backnumv, length, false, counter); // It aint the first dude so we put false here

						if (dfsnumv[getIndex(g, FirstInGraph)] <= backnumv[FriendIndex]) {
							if ((GraphConnectors.contains(FirstInGraph.name) == false && FirstDude == false)) {
								GraphConnectors.add(FirstInGraph.name); // This operates on either of the two conditions 1) If we don't contain the element in the main arraylist, 2) If it ain't the first dude, else we keep going
							} else if (GraphConnectors.contains(FirstInGraph.name) == false && GraphConnected.contains(FirstInGraph.name) == true) {
								// This also operates on two conditions 1) Same condition as before 2) This time, if the previously connected person is contained in the array, if not then this keeps going, the else statement is initiated
								GraphConnectors.add(FirstInGraph.name);
							}
						} else { // These are the condition statements from the directions for backnumv and dfsnumv
							if (backnumv[getIndex(g, FirstInGraph)] >= backnumv[FriendIndex]) {
								backnumv[getIndex(g, FirstInGraph)] = backnumv[FriendIndex];
							} else {
								continue;
							}
						}
						GraphConnected.add(FirstInGraph.name);
					} else if (checkednodes[OfFirstInGraph.fnum] == true) {

						int FriendIndex = OfFirstInGraph.fnum;

						if (backnumv[getIndex(g, FirstInGraph)] < dfsnumv[FriendIndex]) {
							backnumv[getIndex(g, FirstInGraph)] = backnumv[getIndex(g, FirstInGraph)];
						} else if (backnumv[getIndex(g, FirstInGraph)] >= dfsnumv[FriendIndex]) {
							backnumv[getIndex(g, FirstInGraph)] = dfsnumv[FriendIndex];
						}
					}
					OfFirstInGraph = OfFirstInGraph.next;
				}
				counter++;
			}
		} // I'm gonna write another condition that makes sure each connector has more than one friend ...

		for (int nextcounter = 0; nextcounter < GraphConnectors.size(); nextcounter++) {
			int SittingIndex = getIndexOfName(g, GraphConnectors.get(nextcounter));
			Person SittingDude = getPerson(g, SittingIndex);
			if (SittingDude.first != null && SittingDude.first.next != null) {
				continue;
			} else if (SittingDude.first != null && SittingDude.first.next == null) {
				GraphConnectors.remove(SittingIndex);
				continue;
			}
		}

		if (GraphConnectors != null || GraphConnectors.size() > 0) {
			return GraphConnectors;
		} else {
			return null;
		}
	}

	public static ArrayList<String> connectors(Graph g) {

		ArrayList<String> GraphConnectors = new ArrayList<>();
		ArrayList<String> GraphConnected = new ArrayList<>(GraphConnectors);
		int length = g.members.length;
		boolean[] checkednodes = new boolean[length];

		if (length == 0) {
			return null;
		} else if (length == 1) {
			GraphConnectors.add(getName(g, 0));
			return GraphConnectors;
		} else if (length == 2) {
			Person member = g.members[g.map.get(0)];
			GraphConnectors.add(member.name);
			int num = member.first.fnum;
			Person member2 = g.members[num];
			GraphConnectors.add(member2.name);
			return GraphConnectors;
		}

		int val1 = 0;
		int val2 = 0;

		// dfsnum(v): This is the dfs number, assigned when a vertex is visited, dealt out in increasing order.
		// back(v): This is a number that is initially assigned when a vertex is visited, and is equal to dfsnum, but can be changed later as follows:
		// When the DFS backs up from a neighbor, w, to v, if dfsnum(v) > back(w), then back(v) is set to min(back(v),back(w))
		// If a neighbor, w, is already visited then back(v) is set to min(back(v),dfsnum(w))

		// Posting this here so I don't have to keep looking back to the webpage

		int[] dfsnumv = new int[length];
		int[] backnumv = new int[length];

		int nullitycheck = 0;

		for (int counter = 0; counter < length; counter++) {

			if (checkednodes[counter] == false) {

				Person FirstInGraph = g.members[counter];

				GraphConnectors = ConnectorsViaRecursion(GraphConnectors, GraphConnected, g, FirstInGraph,
						checkednodes, val1, val2, dfsnumv, backnumv, length, true, counter);

				nullitycheck++;

			} else {
				nullitycheck++;

				continue;
			}
		}
		// System.out.println("Nullity check is: " + nullitycheck);

		if (nullitycheck == length) {
			if (GraphConnectors != null || GraphConnectors.size() > 0) {
				// Names of all connectors, in any order, returned in an array list (as stated in the assignment, sorting isn't required)
				return GraphConnectors;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}
}


	//RECURSIVE VERSION (NOT 100% WORKING)
////	private static void PeopleInCliques (Graph g, int length, String school, ArrayList <String> PeopleInClique,
////										 int counter, boolean[] checkednodes) {
////
////		for(int secondcounter = 0; secondcounter < length; secondcounter++) {
////
////			Person FirstInGraph = g.members[counter];
////			if (checkednodes[secondcounter] == false) {
////				if (FirstInGraph.student == true && FirstInGraph.school.compareTo(school) == 0) { // the compareTo checks if the name is the same, 0 means yes
////					PeopleInClique.add(FirstInGraph.name); // add this dude to the array of strings for peopleinclique
////					checkednodes[secondcounter] = true;
////				}
////			}
////
////			Friend OfFirstInGraph = FirstInGraph.first;
////
////			if (OfFirstInGraph == null) {
////				return;
////			} else {
////				while (OfFirstInGraph != null) {
////					// friends don't have the name category, so we need to call the person with this fnum
////					Person NextCounterPerson = g.members[FirstInGraph.fnum];
////
////					// This nearly does the same thing as the iterative loop that was commented out below, except that it doesn't create new
////					// array of Strings of names (PeopleInClique), it keeps the same clique so long as all the people are in it, and it runs
////					// up until the person does not have any other friends
////
////					// It basically initializes the friend fnum to a person, adds that person, then goes off to that persons friends
////					// It's gonna hit some conditions obviously because some people have already been checked in the checkednodes array of booleans
////
////					if (checkednodes[OfFirstInGraph.fnum] == false) {
////						if (NextCounterPerson.student == true && NextCounterPerson.school.compareTo(school) == 0) { // the compareTo checks if the name is the same, 0 means yes
////							PeopleInCliques(g, length, school, PeopleInClique, OfFirstInGraph.fnum, checkednodes);
////						}
////					}
////					OfFirstInGraph = OfFirstInGraph.next;
////				}
////			}
////		}
////	}
////
////
//


//	RECURSIVE VERSION (NOT 100% WORKING)
//
//
////		ArrayList<ArrayList<String>> ArrayOfCliques = new ArrayList<>(); // Array of cliques, looks like {[a,b,c,d], [e,f],[g,h,i]} etc. this is an array of 3 arrays
////		Queue<Person> QueueOfPeople = new Queue<>();
////
////		int length = g.members.length;
////
////		boolean[] checkednodes = new boolean[length]; // define whether the person was visited, same as in previous method
////
////		int counter = 0;
////
////		for(counter = 0; counter < length; counter++) {
////
////			ArrayList<String> PeopleInClique = new ArrayList<>();
////			Person FirstInGraph = g.members[counter];
////
////			// check whether this guy is even a student, if not we don't care
////			if (FirstInGraph.student == false) {
////				if (checkednodes[counter] = true) {
////					continue;
////				}
////				else {
////					continue;
////				}
////			} else {
////
////				if (checkednodes[counter] == false) {
////					if (FirstInGraph.student == true && FirstInGraph.school.compareTo(school) == 0) { // the compareTo checks if the name is the same, 0 means yes
////						PeopleInCliques(g, length, school, PeopleInClique, counter, checkednodes);
////					}
////				}
////
////				Friend OfFirstInGraph = FirstInGraph.first;
////			}

	//*****Failed iterative attempt below using no data structure (neither stack/queue)

////
////					// For some reason this loop below keeps creating new clique arrays when it should just add to the already made clique, so
////					// to fix this we need a method that will yield ONE clique, then return, then increment counter for the first loop by 1,
////					// and then do this all over again for a new clique without creating duplicates, and also without splitting a clique
////
//////					while (OfFirstInGraph != null) {
//////						int nextcounter = OfFirstInGraph.fnum;
//////						// friends don't have the name category, so we need to call the person with this fnum
//////						Person NextCounterPerson = g.members[nextcounter];
//////
//////						if (checkednodes[nextcounter] == false) {
//////							if (NextCounterPerson.student == true && NextCounterPerson.school.equals(school)) {
//////								PeopleInClique.add(NextCounterPerson.name);
//////								checkednodes[nextcounter] = true;
//////							}
//////						}
//////						OfFirstInGraph = OfFirstInGraph.next;
//////					}

	//******Failed iterative attempt above using no data structure (neither stack/queue)

////
////				if (PeopleInClique.size() != 0) {
////					ArrayOfCliques.add(PeopleInClique);
////				}
////			} // This bracket marks the end of the first loop, meaning we are done with the first clique, time to go onto the next one
////
////		if(ArrayOfCliques == null) {
////			return null;
////		} else {
////			return ArrayOfCliques;
////		}
////
////		}
//

