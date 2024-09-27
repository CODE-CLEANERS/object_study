package chapter_two;

import java.math.BigDecimal;

/**
 * 1 장에서는 금액을 구현하기 위해 Long 타입을 사용했다.
 * Money 객체로 바꿈으로써 어떤 것을 얻었을까?
 */
// TODO: 12/6/23 바꿈으로써 얻는 이익을 내 언어로 설명하기
public class Money {
    public static final Money ZERO = Money.wons(0);
    private final BigDecimal amount;

    public Money(BigDecimal amount) {
        this.amount = amount;
    }

    public static Money wons(long amount){
        return new Money(BigDecimal.valueOf(amount));
    }
    public Money minus(Money amount) {
        return new Money(this.amount.subtract(amount.amount));
    }

    public Money plus(Money amount){
        return new Money(this.amount.add(amount.amount));
    }

    public Money times(double percent){
        return new Money(this.amount.multiply(BigDecimal.valueOf(percent)));
    }

    public boolean isLessThan(Money other){
        return amount.compareTo(other.amount) < 0;
    }

    public boolean isGreaterOrEqual(Money other){
        return amount.compareTo(other.amount) >= 0;
    }
}
