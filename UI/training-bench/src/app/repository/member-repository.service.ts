import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environement } from '../environement';
import { ApiPaths } from '../api.paths';
import { Member } from '../model/member';

@Injectable({
  providedIn: 'root'
})
export class MemberRepositoryService {

  constructor(private httpClient: HttpClient) { }
  baseUrl = environement.baseUrl;
  

  public createMember(member: Member){
    let url = this.baseUrl + ApiPaths.create;
    return this.httpClient.post<Member>(url, member)
  }

}
