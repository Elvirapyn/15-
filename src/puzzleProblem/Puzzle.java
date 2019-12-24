package puzzleProblem;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;


public class Puzzle extends Application {
	public int move[][] = {{-1,0},{0,-1},{0,1},{1,0}};   //�ϣ����ң���
	public int initList[] = new int[16];
	public int goalList[] = new int[16];
	public final int maxLevel = 60;
	public int leafLevel,limit;
	public int rec[] = new int[maxLevel+10];            //0,1,2,3
	public ListAndPos initMatrix;
	public ListAndPos tempMatrix;
	public ListAndPos goalMatrix;
	public UIcanvas canvas = new UIcanvas();
	Pane pane;
	Scene scene ;
	int[]pos = new int[2];
	Generator generator = new Generator();  
	
	@Override
	public void start(Stage primaryStage) throws Exception {		
		//�������ֲ���
		String url = getClass().getResource("bg.mp3").toString();
		Media media = new Media(url);
		MediaPlayer player = new MediaPlayer(media);
		player.setVolume(10);
		player.setCycleCount(10);
		player.play();

		//���ɳ�ʼ�����Ŀ�����		
		goalList = generator.generateGoalList();
		goalMatrix = new ListAndPos(goalList);
		leafLevel = -1;	
		generateMatrix();
		limit = tempMatrix.fv(goalMatrix);
		while(limit<=maxLevel&&!dfs(tempMatrix, goalMatrix, 0, 0)){
			limit++;
		}
		
		
		//���ɳ�ʼ����
		canvas.initCanvas(initList);
		pane = canvas.getPane();
		scene = new Scene(pane,600,460);
		primaryStage.setScene(scene);
		primaryStage.setTitle("����С��Ϸ");
		primaryStage.show();

		//����⡱��ť
		canvas.solveButton.setOnAction( e -> {
			Thread thread = new Thread() {
				@Override
				public void run() {		
					if(leafLevel!=-1) {
						print(initMatrix);
					}else {
						System.out.println("��"+maxLevel+"�����Ҳ����⣬����Ѱ��!");
					}
				}         
			};
			thread.setName("thread1");
			thread.start();
		});
		
		//�����á���ť
		canvas.restartButton.setOnAction(e->{	
			leafLevel = -1;	
			generateMatrix();
			canvas.refreshCanvas(initList);
			limit = tempMatrix.fv(goalMatrix);
			while(limit<=maxLevel&&!dfs(tempMatrix, goalMatrix, 0, 0)){
				limit++;
			}				
		});
	}
	public static void main(String[] args) {
		Application.launch(args);
	}

	//�����н�ĳ�ʼ����
	public void generateMatrix() {
		do{   
			initList = generator.generateInitList();		
			initMatrix = new ListAndPos(initList);
			tempMatrix = new ListAndPos(initList);
		}while(!tempMatrix.hasSolution(goalMatrix)) ;
	}
	
	//�ں�̨��ӡ���
	public void print(ListAndPos lap){
		lap.print();
		for(int i=0;i<leafLevel;i++) {
			System.out.println("step:"+(i+1));
			pos =lap.swap(lap.val2pos[0][0],lap.val2pos[0][1],lap.val2pos[0][0]+move[rec[i]][0],lap.val2pos[0][1]+move[rec[i]][1]);
			lap.print();
			canvas.move(pos[0],pos[1]);
			try {
				Thread.sleep(300);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.out.println("����");
	}


	public boolean dfs(ListAndPos lap,ListAndPos goalLap ,int level, int pmove){
		int val;
		if(level==limit) {
			val = lap.fv(goalLap);
			if(val==0) {
				leafLevel = level;
				return true;
			}
			return false;
		}

		int newsr,newsc;
		int rawsr,rawsc;
		for(int i=0;i<4;i++) {
			if(pmove+i==3&&level>0) continue;
			rawsr = lap.val2pos[0][0];
			rawsc = lap.val2pos[0][1];
			newsr = rawsr+move[i][0];
			newsc = rawsc+move[i][1];
			if(0<=newsr&&newsr<4&&0<=newsc&&newsc<4){
				lap.swap(rawsr,rawsc,newsr,newsc);
				val = lap.fv(goalLap);
				if(level+val<=limit){
					rec[level]=i;
					if(dfs(lap,goalLap,level+1,i)) return true;
				}
				lap.swap(newsr,newsc,rawsr,rawsc);
			}
		}
		return false;
	}
}

