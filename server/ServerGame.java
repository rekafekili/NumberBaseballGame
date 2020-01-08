package server;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class ServerGame {
	private int[] goal;
	private int hintLimit = 1;
	private int warning = 3;
	
	private Timer alertTimer;
	private TimerTask alertTask;
	
	public ServerGame(ServerUDP udp){
		// 10초마다 줄 경고 기능 구현
		alertTimer = new Timer();
		alertTask = new TimerTask() {
			@Override
			public void run() {
				String msg = "";
				try {
					if(warning > 0) {
						msg = "!!! Warning[" + (warning--) + "] !!!";
					}
					else {
						msg = "STOP";
					}
					udp.sendMessage(msg);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		};
	}
	
	// 경고 기능 시작 메소드
	public void startTimer() {
		alertTimer.scheduleAtFixedRate(alertTask, 10000, 10000);	// 10초 이후로 10초 마다
	}
	
	// 경고 기능 종료 메소드
	public void cancelTimer() {
		alertTimer.cancel();
	}
	
	public void createGoal() {
		int[] arr = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 };
		int[] goal = new int[3];
		for (int i = 0; i < 3; i++) {
			while (true) {
				int randomIndex = (int) (Math.random() * 10);
				if (arr[randomIndex] != -1) {
					goal[i] = arr[randomIndex];
					arr[randomIndex] = -1;
					break;
				}
			}
		}
		System.out.println("Goal : " + goal[0] + goal[1] + goal[2]);
		
		this.goal = goal;
	}

	public String calcNumberBaseball(String inputNumber) {
		int[] input = new int[3];
		for (int i = 0; i < 3; i++) {
			input[i] = inputNumber.charAt(i) - '0';
		}

		int strike = 0;
		int ball = 0;

		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				if (goal[i] == input[j] && i == j) {
					strike++;
				} else if (goal[i] == input[j] && i != j) {
					ball++;
				}
			}
		}

		if (strike == 0 && ball == 0)
			return "OUT!!";
		else if (strike == 3 && ball == 0)
			return "GOAL!";
		return strike + "Strike " + ball + "Ball";
	}

	public String makeHintMessage() {
		String hintMessage = "";
		if(hintLimit==0) {
			hintMessage = "Sorry, Hint is Only Once!";
		}
		else {
			int index = (int) (Math.random() * 3); // 0~2 중 랜덤 값
			for (int i = 0; i < 3; i++) {
				if (i == index) {
					hintMessage += Integer.toString(goal[index]);
				} else {
					hintMessage += "*";
				}
			}
			hintLimit--;
		}
		return hintMessage;
	}
}
