import java.io.*;
import java.util.*;



public class MinHeap{

  public static ArrayList<Contestant> heap = new ArrayList<Contestant>();
  public static ArrayList<Integer> handle = new ArrayList<Integer>();

	public MinHeap() {
		//heap = new ArrayList<Contestant>();
  }

  public static int parent(int i) {
    if (i == 0) {
      return 0;
    }
    return (i-1)/2;
  }

  public static int leftChild(int i) {
    return 2*i + 1;
  }

  public static int rightChild(int i) {
    return 2*i + 2;
  }

  public static void minHeapify(int i){
    int left = leftChild(i);
    int right = rightChild(i);
    int smallest;
    int temp;
    if (left <= heap.size()-1 && (heap.get(left).getPoints() < heap.get(i).getPoints())) {
      smallest = left;
    }
    else {
      smallest = i;
    }
    if (right <= heap.size()-1 && (heap.get(right).getPoints() < heap.get(smallest).getPoints())) {
      smallest = right;
    }
    if (smallest != i) {
      Collections.swap(heap, i, smallest);
      temp = i;
      handle.set(i, smallest);
      handle.set(smallest, temp);
      minHeapify(smallest);
    }
  }

  public static void findContestant(int k){
    if (heap.get(handle.get(k-1)).getId() == k){
      System.out.println("Contestant <" + k + "> is in the extended heap with score <" + (heap.get(handle.get(k))).getPoints() + ">.");
      return;
    }
    System.out.println("Contestant <" + k + "> is not in the extended heap.");
  }

  public static void insertContestant(int k, int s, int max){
    if (heap.size() >= max){
      System.out.println("Contestant <" + k + "> could not be inserted because the extended heap is already full.");
      return;
    }
    for (int i = 0; i < heap.size(); i++){
      if ((heap.get(i)).getId() == k){
        System.out.println("Contestant <" + k + "> is already in the extended heap: cannot insert.");
        return;
      }
    }
    Contestant con = new Contestant(k,s);
    if (heap.size() == 0){
      heap.add(0, con);
    }
    else {
      heap.add(heap.size() - 1, con);
    }
    System.out.println("Contestant <" + k + "> inserted with initial score <" + s + ">.");
    int x = heap.size()-1;
    while (x > 0 && (heap.get(x).getPoints() < heap.get(parent(x)).getPoints())) {
      Collections.swap(heap, x, parent(x));
      x = parent(x);
    }
    handle.set(k, x);
  }

  public static void eliminateWeakest(){
    if (heap.size() <= 0){
      System.out.println("No contestant can be eliminated since the extended heap is empty.");
			return;
		} else {
			int weakID = heap.get(0).getId();
      int weakPoints = heap.get(0).getPoints();
			heap.set(0, heap.get(heap.size()-1));
			heap.remove(heap.size()-1);
      handle.set(heap.get(heap.size()-1).getId(), -1);
			minHeapify(0);
      System.out.println("Contestant <" + weakID + "> with current lowest score <" + weakPoints + "> eliminated.");
			return;
		}
  }

  public static void earnPoints(int k, int p){
    //System.out.println(heap.get(handle[k]).getId() + " " + heap.get(handle[k]).getPoints());
    if (handle.get(k) > -1){
      heap.get(handle.get(k)).addPoints(p);
      System.out.println("Contestant <" + k + ">'s score increased by <" + p + "> points to <"  + heap.get(handle.get(k)).getPoints() + ">.");
      return;
    }
    System.out.println("Contestant <" + k + "> is not in the extended heap.");
  }

  public static void losePoints(int k, int p){
    //System.out.println(heap.get(handle[k]).getId() + " " + heap.get(handle[k]).getPoints());
    if (handle.get(k) > -1){
      heap.get(handle.get(k)-1).removePoints(p);
      System.out.println("Contestant <" + k + ">'s score decreased by <" + p + "> points to <"  + heap.get(handle.get(k)-1).getPoints() + ">.");
      return;
    }
    System.out.println("Contestant <" + k + "> is not in the extended heap.");
  }

  public static void showContestants(){
    for (int i = 0; i < heap.size(); i++){
      System.out.println("Contestant <" + heap.get(i).getId() + "> in extended heap location <" + (i + 1) + "> with score <" + heap.get(i).getPoints() + ">.");
    }
  }

