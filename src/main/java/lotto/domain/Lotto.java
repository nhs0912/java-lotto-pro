package lotto.domain;

import lotto.exception.MyErrorCode;
import lotto.exception.MyException;

import java.util.*;
import java.util.stream.Collectors;

public class Lotto {
    private static final int LOTTO_COUNT_VALID_SIZE = 6;
    private List<Ball> balls;

    public Lotto(List<Ball> balls) {
        checkValidLotto(balls);
        this.balls = balls;
    }

    public List<Ball> balls() {
        return this.balls;
    }

    public List<Integer> extractNumber() {
        List<Integer> numbers = new ArrayList<>();
        for (Ball ball : balls) {
            numbers.add(ball.number());
        }
        return numbers;
    }

    public int match(Lotto compareLotto) {
        return (int) this.balls().stream()
                .filter(ball -> compareLotto.contains(ball))
                .count();
    }

    public boolean contains(Ball ball) {
        return this.balls().contains(ball);
    }

    private void checkValidLotto(List<Ball> balls) {
        checkDuplicationBall(balls);
        checkLengthOverThanSix(balls);
    }

    private void checkLengthOverThanSix(List<Ball> balls) {
        int ballsSize = balls.size();
        if (!isValidInputLottoRange(ballsSize)) {
            throw new MyException(MyErrorCode.VALID_LOTTO_SIZE_SIX);
        }
    }

    private boolean isValidInputLottoRange(int ballsSize) {
        return ballsSize == LOTTO_COUNT_VALID_SIZE;
    }

    private void checkDuplicationBall(List<Ball> balls) {
        Set<Ball> notDuplicationBalls = new HashSet<>();
        for (Ball ball : balls) {
            notDuplicationBalls.add(ball);
        }
        if (balls.size() != notDuplicationBalls.size()) {
            throw new MyException(MyErrorCode.EXIST_DUPLICATION_NUMBER);
        }
    }
}
