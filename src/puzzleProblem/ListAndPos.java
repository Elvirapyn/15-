package puzzleProblem;


public class ListAndPos{

	public int list[] = new int[16];            //��ŵ�i��λ�õ�����ֵ
	public int val2pos[][] = new int[16][2];    //��������Ӧ��r��c
	
	//�вι��췽��
	public ListAndPos(int arr[]) {
		for(int i=0;i<16;i++) {
			list[i]=arr[i];
		}
		initVal2Pos();
	}
	
	//��listֵ��λ����Ϣ��¼��val2pos��
	public void initVal2Pos() {
		for(int i=0;i<16;i++) {
			val2pos[list[i]][0] = i/4;
			val2pos[list[i]][1] = i%4;
		}
	}
	
	/*
	 * �ڱ�׼����ϵ�У������x�����ľ���ֵ��y�����ľ���ֵ֮��Ϊ�����پ��롣
	 * �����ʼ������Ŀ������Ӧֵ�������پ��벢��ͣ���ȥ0���õ���ֵΪ����ֵ��д�ɺ�����Ϊ����������
	 * ��ֵΪ�ӳ�ʼ״̬��Ŀ��״̬��Ҫ��������С������ʵ�ʲ���ֻ����ڵ��ڸ�ֵ��
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
	 * �������������ҿ�ÿ�������ұ߱���С�����ĸ��������������
	 */
	public int getReversedOrder() {              //��list�е�������
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
	 * ��ʼ״̬0���ڵ�����Ϊi,Ŀ��״̬0���ڵ�����Ϊj������֮��ľ���ֵΪk����kΪ������������������Ӧ������������ż��������н⡣
	 * ��kΪż���������������������������ͬ���н⡣�����������������Ϊ�޽⡣
	 * ͨ����ʼ�ж��Ϳ��Բ�����������ֱ�ӷ��޽������
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
	
	//��ӡÿ���ƶ���״̬
	public void print() {
		for(int i=0;i<16;i=i+4) {
			System.out.printf("%d %d %d %d%n",list[i],list[i+1],list[i+2],list[i+3]);
		}
		System.out.println("-----------------------");
	}
}
