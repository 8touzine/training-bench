import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environement } from '../environement';
import { ApiPaths } from '../api.paths';
import { MemberEvent } from '../model/member-event';
@Injectable({
  providedIn: 'root'
})
export class MemberEventRepositoryService {

  constructor(private httpClient: HttpClient) { }
  baseUrl = environement.baseUrl;

  public getCreatedMembers(){
    let url = this.baseUrl + ApiPaths.created;
    return this.httpClient.get<MemberEvent[]>(url);
  }
}
