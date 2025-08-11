import { Component } from '@angular/core';
import {MatListModule} from '@angular/material/list';
import {MatCardModule} from '@angular/material/card';
import { MemberServiceService } from '../../service/member-service.service';
import { map, Observable, tap } from 'rxjs';
import { MemberEvent } from '../../model/member-event';
import { CommonModule } from '@angular/common';
import {MatButtonModule} from '@angular/material/button';
import { MatButton } from '@angular/material/button';

@Component({
  selector: 'app-created-members',
  standalone: true,
  imports: [MatListModule, MatCardModule, CommonModule, MatButtonModule, MatButton],
  providers: [MemberServiceService],
  templateUrl: './created-members.component.html',
  styleUrl: './created-members.component.scss'
})
export class CreatedMembersComponent {

  members$!: Observable<MemberEvent[]>;
  members: MemberEvent[] = [];

  constructor(private memberCreated: MemberServiceService){}

  ngOnInit(){
      this.loadData();
  }
  loadData(){
this.members$ = this.memberCreated.getMemberCreatedEvents()
      .pipe(
        map((event: any) => event.content)
      );
      this.members$.subscribe((member) => {
        console.log("eeeh ", member);
      } );

      console.log(this.members$);
  }

}