  public static void showHandles(){
    for (int i = 0; i < handle.size(); i++){
      if (handle.get(i) > -1){
        System.out.println("Contestant <" + (i+1) + "> stored in extended heap location <" + (handle.get(i+1)) + ">.");
      }
      else {
        System.out.println("There is no Contestant <" + (i+1) + "> in the exteded heap: handle[" + (i+1) + "] = -1.");
      }
    }
  }

  public static void showLocation(int k){
    if ((heap.get(handle.get(k)).getId() == k)) {
      System.out.println("Contestant <" + k + "> stored in extended heap location <" + (handle.get(k)) + ">.");
      return;
    }
    System.out.println("There is no contestant <" + k + "> in the extended heap: handle[" + k + "] = -1.");
  }

  public static void crownWinner() {
    while (heap.size() > 1){
      Collections.swap(heap, heap.size() - 1, 0);
      handle.set(heap.get(heap.size() - 1).getId(), 0);
      handle.set(heap.get(0).getId(), -1);
      heap.remove(heap.size() - 1);
      minHeapify(0);
    }
    int winId = heap.get(0).getId();
    int winPoints = heap.get(0).getPoints();
    System.out.println("Contestant <" + winId + "> wins with score <" + winPoints + ">!");
  }

  public static void outputFile(){
    try {
      PrintStream out = new PrintStream(new FileOutputStream("output.txt"));
      System.setOut(out);
    }
    catch (FileNotFoundException e){
        System.out.println(e);
    }
  }

  public static void main(String args[]){
    outputFile();
    ArrayList<String> lines = new ArrayList<String>();
    try {
      Scanner scanner = new Scanner(new File(args[0]));
      while (scanner.hasNextLine()) {
        lines.add(scanner.nextLine());
      }
    }
    catch (FileNotFoundException e){
        System.out.println(e);
    }
    int max_size = Integer.parseInt(lines.get(0));
    for (int i = 0; i < max_size; i++){
      handle.add(i);
      handle.set(i, -1);
    }
    String[] split;
    String operation;
    String idString;
    String pointsString;
    int id;
    int points;
    for (int i = 1; i < lines.size(); i++){
      split = lines.get(i).split(" ");
      operation = split[0];
      switch(operation) {
        case "insertContestant":
          idString = split[1].substring(split[1].indexOf("<") + 1, split[1].indexOf(">"));
          pointsString = split[2].substring(split[2].indexOf("<") + 1, split[2].indexOf(">"));
          id = Integer.valueOf(idString);
          points = Integer.valueOf(pointsString);
          System.out.println(operation + " " + split[1] + " " + split[2]);
          insertContestant(id, points, max_size);
          break;
        case "showContestants":
          System.out.println(operation);
          showContestants();
          break;
        case "findContestant":
          idString = split[1].substring(split[1].indexOf("<") + 1, split[1].indexOf(">"));
          id = Integer.valueOf(idString);
          System.out.println(operation + " " + split[1]);
          findContestant(id);
          break;
        case "eliminateWeakest":
          System.out.println(operation);
          eliminateWeakest();
          break;
        case "losePoints":
          idString = split[1].substring(split[1].indexOf("<") + 1, split[1].indexOf(">"));
          pointsString = split[2].substring(split[2].indexOf("<") + 1, split[2].indexOf(">"));
          id = Integer.valueOf(idString);
          points = Integer.valueOf(pointsString);
          System.out.println(operation + " " + split[1] + " " + split[2]);
          losePoints(id, points);
          break;
        case "earnPoints":
          idString = split[1].substring(split[1].indexOf("<") + 1, split[1].indexOf(">"));
          pointsString = split[2].substring(split[2].indexOf("<") + 1, split[2].indexOf(">"));
          id = Integer.valueOf(idString);
          points = Integer.valueOf(pointsString);
          System.out.println(operation + " " + split[1] + " " + split[2]);
          earnPoints(id, points);
          break;
        case "showHandles":
          System.out.println(operation);
          showHandles();
          break;
        case "crownWinner":
          System.out.println(operation);
          crownWinner();
          break;
        case "showLocation":
          idString = split[1].substring(split[1].indexOf("<") + 1, split[1].indexOf(">"));
          id = Integer.valueOf(idString);
          System.out.println(operation + " " + split[1]);
          showLocation(id);
          break;
      }
    }


  }
}
