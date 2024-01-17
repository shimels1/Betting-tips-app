import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { SharedServiceService } from '../service/shared-service.service';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css'],
})
export class HeaderComponent {
  public isLogin: boolean;

  constructor(
    private router: Router,
    private sharedService: SharedServiceService
  ) {
    // this.isLogin = sessionStorage.getItem('role');
    if (sessionStorage.getItem('role') == 'admin') {
      this.isLogin = true;
    } else {
      this.isLogin = false;
    }

    this.sharedService.subject.subscribe((data) => {
      if (sessionStorage.getItem('role') == 'admin') {
        this.router.navigate(['/login']);
        this.isLogin = true;
      } else {
        this.isLogin = false;
      }
    });
  }

  logout() {
    sessionStorage.clear();
    // sessionStorage.removeItem('fname');
    // sessionStorage.removeItem('lname');
    // sessionStorage.removeItem('id');
    // sessionStorage.removeItem('email');
    // sessionStorage.removeItem('role');
    this.isLogin = false;
    console.log(this.isLogin);
    if (sessionStorage.getItem('id') == null) {
      this.router.navigate(['/login']);
      // location.reload();
    }
  }
}
