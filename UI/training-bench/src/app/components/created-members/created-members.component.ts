import { Component } from '@angular/core';
import {MatListModule} from '@angular/material/list';
import {MatCardModule} from '@angular/material/card';
import { MemberServiceService } from '../../service/member-service.service';
import { Observable } from 'rxjs';
import { MemberEvent } from '../../model/member-event';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-created-members',
  standalone: true,
  imports: [MatListModule, MatCardModule, CommonModule],
  providers: [MemberServiceService],
  templateUrl: './created-members.component.html',
  styleUrl: './created-members.component.scss'
})
export class CreatedMembersComponent {

  members$!: Observable<MemberEvent[]>;
  members: MemberEvent[] = [];

  constructor(private memberCreated: MemberServiceService){}

  ngOnInit(){
      this.members$ = this.memberCreated.getMemberCreatedEvents();
  }

}
