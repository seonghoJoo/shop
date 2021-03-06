package jpa.web.shop.service;

import java.util.*;
import jpa.web.shop.domain.Member;
import jpa.web.shop.repository.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Transactional
    public Long join(Member member){
        validateDuplicateMember(member);
        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicateMember(Member member) {
        List<Member> findMemebrs = memberRepository.findByName(member.getUsername());
        if(!findMemebrs.isEmpty()){
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    // 회원 전체 조회
    public List<Member> findMembers(){
        return memberRepository.findAll();
    }

    // 회원 단건 조회
    public Member findOne(Long memberId){
        return memberRepository.findOne(memberId);
    }

    @Transactional
    public Member update(Long id , String name){
        Member member = memberRepository.findOne(id);
        member.setUsername(name);
        // @transactional로 변경감지 실행
        // update 쿼리 실행
        return member;
    }

}
