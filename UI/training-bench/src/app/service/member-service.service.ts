import { Injectable } from '@angular/core';
import { MemberRepositoryService } from '../repository/member-repository.service';
import { MemberEventRepositoryService } from '../repository/member-event-repository.service';
import { Member } from '../model/member';

@Injectable({
  providedIn: 'root'
})
export class MemberServiceService {

  constructor(
    private memberRepo: MemberRepositoryService,
    private memvereventRepo: MemberEventRepositoryService
  ) { }

  public getMemberCreatedEvents(){
    return this.memvereventRepo.getCreatedMembers();
  }

  public registerMember(member: Member){
    return this.memberRepo.createMember(member);
  }
}
