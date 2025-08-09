package trainging.bench.API.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import trainging.bench.API.model.MemberDTO;
import trainging.bench.API.model.MemberEvent;
import trainging.bench.API.repository.MemberCreatedRepository;
import trainging.bench.API.service.MemberServiceImpl;

@RestController
@RequestMapping("/api/members")
public class controller {

    @Autowired
    private MemberCreatedRepository repository;

    @Autowired
    private MemberServiceImpl memberService;

    @GetMapping("/created")
    public Iterable<MemberEvent> findAll(){
        return repository.findAll();
    }

    @PostMapping("/create")
    public MemberDTO createMember(@RequestBody MemberDTO memberDTO){
        return memberService.createMember(memberDTO);
    }
}
