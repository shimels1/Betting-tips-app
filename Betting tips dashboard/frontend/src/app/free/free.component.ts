import { Component, OnInit } from '@angular/core';
import { ConfirmationService, MessageService } from 'primeng/api';
import { DatePipe } from '@angular/common';
import { Match } from '../model/Match';
import { MatchService } from '../service/match.service';
import { AutoCompleteCompleteEvent } from 'primeng/autocomplete';
import { Router } from '@angular/router';
@Component({
  selector: 'app-free',
  templateUrl: './free.component.html',
  styleUrls: ['./free.component.css'],
  providers: [MessageService, ConfirmationService],
})
export class FreeComponent implements OnInit {
  matchs!: Match[];
  matchDialogsave: boolean = false;
  matchDialogupdate: boolean = false;

  submitted: boolean = false;
  match!: Match;

  dateLists: any | undefined;
  curentDateIndex: number = 0;

  catagorys?: any | undefined;

  selectedCatagory: any | undefined;
  currentCatagory: any | undefined;

  constructor(
    private matchService: MatchService,
    private messageService: MessageService,
    private confirmationService: ConfirmationService,
    private router: Router
  ) {
    if (sessionStorage.getItem('id') == null) this.router.navigate(['/login']);
  }

  // Leag suggestion
  Leagsuggestions: any[] = [];
  selectedLeag: any[] = [];

  filterLeag(event: AutoCompleteCompleteEvent) {
    let filtered: any[] = [];
    let query = event.query;
    for (let i = 0; i < this.Leagsuggestions.length; i++) {
      let country = this.Leagsuggestions[i];
      if (country.toLowerCase().indexOf(query.toLowerCase()) == 0) {
        filtered.push(country);
      }
    }
    this.selectedLeag = filtered;
  }
  // Club1 suggestion
  Club1suggestions: any[] = [];
  selectedClub1: any[] = [];

  filterClub1(event: AutoCompleteCompleteEvent) {
    let filtered: any[] = [];
    let query = event.query;
    for (let i = 0; i < this.Club1suggestions.length; i++) {
      let country = this.Club1suggestions[i];
      if (country.toLowerCase().indexOf(query.toLowerCase()) == 0) {
        filtered.push(country);
      }
    }
    this.selectedClub1 = filtered;
  }
  // Club2 suggestion
  Club2suggestions: any[] = [];
  selectedClub2: any[] = [];

  filterClub2(event: AutoCompleteCompleteEvent) {
    let filtered: any[] = [];
    let query = event.query;
    for (let i = 0; i < this.Club2suggestions.length; i++) {
      let country = this.Club2suggestions[i];
      if (country.toLowerCase().indexOf(query.toLowerCase()) == 0) {
        filtered.push(country);
      }
    }
    this.selectedClub2 = filtered;
  }
  // Prediction suggestion
  Predictionsuggestions: any[] = [];
  selectedPrediction: any[] = [];

  filterPrediction(event: AutoCompleteCompleteEvent) {
    let filtered: any[] = [];
    let query = event.query;
    for (let i = 0; i < this.Predictionsuggestions.length; i++) {
      let country = this.Predictionsuggestions[i];
      if (country.toLowerCase().indexOf(query.toLowerCase()) == 0) {
        filtered.push(country);
      }
    }
    this.selectedPrediction = filtered;
  }

  ngOnInit() {
    this.matchService.getLeagLists().subscribe((leags) => {
      leags.forEach((element) => {
        this.Leagsuggestions?.push(element.league);
      });
    });
    this.matchService.getClubLists().subscribe((leags) => {
      leags.forEach((element) => {
        this.Club1suggestions?.push(element.club1);
      });
    });
    this.matchService.getClubLists().subscribe((leags) => {
      leags.forEach((element) => {
        console.log(element);
        this.Club2suggestions?.push(element.club1);
      });
    });
    this.matchService.getPredictionLists().subscribe((leags) => {
      leags.forEach((element) => {
        this.Predictionsuggestions?.push(element.prediction);
      });
    });
    // this.getAllMatch();
    this.catagorys = [
      { name: 'primeSafeTips' },
      { name: 'daily50odds' },
      { name: 'daily10odds' },
      { name: 'overUnderTips' },
      { name: 'doubleTips' },
      { name: 'singleGame' },
      { name: 'tennisTips' },
      { name: 'basketball' },
    ];
    this.currentCatagory = 'primeSafeTips';
    this.getDateList(this.currentCatagory);
  }
  getDateList(catagory: any) {
    this.dateLists = [];
    this.matchs = [];
    this.curentDateIndex = 0;
    this.matchService.getDateLists(this.currentCatagory).subscribe((res) => {
      this.dateLists = res;
      var currentDate = this.dateLists[this.curentDateIndex].date;
      var datePipe = new DatePipe('en-US');
      currentDate = datePipe.transform(currentDate, 'yyyy-MM-dd');
      this.getMatchByDate(currentDate, this.currentCatagory);
    });
  }
  getMatchByDate(date: any, catagory: any) {
    this.matchService
      .getDataByDateAndCatagory(date, catagory)
      .subscribe((data) => {
        this.matchs = data;
      });
  }
  openNew() {
    this.match = {};
    this.submitted = false;
    this.matchDialogsave = true;
    let today = new Date();
  }

