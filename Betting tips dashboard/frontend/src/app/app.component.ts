import { MatchService } from './service/match.service';
import { Component, OnInit } from '@angular/core';
import { ConfirmationService, MessageService } from 'primeng/api';
import { Match } from './model/Match';
import { DatePipe } from '@angular/common';

// Import the functions you need from the SDKs you need
import { initializeApp } from 'firebase/app';
import { getAnalytics } from 'firebase/analytics';
@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css'],
  providers: [MessageService, ConfirmationService],
})
export class AppComponent implements OnInit {
  matchs!: Match[];
  matchDialog: boolean = false;

  submitted: boolean = false;
  match!: Match;
  constructor(
    private matchService: MatchService,
    private messageService: MessageService,
    private confirmationService: ConfirmationService
  ) {}

  ngOnInit() {
    // TODO: Add SDKs for Firebase products that you want to use
    // https://firebase.google.com/docs/web/setup#available-libraries

    // Your web app's Firebase configuration
    // For Firebase JS SDK v7.20.0 and later, measurementId is optional
    const firebaseConfig = {
      apiKey: 'AIzaSyDnNb2Jo_TRJKbkuWc3H8lhSBiDmgqCwI4',
      authDomain: 'betpowersport.firebaseapp.com',
      projectId: 'betpowersport',
      storageBucket: 'betpowersport.appspot.com',
      messagingSenderId: '802622139593',
      appId: '1:802622139593:web:b3393ae660bf5a968a7118',
      measurementId: 'G-YRR8YP8RZL',
    };

    // Initialize Firebase
    const app = initializeApp(firebaseConfig);
    const analytics = getAnalytics(app);
    this.getAllMatch();
  }
  getAllMatch() {
    this.matchService.getAllMatch().subscribe((data) => {
      this.matchs = data;
    });
  }
  openNew() {
    this.match = {};
    this.submitted = false;
    this.matchDialog = true;
  }

  saveMatch() {
    if (this.match.club1?.trim()) {
      if (this.match.idbetting) {
        var datePipe = new DatePipe('en-US');
        this.match.date = datePipe.transform(this.match.date, 'yyyy/MM/dd');
        this.matchService
          .updateMatch(this.match.idbetting, this.match)
          .subscribe((result: any) => {
            this.messageService.add({
              severity: 'success',
              summary: 'Successful',
              detail: 'Match Updated',
              life: 3000,
            });
            this.submitted = true;
            this.matchs = [...this.matchs];
            this.matchDialog = false;
            this.match = {};

            this.getAllMatch();
          });
      } else {
        var datePipe = new DatePipe('en-US');
        this.match.date = datePipe.transform(this.match.date, 'yyyy/MM/dd');
        this.matchService.addMatch(this.match).subscribe((result: any) => {
          this.messageService.add({
            severity: 'success',
            summary: 'Successful',
            detail: 'Match Created',
            life: 3000,
          });
          this.submitted = true;
          this.matchs = [...this.matchs];
          this.matchDialog = false;
          this.match = {};

          this.getAllMatch();
        });
      }
    }
  }

  hideDialog() {
    this.matchDialog = false;
    this.submitted = false;
  }

  editmatch(match: Match) {
    this.match = { ...match };
    this.matchDialog = true;
  }

  deletematch(match: Match) {
    this.confirmationService.confirm({
      message: 'Are you sure you want to delete ' + match.idbetting + '?',
      header: 'Confirm',
      icon: 'pi pi-exclamation-triangle',
      accept: () => {
        this.matchService.deleteMatch(match.idbetting).subscribe((res) => {
          this.messageService.add({
            severity: 'success',
            summary: 'Successful',
            detail: 'match Deleted',
            life: 3000,
          });
          this.getAllMatch();
        });
      },
    });
  }
}
