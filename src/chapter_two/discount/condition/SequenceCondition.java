package chapter_two.discount.condition;

import chapter_two.Screening;

/**
 * 상영 순서(sequence) 에 따른 할인 조건
 */
public class SequenceCondition implements DiscountCondition{
    private int sequence;

    public SequenceCondition(int sequence) {
        this.sequence = sequence;
    }

    @Override
    public boolean isSatisfiedBy(Screening screening) {
        return screening.isSequence(sequence);
    }
}
