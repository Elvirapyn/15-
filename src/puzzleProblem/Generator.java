package puzzleProblem;
import java.util.ArrayList;
import java.util.Random;


public class Generator{
	private int num = 16;

	public int[] generateInitList() {
		int list[] = new int[this.num]; 
		ArrayList<Integer> ranArrayList = new ArrayList<Integer>();
		Random ran = new Random();     //随机数种子
		int temp;
		int i = 0;
		while (i < this.num){
			temp = ran.nextInt(num);
			if (!ranArrayList.contains(temp)) {
				list[i] = temp;
				ranArrayList.add(temp);
				i++;
			}
		}
		return list;  
	}
	public int[] generateGoalList() {
		int list[] = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,0}; 
		return list;  
	}

}
