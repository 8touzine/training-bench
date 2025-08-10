import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CreatedMembersComponent } from './created-members.component';

describe('CreatedMembersComponent', () => {
  let component: CreatedMembersComponent;
  let fixture: ComponentFixture<CreatedMembersComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CreatedMembersComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CreatedMembersComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