  saveMatch() {
    if (this.match.club1?.trim()) {
      this.match.catagory = this.selectedCatagory.name;
      this.match.type = 'free';
      if (this.match.idbetting) {
      } else {
        var datePipe = new DatePipe('en-US');
        this.match.date = datePipe.transform(this.match.date, 'yyyy/MM/dd');
        // this.match.club1_score = 0;
        // this.match.club2_score = 0;
        // this.match.result = 'Waiting';

        if (this.match.result == null) this.match.result = 'Waiting';
        this.matchService.addMatch(this.match).subscribe((result: any) => {
          this.messageService.add({
            severity: 'success',
            summary: 'Successful',
            detail: 'Match Created',
            life: 3000,
          });
          this.submitted = true;
          this.matchs = [...this.matchs];
          this.matchDialogsave = false;
          this.match = {};

          var currentDate = this.dateLists[this.curentDateIndex].date;
          var datePipe = new DatePipe('en-US');
          currentDate = datePipe.transform(currentDate, 'yyyy/MM/dd');
          this.getMatchByDate(currentDate, this.currentCatagory);
        });
      }
    }
  }
  updateMatch() {
    if (this.match.club1?.trim()) {
      this.match.catagory = this.selectedCatagory.name;
      this.match.type = 'free';
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
            this.matchDialogupdate = false;
            this.match = {};
            var currentDate = this.dateLists[this.curentDateIndex].date;
            var datePipe = new DatePipe('en-US');
            currentDate = datePipe.transform(currentDate, 'yyyy/MM/dd');
            this.getMatchByDate(currentDate, this.currentCatagory);
          });
      } else {
      }
    }
  }

  hideDialog() {
    this.matchDialogupdate = false;
    this.matchDialogsave = false;
    this.submitted = false;
  }

  editmatch(match: Match) {
    this.match = { ...match };
    this.matchDialogupdate = true;
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
            detail: 'Match Deleted',
            life: 3000,
          });
          var currentDate = this.dateLists[this.curentDateIndex].date;
          var datePipe = new DatePipe('en-US');
          currentDate = datePipe.transform(currentDate, 'yyyy/MM/dd');
          this.getMatchByDate(currentDate, this.selectedCatagory);
        });
      },
    });
  }

  previous() {
    if (this.curentDateIndex == 0 || this.dateLists.length == 0) return;
    this.curentDateIndex = this.curentDateIndex - 1;
    var currentDate = this.dateLists[this.curentDateIndex].date;
    var datePipe = new DatePipe('en-US');
    currentDate = datePipe.transform(currentDate, 'yyyy/MM/dd');
    this.getMatchByDate(currentDate, this.currentCatagory);
  }

  next() {
    if (
      this.curentDateIndex == this.dateLists.length - 1 ||
      this.dateLists.length == 0
    )
      return;
    this.curentDateIndex = this.curentDateIndex + 1;
    var currentDate = this.dateLists[this.curentDateIndex].date;
    var datePipe = new DatePipe('en-US');
    currentDate = datePipe.transform(currentDate, 'yyyy/MM/dd');
    this.getMatchByDate(currentDate, this.currentCatagory);
  }

  selectedCat() {
    this.currentCatagory = this.selectedCatagory.name;
    console.log(this.currentCatagory);

    this.getDateList(this.currentCatagory);
    // this.matchService
    //   .getByDateAndCat('free', 'primeSafeTips')
    //   .subscribe((res) => {
    //     this.dateLists = res;
    //     console.log(this.selectedCatagory);
    //     var currentDate = this.dateLists[this.curentDateIndex].date;
    //     var datePipe = new DatePipe('en-US');
    //     currentDate = datePipe.transform(currentDate, 'yyyy-MM-dd');
    //     this.getMatchByDate(currentDate, 'primeSafeTips');
    //   });
  }
}
