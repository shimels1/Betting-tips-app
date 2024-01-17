import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root',
})
export class UserService {
  private readonly apiUrl = 'http://localhost:3000/api/user';

  constructor(private http: HttpClient) {}

  create(data: any) {
    return this.http.put(`${this.apiUrl}/register`, data);
  }

  getAll() {
    return this.http.get<any[]>(`${this.apiUrl}/getAll`);
  }
  getByemail(email: any) {
    return this.http.get(`${this.apiUrl}/email/${email}`);
  }
  login(data: any) {
    return this.http.post(`${this.apiUrl}/login`, data);
  }

  delete(email: any) {
    return this.http.delete(`${this.apiUrl}/delete/${email}`);
  }

  update(email: any, data: any) {
    return this.http.put(`${this.apiUrl}/update/${email}`, data);
  }
}
