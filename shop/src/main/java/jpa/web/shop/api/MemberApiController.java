package jpa.web.shop.api;

import jpa.web.shop.domain.Member;
import jpa.web.shop.service.MemberService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class MemberApiController {

    private final MemberService memberService;

    @GetMapping("/api/v1/members")
    public List<Member> membersV1(){
        return memberService.findMembers();
    }

    @PostMapping("/api/v1/members")
    public CreateMemberResponse saveMemberV1(@RequestBody @Valid Member member){
        Long id = memberService.join(member);
        return new CreateMemberResponse(id);
    }

    @GetMapping("/api/v2/members")
    public Result memberV2(){
        List<Member> findMembers = memberService.findMembers();
        List<MemberDto> collect = findMembers.stream()
                .map(m->new MemberDto(m.getUsername()))
                .collect(Collectors.toList());
        return new Result(collect, collect.size());
    }

    @Data
    @AllArgsConstructor
    static class Result<T>{
        private T data;
        private int count;
        
        //  다음과 같이 값을 반환함
        // {
        //    "data": [
        //        {
        //            "name": "joo"
        //        },
        //        {
        //            "name": "user"
        //        },
        //        {
        //            "name": "user2"
        //        }
        //    ]
        //}
    }

    @Data
    @AllArgsConstructor
    static class MemberDto{
        private String name;
    }

    // 별도의 DTO를 받는것
    @PostMapping("/api/v2/members")
    public CreateMemberResponse saveMemberV2(@RequestBody @Valid CreateMemberRequest request){
        Member member = new Member();
        member.setUsername(request.getName());
        Long id = memberService.join(member);
        return new CreateMemberResponse(id);
    }

    @PutMapping("/api/v2/members/{id}")
    public UpdatgeMemberResponse updateMemberV2(
            @PathVariable("id") Long id,
            @RequestBody @Valid UpdateMemberRequest request
            ){
        Member member = memberService.update(id, request.getName());
        Member findMember = memberService.findOne(id);
        return new UpdatgeMemberResponse(findMember.getId(), findMember.getUsername());
    }

    @Data
    @Getter
    static class UpdateMemberRequest{
        private String name;

    }

    @Data
    @AllArgsConstructor
    static class UpdatgeMemberResponse{
        private Long id;
        private String name;

    }

    @Data
    static class CreateMemberRequest{
        private String name;
    }


    @Data
    static class CreateMemberResponse{
        private Long id;

        public CreateMemberResponse(Long id) {
            this.id = id;
        }
    }
}
