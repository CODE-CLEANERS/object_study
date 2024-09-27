package chapter_two.test;

import chapter_two.Money;
import chapter_two.Movie;
import chapter_two.discount.condition.SequenceCondition;
import chapter_two.discount.policy.AmountDiscountPolicy;
import org.junit.jupiter.api.Test;

import java.time.Duration;

class MovieInitTest {
    /**
     * Movie 클래스 어디에서도 할인 정책을 결정하는 부분이 없다. 어떻게 영화 요금 계산에 할인정책을 결정 가능할까?
     */
    @Test
    void foo(){
        new Movie(
                "화양연화",
                Duration.ofMinutes(120L),
                Money.wons(10000L),
                new AmountDiscountPolicy(
                        Money.wons(1000)
                ));

        new Movie(
                "화양연화",
                Duration.ofMinutes(120L),
                Money.wons(10000L),
                new AmountDiscountPolicy(
                        Money.wons(1000),
                        new SequenceCondition(2),
                        new SequenceCondition(10)));
    }
}