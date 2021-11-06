package lotto.domain;

import lotto.exception.InputDataErrorCode;
import lotto.exception.InputDataException;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum Rank {
    FIRST(6, 2_000_000_000),
    SECOND(5, 30_000_000),
    THIRD(5, 1_500_000),
    FOURTH(4, 50_000),
    FIFTH(3, 5_000),
    NOT_WIN_MONEY_MATCH_COUNT_TWO(2, 0),
    NOT_WIN_MONEY_MATCH_COUNT_ONE(1, 0),
    NOT_MATCH(0, 0);

    private int count;
    private int winMoney;

    Rank(int count, int winMoney) {
        this.count = count;
        this.winMoney = winMoney;
    }

    public int count() {
        return this.count;
    }

    public int winMoney() {
        return this.winMoney;
    }

    public static Rank rank(int count, boolean isMatchBonusBall) {
        Rank findRank = findRank(count);
        if (isMatchCountFive(findRank)) {
            return chooseSecondRank(isMatchBonusBall);
        }
        return findRank;
    }

    private static Rank chooseSecondRank(boolean isMatchBonusBall) {
        if (isMatchBonusBall) {
            return Rank.SECOND;
        }
        return Rank.THIRD;
    }

    private static boolean isMatchCountFive(Rank findRank) {
        return findRank.count() == 5;
    }

    private static Rank findRank(int count) {
        return Arrays.stream(values())
                .filter(rank -> isMatch(count, rank))
                .findFirst()
                .orElseThrow(() -> new InputDataException(InputDataErrorCode.INVALID_LOTTO_WIN_COUNT));
    }

    public static List<Rank> getLottoResultRank() {
        return Arrays.stream(values())
                .filter(rank -> rank.count > 2)
                .collect(Collectors.toList());
    }

    private static boolean isMatch(int count, Rank rank) {
        return rank.count == count;
    }
}
