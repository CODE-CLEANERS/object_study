package chapter_two.discount.policy;

import chapter_two.Money;
import chapter_two.Screening;
import chapter_two.discount.condition.DiscountCondition;

public class PercentDiscountPolicy extends DiscountPolicy{
    private Double percent;

    public PercentDiscountPolicy(Double percent, DiscountCondition... conditions) {
        super(conditions);
        this.percent = percent;
    }

    @Override
    protected Money getDiscountAmount(Screening screening) {
        return screening.getMovieFee().times(percent);
    }
}
