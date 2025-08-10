import { Component, inject } from '@angular/core';
import {MatSelectModule} from '@angular/material/select';
import {MatInputModule} from '@angular/material/input';
import {MatFormFieldModule} from '@angular/material/form-field';
import {MatButtonModule} from '@angular/material/button';
import {MatCardModule} from '@angular/material/card';
import {MatSnackBar} from '@angular/material/snack-bar';
import { MemberServiceService } from '../../service/member-service.service';
import { FormsModule } from '@angular/forms';
import { Member } from '../../model/member';

@Component({
  selector: 'app-member-form',
  standalone: true,
imports: [MatFormFieldModule, MatInputModule, MatSelectModule, MatCardModule, MatButtonModule, FormsModule],
providers: [MemberServiceService],
  templateUrl: './member-form.component.html',
  styleUrl: './member-form.component.scss'
})
export class MemberFormComponent {
  private snackBar = inject(MatSnackBar);
  private name?: string;
  private email?: string;

  constructor(private memberService: MemberServiceService){}

  openSnackBar(message: string, action: string) {
    
    
  }

  createMember(name: string, email: string){
    let member!: Member ; 
    member.nom = name;
    member.email = email;
    this.memberService.registerMember(member);
    this.snackBar.open("member created", 'ok');
  }

}
