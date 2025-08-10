import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { MemberFormComponent } from "./components/member-form/member-form.component";
import {MatTabsModule} from '@angular/material/tabs';
import {MatGridListModule} from '@angular/material/grid-list';
import { CreatedMembersComponent } from './components/created-members/created-members.component';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, MemberFormComponent, MatTabsModule, MatGridListModule, CreatedMembersComponent],
  templateUrl: './app.component.html',
  styleUrl: './app.component.scss'
})
export class AppComponent {
  title = 'training-bench';
}
