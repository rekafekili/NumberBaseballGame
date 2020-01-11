package server;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class ServerGame {
	private int[] goal;
	private int hintLimit = 1;
	private int warning = 3;
	
	/**
	 * 정답을 생성하는 메소드
	 * 어떤 방식을 할지 고민하다 무작위로 카드를 뽑는 것처럼 구현
	 */
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

	/**
	 * Client로부터 받은 예상 값을 정답과 비교하여 결과를 반환하는 메소드
	 * @param inputNumber Client로부터 받은 예상 값
	 * @return x Strike y Ball 형식의 문자열
	 */
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

	/**
	 * 정답을 구성하는 세 숫자 중에서 하나를 랜덤으로 골라서 반환
	 * @return 랜덤으로 고른 숫자와 *를 결합한 문자열
	 */
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
