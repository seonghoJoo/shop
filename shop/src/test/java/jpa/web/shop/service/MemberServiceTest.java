package jpa.web.shop.service;


import jpa.web.shop.domain.Member;
import jpa.web.shop.repository.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

@SpringBootTest
@Transactional
public class MemberServiceTest {


    @Autowired
    MemberService memberService;

    @Autowired
    MemberRepository memberRepository;
    
    @Autowired
    EntityManager em;

    @Test
    @Rollback(false)
    public void 회원가입() throws Exception {
        // given
        Member member = new Member();
        member.setUsername("kim");

        // when
        Long savedId = memberService.join(member);

        // 영속성 컨텍스트에 있는 애를 DB에 직접 반영해버리기
        //em.flush();
        
        // then
        Assertions.assertThat(member).isEqualTo(memberRepository.findOne(savedId));
    }

    @Test
    public void 중복_회원_예외() throws Exception {
        // given
        Member member1 = new Member();
        member1.setUsername("kim1");
        Member member2 = new Member();
        member2.setUsername("kim1");

        // when
        memberService.join(member1);
        try{
            memberService.join(member2);//예외가 발생해야한다.
        }catch (IllegalStateException e){
            return;
        }

        // then
        Assertions.fail("예외가 발생한다.");
    }


}
