import { Component, OnInit } from '@angular/core';
import { UserService } from '../service/user.service';
import { Router } from '@angular/router';
import { NgForm } from '@angular/forms';
import { SharedServiceService } from '../service/shared-service.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
})
export class LoginComponent implements OnInit {
  public isLogin: any;

  constructor(
    private userService: UserService,
    private router: Router,
    private sharedService: SharedServiceService
  ) {
    if (sessionStorage.getItem('id') != null) this.router.navigate(['/free']);
  }

  ngOnInit(): void {}
  options = ['Admin', 'Data_collecter'];

  save(f: NgForm) {
    this.userService.login(f.value).subscribe((res: any) => {
      // res = res[0];
      console.log(res);

      sessionStorage.setItem('fname', res.fname);
      sessionStorage.setItem('lname', res.lname);
      sessionStorage.setItem('id', res.id);
      sessionStorage.setItem('email', res.email);
      sessionStorage.setItem('role', res.role);

      this.sharedService.subject.next('login');
      this.router.navigate(['/free']);
      // console.log(res);
      // location.reload();
    });
  }
}
