package trie;

import java.util.ArrayList;

public class Trie {

	private Trie() { }

	public static TrieNode buildTrie(String[] allWords) {

		TrieNode root = new TrieNode(null, null, null);

		int length = allWords.length;

		if (length == 1) {
			int iarrayi = allWords[0].length() - 1;
			Indexes FirstWord = new Indexes(0, (short) 0, (short) iarrayi);
			root.firstChild = new TrieNode(FirstWord, null, null);
			return root;

		} else if (length == 0) {
			return root;

		} else {

			int iarrayi = (allWords[0].length() - 1);
			Indexes RootWord = new Indexes(0, (short) 0, (short) iarrayi);
			root.firstChild = new TrieNode(RootWord, null, null);

			TrieNode CurrentNode = root.firstChild;
			TrieNode PreviousNode = root.firstChild;

			int ArrayIndex = 1;
			int CurrentStart = 0;
			int CurrentEnd = 0;
			int CurrentIndex = 0;
			int concurrency;
			int fconcurrency;

			while(ArrayIndex < length) {

				fconcurrency = 0;


				String wordinsertion = allWords[ArrayIndex];

				while (CurrentNode != null) {

					Indexes CurrentNodexes = CurrentNode.substr;

					CurrentStart = CurrentNodexes.startIndex;
					CurrentEnd = CurrentNodexes.endIndex;
					CurrentIndex = CurrentNodexes.wordIndex;

					// System.out.println("For CurrentNode ~~~~ " + "Word Index is: " + CurrentNode.substr.wordIndex +  ", Start Index is: " + CurrentNode.substr.startIndex + ", EndIndex is: " + CurrentNode.substr.endIndex);

					concurrency = 0;
					while(concurrency < wordinsertion.substring(CurrentStart).length() && concurrency < allWords[CurrentIndex].substring(CurrentStart, CurrentEnd + 1).length()) {
						if (wordinsertion.substring(CurrentStart).charAt(concurrency) == allWords[CurrentIndex].substring(CurrentStart, CurrentEnd + 1).charAt(concurrency)) {
							concurrency++;
						}
						else {
							break;
						}
					}

					// System.out.println("The concurrency is: " + concurrency);

					fconcurrency += concurrency;
					// System.out.println("Index Concurrency is: " + fconcurrency);

					boolean greater;
					int test = concurrency - 1;
					if (test > (-1)) {
						greater = true;
					} else {
						greater = false;
					}

					if(greater) {
						if(test < CurrentEnd) {
							PreviousNode = CurrentNode;
							break;
						}
						else {
							PreviousNode = CurrentNode.firstChild;
							CurrentNode = PreviousNode;
						}
					}

					if (!greater) {
						PreviousNode = CurrentNode;
						CurrentNode = CurrentNode.sibling;
					}

				}

					if (CurrentNode == null) {

						PreviousNode.sibling = new TrieNode(new Indexes(ArrayIndex, (short) CurrentStart, (short) (wordinsertion.length() - 1)), null, null);

					} else {

						TrieNode RotateChildrenDown = PreviousNode.firstChild;

						Indexes NewParent = new Indexes(CurrentIndex, (short) CurrentStart, (short) (fconcurrency - 1));
						Indexes WordToInsert = new Indexes(ArrayIndex, (short) fconcurrency, (short) (wordinsertion.length() - 1));
						Indexes FirstChildInsertion = new Indexes(CurrentIndex, (short) fconcurrency, (short) CurrentEnd);

						TrieNode FirstChildToInsert = new TrieNode(FirstChildInsertion, null, null);
						PreviousNode.firstChild = FirstChildToInsert;

						TrieNode TreeWordToInsert = new TrieNode(WordToInsert, null, null);
						PreviousNode.firstChild.sibling = TreeWordToInsert;

						PreviousNode.firstChild.firstChild = RotateChildrenDown;

						PreviousNode.substr.startIndex = (short) CurrentStart;
						PreviousNode.substr.endIndex = (short) (fconcurrency - 1);
						PreviousNode.substr.wordIndex = (short) CurrentIndex;


					}

					CurrentNode = root.firstChild;
					PreviousNode = root.firstChild;
					ArrayIndex++;
				}
			}
			return root;
		}


