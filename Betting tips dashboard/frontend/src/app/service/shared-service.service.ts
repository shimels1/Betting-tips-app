import { Injectable } from '@angular/core';
import { Subject } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class SharedServiceService {
  subject: Subject<Object> = new Subject<Object>();
  constructor() {}
}
