package com.example.core.order;

import com.example.core.annotation.MainDiscountPolicy;
import com.example.core.discount.DiscountPolicy;
import com.example.core.member.Member;
import com.example.core.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class OrderServiceImpl implements OrderService{
    private final DiscountPolicy discountPolicy;
    private final MemberRepository memberRepository;

    @Autowired DiscountPolicy rateDiscountPolicy;

    @Autowired
    public OrderServiceImpl(MemberRepository memberRepository, @MainDiscountPolicy DiscountPolicy discountPolicy) {
        this.memberRepository = memberRepository;
        this.discountPolicy = discountPolicy;
    }

    @Override
    public Order createOrder(Long memberId, String itemName, int itemPrice) {
        Member member = memberRepository.findById(memberId);
        int discountPrice = discountPolicy.discount(member, itemPrice);

        return new Order(memberId, itemName, itemPrice, discountPrice);
    }

    // AppConfig 싱글톤 테스트 용
    public MemberRepository getMemberRepository() {
        return memberRepository;
    }
}
