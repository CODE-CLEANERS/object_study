package chapter_two.discount.condition;

import chapter_two.Screening;

public interface DiscountCondition {
    boolean isSatisfiedBy(Screening screening);
}
