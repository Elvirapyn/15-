package puzzleProblem;

import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.animation.*;
import javafx.scene.control.Button;
import javafx.scene.image.*;
import javafx.util.Duration;

public class UIcanvas{
	Image[] images = new Image[16];
	ImageView []imageViews = new ImageView[16];
	Pane pane = new Pane();
	int duration = 100;
	String url = getClass().getResource("ButtonClick.wav").toString();
	Image bgImage;
	ImageView bgImageView;
	Button restartButton = new Button("重置");
	Button solveButton = new Button("求解");
	int offsetx = 100;
	int offsety = 150;
	int size = 65;
	
	public Pane getPane() {
		return this.pane;
	}
	public Button getRestartButton() {
		return this.restartButton;
	}
	
	//点击重置按钮时刷新界面
	public void refreshCanvas(int[] list) {
		this.pane.getChildren().removeAll(imageViews);
		for(int i=0;i<16;i++) {
			this.images[i] = null;
			this.imageViews[i]=null;
		}
			
		addImage(list);
	}
	//向界面的pane中添加图片子节点
	public void addImage(int[] list) {
		for(int i=0;i<16;i++) {
			if(list[i]==0)
				this.images[i] = null;
			else {
				String urlString = "image/图片"+String.valueOf(list[i])+".png";
				this.images[i] = new Image(urlString);
			}
			this.imageViews[i] = new ImageView();
			this.imageViews[i].setImage(this.images[i]);
			this.imageViews[i].setFitHeight(size);
			this.imageViews[i].setFitWidth(size);
			if(i<4) {
				imageViews[i].setX(size*i+offsetx);
				imageViews[i].setY(offsety);
			}
			else if(i<8&&i>=4) {
				imageViews[i].setX(size*(i%4)+offsetx);
				imageViews[i].setY(offsety+ size);
			}
			else if(i<12&&i>=8) {
				imageViews[i].setX(size*(i%4)+offsetx);
				imageViews[i].setY(offsety+ size*2);
			}
			else if(i<16&&i>=12) {
				imageViews[i].setX(size*(i%4)+offsetx);
				imageViews[i].setY(offsety+ size*3);
			}
			this.pane.getChildren().add(this.imageViews[i]);			
		}
	}
	
	//初始化界面
	public void initCanvas(int[] list) {
		this.bgImage = new Image("image/start.png");
		this.bgImageView = new ImageView();
		this.bgImageView.setImage(bgImage);
		this.bgImageView.setFitHeight(460);
		this.bgImageView.setFitWidth(600);
		this.pane.getChildren().add(bgImageView);
		solveButton.setLayoutX(450);
		solveButton.setLayoutY(200);
		solveButton.setStyle("-fx-background-color: #35c6f2; -fx-font-size: 16pt;");
		restartButton.setLayoutX(450);
		restartButton.setLayoutY(280);
		restartButton.setStyle("-fx-background-color: #35c6f2; -fx-font-size: 16pt;");
		pane.getChildren().add(solveButton);
		pane.getChildren().add(restartButton);

		Image bgImage =new Image("image/bg.jpg");
		ImageView bgImageView = new ImageView();
		bgImageView.setImage(bgImage);
		bgImageView.setFitHeight(24+size*4);
		bgImageView.setFitWidth(24+size*4);
		bgImageView.setX(offsetx-12);
		bgImageView.setY(offsety-12);
		this.pane.getChildren().add(bgImageView);
		addImage(list);
		
	}

	//创建移动时的动画效果
	public void move(int pos1,int pos2) {
		Media media = new Media(url);
		MediaPlayer player = new MediaPlayer(media);
		Timeline timeline = new Timeline();
		KeyValue kv1;
		KeyValue kv2; 
		KeyFrame kf1;
		KeyFrame kf2;
		timeline.setCycleCount(1);
		timeline.setAutoReverse(true);
		//左右交换
		if(pos1==(pos2+1)||pos1==(pos2-1)) {
			double x1 = imageViews[pos2].getX();
			double x2 = imageViews[pos1].getX();
			kv1 = new KeyValue(imageViews[pos1].xProperty(), x1);	
			kv2 = new KeyValue(imageViews[pos2].xProperty(), x2);
			kf1 = new KeyFrame(Duration.millis(this.duration), kv1);
			kf2 = new KeyFrame(Duration.millis(this.duration), kv2);	
		}
		//上下交换
		else {
			double y1 = imageViews[pos2].getY();
			double y2 = imageViews[pos1].getY();
			kv1 = new KeyValue(imageViews[pos1].yProperty(), y1);
			kf1 = new KeyFrame(Duration.millis(this.duration), kv1);
			kv2 = new KeyValue(imageViews[pos2].yProperty(), y2);
			kf2 = new KeyFrame(Duration.millis(this.duration), kv2);
		}
		timeline.getKeyFrames().add(kf2);
		timeline.getKeyFrames().add(kf1);	
		timeline.play();
		player.setVolume(100);
		player.play();
		ImageView tempImage = imageViews[pos2];
		imageViews[pos2]=imageViews[pos1];
		imageViews[pos1]=tempImage;		
	}
}

