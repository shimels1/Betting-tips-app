import { Injectable } from '@angular/core';
import { Match } from '../model/Match';
import { HttpClient, HttpHeaders } from '@angular/common/http';
@Injectable({
  providedIn: 'root',
})
export class VipMatchService {
  
  url = 'http://localhost:8000/api/vip';

  constructor(private http: HttpClient) {}

  getAllMatch() {
    return this.http.get<Match[]>(this.url + '/GetAll');
  }
  getDateLists(type: any) {
    return this.http.get<Match[]>(this.url + '/getDateLists/' + type);
  }
  getByDateAndCat(date: any, catagory: any) {
    console.log(date);
    return this.http.post<Match[]>(this.url + '/getByDateAndCat/', {
      date: date,
      catagory: catagory,
    });
  }
  getLeagLists() {
    return this.http.get<Match[]>(this.url + '/getLeagLists/');
  }
  getPredictionLists() {
    return this.http.get<Match[]>(this.url + '/getPredictionLists/');
  }
  getClubLists() {
    return this.http.get<Match[]>(this.url + '/getClubLists/');
  }
  getByDate(date: any) {
    return this.http.post<Match[]>(this.url + '/getByDate/', {
      date: date,
    });
  }
  getDataByDateAndCatagory(date: any, catagory: any) {
    return this.http.post<Match[]>(this.url + '/getByDateAndCat/', {
      date: date,
      catagory: catagory,
    });
  }

  ViewOneMatch(idbetting: any) {
    return this.http.get<Match>(this.url + '/getOne/' + idbetting, {});
  }

  addMatch(Match: Match) {
    return this.http.post(this.url + '/post', Match);
  }
  deleteMatch(idbetting: any) {
    return this.http.delete(this.url + '/delete/' + idbetting, {});
  }

  updateMatch(idbetting: string, Match: Match) {
    return this.http.put(this.url + '/put', Match);
  }
}
