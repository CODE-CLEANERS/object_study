package chapter_two.discount.policy;

import chapter_two.Money;
import chapter_two.Screening;
import chapter_two.discount.condition.DiscountCondition;

import java.util.ArrayList;
import java.util.List;

/**
 * 할인 정책은 1.정가할인 2.비율할인 이 있다.
 * 할인 적용 조건(discountPolicy) 는 여러개가 있다.
 */
public abstract class DiscountPolicy {
    private List<DiscountCondition> conditions = new ArrayList<>();

    public DiscountPolicy(DiscountCondition... conditions) {
        this.conditions = List.of(conditions);
    }

    /**
     * 부모 클래스에서 해당 매서드로 기본 동작을 정의하고
     * 자식 클래스에서는 @Override 할 getDiscountAmount 매서드로 필요한 처리를 완료한다.
     * 위와 같이 부모 클래스에 기본적인 알고리즘을 구현하고, 중간에 필요한 처리를 자식 클래스에게 위임하는 디자인 패턴을
     * Template Method Pattern 이라고 부른다.
     */
    public Money calculateDiscountPolicy(Screening screening){
        for (DiscountCondition each : conditions) {
            if (each.isSatisfiedBy(screening)){
                return getDiscountAmount(screening);
            }
        }

        return Money.ZERO;
    }

    protected abstract Money getDiscountAmount(Screening screening);
}
