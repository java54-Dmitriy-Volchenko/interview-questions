package telran.interviews;
import java.time.LocalDate;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
public class InterviewQuestions {
public static void displayOccurrences(String [] strings) {
	HashMap<String, Integer> mapOccurrences = getOccurrencesMap(strings);
	TreeMap<Integer, TreeSet<String>> treeMapOccurrences = getTreeMapOccurrences(mapOccurrences);
	displayOccurrences(treeMapOccurrences);
}
public static void displayOccurrencesStream(String[] strings) {
	Arrays.stream(strings)
	.collect(Collectors.groupingBy(s -> s, Collectors.counting()))
	.entrySet().stream().sorted((e1, e2) -> {
		int res = Long.compare(e2.getValue(), e1.getValue());
		return res == 0 ? e1.getKey().compareTo(e2.getKey()) : res;
	}).forEachOrdered(e -> System.out.printf("%s -> %d\n", e.getKey(), e.getValue()));
}

private static void displayOccurrences(TreeMap<Integer, TreeSet<String>> treeMapOccurrences) {
	treeMapOccurrences.entrySet().forEach(e -> {
		e.getValue().forEach(str -> System.out.printf("%s => %d\n",str, e.getKey()));
	});
	
}

private static TreeMap<Integer, TreeSet<String>> getTreeMapOccurrences(HashMap<String, Integer> mapOccurrences) {
	TreeMap<Integer, TreeSet<String>> result =
			new TreeMap<Integer, TreeSet<String>>(Comparator.reverseOrder());
	mapOccurrences.entrySet()
	.forEach(e -> result.computeIfAbsent(e.getValue(), k -> new TreeSet<>() ).add(e.getKey()));
	
	return result;
}

private static HashMap<String, Integer> getOccurrencesMap(String[] strings) {
	HashMap<String, Integer> result = new HashMap<>();
	for(String str: strings) {
		result.merge(str, 1, Integer::sum);
	}
	return result;
}
static public boolean isSum2(int [] array, int sum) {
	//returns true if a given array contains two numbers, the summing of which
	//equals a given 'sum' value
	//complexity O[N] only one pass over the elements
	HashSet<Integer> helper = new HashSet<>();
	int index = 0;
	while(index < array.length && !helper.contains(sum - array[index])) {
		helper.add(array[index++]);
	}
	return index < array.length;
}
static public int getMaxWithNegativePresentation(int [] array) {
	//returns maximal positive value for which exists negative one with the same abs value
	//if no pair of positive and negative values with the same abs value the method returns -1
	//complexity O[N] only one pass over the elements
	int maxRes = -1;
	HashSet<Integer> helper = new HashSet<>();
	for(int num: array) {
		if(helper.contains(-num)) {
			maxRes = Math.max(maxRes, Math.abs(num));
		} else {
			helper.add(num);
		}
	}
	return maxRes;
}
public static Map<Integer, Integer> getMapSquares(List<Integer> numbers) {
	Map<Integer, Integer> res = numbers.stream()
			.collect(Collectors.toMap(n -> n, n -> n * n, (v1, v2) -> v1,
					LinkedHashMap::new));
	return res;
}
public static boolean isAnagram(String word, String anagram) {
	
	boolean res = false;
	
	 if (word.equals(anagram)) res = false;
	 else if (word.length() != anagram.length()) res = false;

	 else {
		res = makeMapFrom(word).equals(makeMapFrom(anagram));
        }
        return res;
	}

	private static Map<String, Long> makeMapFrom (String str){
		return   Arrays.stream(str.split(""))
				.collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
	}
	
	
public static List<DateRole> assignRoleDates(List<DateRole> rolesHistory,
		List<LocalDate> dates) {
	
	TreeMap<LocalDate, String> rolesMap = new TreeMap<>();
    for (DateRole role : rolesHistory) {
        rolesMap.put(role.date(), role.role());
    }

    return dates.stream()
            .map(date-> {
                Map.Entry<LocalDate, String> entry = rolesMap.floorEntry(date);
                String role = (entry != null) ? entry.getValue() : null;
                return new DateRole(date, role);
            })
            .collect(Collectors.toList());
}
public static void displayDigitsStatistics() {
	
	   new Random().ints(1_000_000, 0, Integer.MAX_VALUE)
       .flatMap(n -> String.valueOf(n).chars())
       .boxed() //makes objects from primitives to have the opportunity makes map in collect step
       .collect(Collectors.groupingBy(c -> c, Collectors.counting()))//makes map
       .entrySet().stream()//stream of entries from map to sort result on the next step
       .sorted(Map.Entry.<Integer, Long>comparingByValue(Comparator.reverseOrder()))//comparator from java.util found in Internet
       .forEach(e -> System.out.printf("%d -> %d\n", e.getKey() - '0', e.getValue()));//solution  to get digits from ASCII code found in Internet

	
	
}
}
