package puzzleProblem;


public class ListAndPos{

	public int list[] = new int[16];            //存放第i个位置的数码值
	public int val2pos[][] = new int[16][2];    //存放数码对应的r、c
	
	//有参构造方法
	public ListAndPos(int arr[]) {
		for(int i=0;i<16;i++) {
			list[i]=arr[i];
		}
		initVal2Pos();
	}
	
	//将list值得位置信息记录到val2pos中
	public void initVal2Pos() {
		for(int i=0;i<16;i++) {
			val2pos[list[i]][0] = i/4;
			val2pos[list[i]][1] = i%4;
		}
	}
	
	/*
	 * 在标准坐标系中，两点的x坐标差的绝对值与y坐标差的绝对值之和为曼哈顿距离。
	 * 求出初始矩阵与目标矩阵对应值得曼哈顿距离并求和（除去0）得到的值为评估值，写成函数即为评估函数。
	 * 该值为从初始状态到目标状态所要经过的最小步数，实际步数只会大于等于该值。
	 */
	public int fv(ListAndPos lap) {
		int cost = 0;
		for(int k=1;k<16;k++) {
			cost += Math.abs(val2pos[k][0]-lap.val2pos[k][0])
					+Math.abs(val2pos[k][1]-lap.val2pos[k][1]);
		}
		return cost;
	}
	/*
	 * 逆序数：从左到右看每个数的右边比它小的数的个数，并进行求和
	 */
	public int getReversedOrder() {              //求list中的逆序数
		int  reversedOrder = 0;
		for(int i=0;i<16;i++) {
			if(list[i]==0) continue;
			for(int j=i+1;j<16;j++) {
				if(list[j]==0) continue;
				if(list[i]>list[j]) {
					reversedOrder++;
				}
			}
		}
		return reversedOrder;
	}
	/*
	 * 初始状态0所在的行数为i,目标状态0所在的行数为j，两者之差的绝对值为k。若k为奇数，则两个矩阵相应的逆序数的奇偶性相异才有解。
	 * 若k为偶数，则两个矩阵的逆序数必须相同才有解。不是上述两种情况即为无解。
	 * 通过初始判定就可以不用搜索就能直接否定无解情况。
	 */
	public boolean hasSolution(ListAndPos lap) {
		int revsOrder1 = getReversedOrder();
		int revsOrder2 = lap.getReversedOrder();
		int zeroXZ = Math.abs(val2pos[0][0]-lap.val2pos[0][0]);
		int revsOrder = revsOrder1+revsOrder2;
		if(zeroXZ%2==1) {
			if(revsOrder %2==1)
				return true;
			else
				return false;
		}else {
			if(revsOrder%2==0)
				return true;
			else
				return false;
		}
	}
	
	public int[] swap(int rawsr,int rawsc,int newsr,int newsc) {
		int listpos[]=new int[2];
		listpos[0] = rawsr*4+rawsc;
		listpos[1] = newsr*4+newsc;
		val2pos[list[listpos[0]]][0]=newsr;
		val2pos[list[listpos[0]]][1]=newsc;
		val2pos[list[listpos[1]]][0]=rawsr;
		val2pos[list[listpos[1]]][1]=rawsc;
		int temp = list[listpos[0]];
		list[listpos[0]] = list[listpos[1]];
		list[listpos[1]] = temp;
		return listpos;
	}
	
	//打印每次移动的状态
	public void print() {
		for(int i=0;i<16;i=i+4) {
			System.out.printf("%d %d %d %d%n",list[i],list[i+1],list[i+2],list[i+3]);
		}
		System.out.println("-----------------------");
	}
}
