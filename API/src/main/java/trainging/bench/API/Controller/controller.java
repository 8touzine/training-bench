package trainging.bench.API.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import trainging.bench.API.model.MemberEvent;
import trainging.bench.API.repository.MemberCreatedRepository;

@RestController
@RequestMapping("/api/members")
public class controller {

    @Autowired
    private MemberCreatedRepository repository;

    @GetMapping("/created")
    public Iterable<MemberEvent> findAll(){
        return repository.findAll();
    }
}
