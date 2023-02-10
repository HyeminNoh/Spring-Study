package com.example.core;

import com.example.core.discount.FixDiscountPolicy;
import com.example.core.member.MemberService;
import com.example.core.member.MemberServiceImpl;
import com.example.core.member.MemoryMemberRepository;
import com.example.core.order.OrderService;
import com.example.core.order.OrderServiceImpl;

// 애플리케이션의 전체 동작 방식을 구성하기 위해, 구현 객체를 생성하고 연결하는 책임을 가지는 별도의 설정 클래스
// 관심사를 분리해주는 역할
public class AppConfig {

    public MemberService memberService() {
        return new MemberServiceImpl(new MemoryMemberRepository()); // MemoryMemberRepository를 사용하는 MemberServiceImpl
    }

    public OrderService orderService() {
        return new OrderServiceImpl(new MemoryMemberRepository(), new FixDiscountPolicy());
    }
}