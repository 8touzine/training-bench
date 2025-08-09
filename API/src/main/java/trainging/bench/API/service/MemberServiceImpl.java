package trainging.bench.API.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import trainging.bench.API.model.MemberDTO;
import trainging.bench.API.model.MemberDb;
import trainging.bench.API.repository.MemberDbRepository;

@Service
public class MemberServiceImpl implements MemberService {

    @Autowired
    private MemberDbRepository memberDbRepository;


    @Override
    public MemberDTO createMember(MemberDTO memberDTO) {
        MemberDb memberDb = convertToEntity(memberDTO);
        MemberDb createdMember = memberDbRepository.save(memberDb);
        return convertToDto(createdMember);
    }

    private MemberDb convertToEntity(MemberDTO memberDTO){
        MemberDb memberDb = new MemberDb();
        memberDb.setNom(memberDTO.nom());
        memberDb.setEmail(memberDTO.email());
        return memberDb;
    }
    private MemberDTO convertToDto(MemberDb memberDb){
        return new MemberDTO(memberDb.getId(), memberDb.getNom(), memberDb.getEmail());
    }
}