	public static ArrayList<TrieNode> completionList(TrieNode root, String[] allWords, String prefix) {
		ArrayList<TrieNode> Words = new ArrayList<>();
		TrieNode CurrentNode = root;

		int CurrentStart = 0;
		int CurrentEnd = 0;
		int CurrentIndex = 0;
		int concurrency;
		int fconcurrency = 0;

		if(CurrentNode == null) {
			return null;
		}

		if(CurrentNode.firstChild != null && allWords.length == 1) {
			Words.add(CurrentNode.firstChild);
			return Words;
		}

		while(CurrentNode != null) {

			if(CurrentNode.substr == null) {

				CurrentStart = CurrentNode.firstChild.substr.startIndex;
				CurrentEnd = CurrentNode.firstChild.substr.endIndex;
				CurrentIndex = CurrentNode.firstChild.substr.wordIndex;
				CurrentNode = CurrentNode.firstChild;
			}

			CurrentStart = CurrentNode.substr.startIndex;
			CurrentEnd = CurrentNode.substr.endIndex;
			CurrentIndex = CurrentNode.substr.wordIndex;


				concurrency = 0;
				int PrefixLength = prefix.length();
				if(PrefixLength > 0) {
					while (concurrency < prefix.length() && concurrency < allWords[CurrentIndex].length()) {
						if (prefix.charAt(concurrency) == allWords[CurrentIndex].charAt(concurrency)) {
							concurrency++;
							// System.out.println("Checking concurrency at this point: " + concurrency);
						 } else {
						break;
					}
					}
				}

			boolean greater;
			int test = concurrency - 1;
			if (test > (-1)) {
				greater = true;
			} else {
				greater = false;
			}

			//System.out.println("The concurrency is: " + concurrency);

				if(concurrency > 0 && greater == true) {
					if(allWords[CurrentIndex].startsWith(prefix)) {
						if(CurrentNode.firstChild != null) {
							TrieNode RecursiveNode = CurrentNode.firstChild;
							Words.addAll(completionList(RecursiveNode, allWords, prefix)); //might be null? reminder to check later
							if(CurrentNode.sibling != null) {
								CurrentNode = CurrentNode.sibling;
								continue;
							}
							else {
								break;
							}
						}
						else {
							Words.add(CurrentNode);
							if(CurrentNode.sibling != null) {
								CurrentNode = CurrentNode.sibling;
								continue;
							}
							else {
								break;
							}
						}
					}
					else if(prefix.startsWith(allWords[CurrentIndex].substring(0,CurrentEnd +1))) {
						if(CurrentNode.firstChild != null) {
							TrieNode RecursiveNode = CurrentNode.firstChild;
							Words.addAll(completionList(RecursiveNode, allWords, prefix)); //might be null? reminder to check later
							if(CurrentNode.sibling != null) {
								CurrentNode = CurrentNode.sibling;
								continue;
							}
							else {
								break;
							}
						}
						else {
							Words.add(CurrentNode);
							if(CurrentNode.sibling != null) {
								CurrentNode = CurrentNode.sibling;
								continue;
							}
							else {
								break;
							}
						}
					}
					else {
						if(CurrentNode.sibling != null) {
							CurrentNode = CurrentNode.sibling;
							continue;
						}
						else {
							break;
						}
					}
			} else if (!greater) {
					if(CurrentNode.sibling != null) {
						CurrentNode = CurrentNode.sibling;
						continue;
					}
					else {
						break;
					}
			}
				concurrency = 0;
		}

		if(Words.isEmpty()) {
			return null;
		}

//
//				if(greater) {
//					if((fconcurrency - test)== CurrentEnd) {
//						if(CurrentNode.firstChild == null) {
//							Indexes IndexToInsert = new Indexes(CurrentIndex, (short) 0, (short) CurrentEnd);
//							TrieNode NodeToInsert = new TrieNode(IndexToInsert, CurrentNode.firstChild, CurrentNode.sibling);
//							Words.add(NodeToInsert);
//							CurrentNode = CurrentNode.sibling;
//							continue;
//						}
//						if(CurrentNode.firstChild != null) {
//							CurrentNode = CurrentNode.firstChild;
//							concurrency = 0;
//							continue;
//						}
//					}
//					//if((fconcurrency - test) < )
//				}
//
//				if (!greater) {
//					//if((fconcurrency - test) == CurrentEnd) {
//						if(PrefixLength > 0) {
//							CurrentNode = CurrentNode.sibling;
//							continue;
//						} else {
//							Indexes IndexToInsert = new Indexes(CurrentIndex, (short) 0, (short) CurrentEnd);
//							TrieNode NodeToInsert = new TrieNode(IndexToInsert, CurrentNode.firstChild, CurrentNode.sibling);
//							Words.add(NodeToInsert);
//							CurrentNode = CurrentNode.sibling;
//							continue;
//						}
////					else {
////						CurrentNode = CurrentNode.sibling;
////					}
//
//					}
//				}
//

		return Words;
	}



	public static void print(TrieNode root, String[] allWords) {
		System.out.println("\nTRIE\n");
		print(root, 1, allWords);
	}
	
	private static void print(TrieNode root, int indent, String[] words) {
		if (root == null) {
			return;
		}
		for (int i=0; i < indent-1; i++) {
			System.out.print("    ");
		}
		
		if (root.substr != null) {
			String pre = words[root.substr.wordIndex]
							.substring(0, root.substr.endIndex+1);
			System.out.println("      " + pre);
		}
		
		for (int i=0; i < indent-1; i++) {
			System.out.print("    ");
		}
		System.out.print(" ---");
		if (root.substr == null) {
			System.out.println("root");
		} else {
			System.out.println(root.substr);
		}
		
		for (TrieNode ptr=root.firstChild; ptr != null; ptr=ptr.sibling) {
			for (int i=0; i < indent-1; i++) {
				System.out.print("    ");
			}
			System.out.println("     |");
			print(ptr, indent+1, words);
		}
	}
 }
