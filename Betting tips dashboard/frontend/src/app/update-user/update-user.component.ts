import { Component, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';
import { UserService } from '../service/user.service';

import { ActivatedRoute, Route, Router } from '@angular/router';

@Component({
  selector: 'app-update-user',
  templateUrl: './update-user.component.html',
  styleUrls: ['./update-user.component.css'],
})
export class UpdateUserComponent implements OnInit {
  role = '';
  user: User = {};
  constructor(
    private userService: UserService,
    private router: Router,
    private route: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.getAll();
  }
  getAll() {
    this.userService
      .getByemail(this.route.snapshot.paramMap.get('email') + '')
      .subscribe((res: any) => {
        this.user = res[0];
        console.log(this.user);
        this.user.password2 = this.user.password;
        // console.log(res.data[0]);
      });
  }
  options = ['Admin', 'Data_collecter'];

  update(f: NgForm) {
    console.log(f.value);
    this.userService
      .update(this.route.snapshot.paramMap.get('username') + '', f.value)
      .subscribe((res) => {
        console.log(res);
        // location.reload();
        this.router.navigate(['/manage']);
      });
  }
}

interface User {
  id?: string;
  username?: string;
  first_name?: string;
  last_name?: string;
  password?: string;
  role?: string;
  sex?: string;
  phone?: any;
  password2?: any;
}
