import { ChangeDetectorRef, Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { disconnect } from 'process';
import { interval, Observable, Subscription } from 'rxjs';
import { take, map } from 'rxjs/operators';
import { DialogComponent } from '../../Dialog/dialog.component';

@Component({
  selector: 'app-progress-timer',
  templateUrl: './progress-timer.component.html',
  styleUrls: ['./progress-timer.component.css']
})
export class ProgressTimerComponent implements OnInit {

  progressBars;
  completeness;
  timerValue: number = 0;
  pauseAt: number = 0;
  timeArr = ["00", "00", "00"];
  secValue = 0;
  hrValue = 0;
  minValue = 0;
  pauseValue = false;
  isStarted = false;
  firstStart = true;
  showOverDueTime: boolean = false;
  showStartButton: boolean = true;
  showStopButton: boolean = false;
  overDueValue: number;
  overDueStr: string = "00:00:00";


  @Input() barIndex = 0
  @Input() parentConfig;
  @Input() startDate: number = null;
  @Input() receiveTime: number;
  @Input() receiveTimeUnit: string;
  // @Input() displayIndex: number;
  @Input() stepIndex;

  @Input()
  stepName;

  @Input()
  startAt = 0;

  @Input()
  showTimeRemaining = true;

  @Output()
  counterState = new EventEmitter();

  @Output()
  launchDate = new EventEmitter();

  @Output()
  sendTime = new EventEmitter<{timerValue:number}>();

  @Output()
  displayTime = new EventEmitter();

  // @Output()
  // isRunning = new EventEmitter();

  private currentValue = "";


  private counterSub: Subscription;
  private timerSub: Subscription;

  constructor(private changeDetector: ChangeDetectorRef, public dialog: MatDialog) {}

  ngOnInit() {
    this.progressBars = document.querySelectorAll('.progress-done');
    // this.completeness = 70;
    // this.progressBar = document.querySelector('.progress-done');
    // this.progressBar.style.width = this.completeness + '%';
    // this.progressBar.style.opacity = 1;



    console.log("parent config data for timer: ");
    console.log(this.parentConfig)
    // console.log("TIMER DISPLAY INDEX: " + this.displayIndex)

    this.initTimerConfig();
    if(this.startDate){
      const currentDate = new Date().getTime();
      console.log("timer current date: " + currentDate);
      console.log("timer start date: " + this.startDate)

      if(currentDate - this.startDate < this.convertTime() * 1000){ // the date substraction will return a value with milliseconds
        this.calculateLaunchTime();
        console.log("timer is launched during initialization")
        console.log("timer spent since last launch is " + this.formatValue(Math.round(currentDate - this.startDate)/1000) )
        // console.log("timer spent since last launch is " + Math.round(currentDate - this.startDate)/1000 + " seconds." )
      }else if(currentDate - this.startDate > this.convertTime() * 1000){
        // this.pauseValue = false;
        // this.isStarted = false;
        this.showOverDueTime = true;
        this.showStopButton = true;
        this.showStartButton = false;
        const loadDate = new Date().getTime();
        const timerTime = this.convertTime();
        const timeSpent = Math.round((loadDate - this.startDate)/1000);
        const initOverDueValue = timeSpent - timerTime;
        this.overDueTimeCounter(initOverDueValue);

      }
    }


    // manaully start a timer for devlopment with the following code
    this.minValue = 1;
    this.startTimer();


  }








  initTimerConfig(){
    this.secValue = 0;
    this.hrValue = 0;
    this.minValue = 0;
    if(this.receiveTime && this.receiveTimeUnit){
      switch(this.receiveTimeUnit){
        case 'hr':
          this.hrValue = this.receiveTime;
          break;

        case 'min':
          this.minValue = this.receiveTime;
          break;

        case 'sec':
          this.secValue = this.receiveTime;
          break;
      }
    }
  }



  public start() {
    this.currentValue = this.formatValue(this.startAt);
    this.changeDetector.detectChanges();
    if (this.firstStart) {
      this.timerValue = this.startAt;
      this.firstStart = false;
    }

    const t: Observable<number> = interval(1000);
    this.counterSub = t
      .pipe(take(this.startAt))
      .pipe(map(v => this.startAt - (v + 1)))
      .subscribe(
        v => {
          // progress done code for development
          this.calcProgress(this.startAt, this.startAt - v)


          this.currentValue = this.formatValue(v);
          this.changeDetector.detectChanges();
        },
        err => {
          this.counterState.error(err);
        },
        () => {
          this.stopTimer();
          this.playAudio();
          this.alert();
          this.overDueTimeCounter();

        }
      );
  }

  overDueTimeCounter(initOverDueTime = 0){
    this.progressBars[this.barIndex].style.background = '#33cd52'

    console.log("initial overdue time value is: " + this.formatValue(initOverDueTime))
    this.showOverDueTime = true
    // this.isRunning.emit({isRunning: true, isOverDue: this.showOverDueTime})
    const overDue = interval(1000);
    this.timerSub = overDue.pipe(map(v => v + 1)).subscribe(v => {
      this.overDueValue = v + initOverDueTime;
      this.overDueStr = this.formatValue(this.overDueValue);
      this.changeDetector.detectChanges();
    })
  }

  private formatValue(v) {
    this.pauseAt = v;
    const hours = Math.floor(Math.floor(v / 60) / 60);
    const formattedHours = "" + (hours > 9 ? hours : "0" + hours);
    const minutes = Math.floor(v / 60) % 60;
    const formattedMinutes = "" + (minutes > 9 ? minutes : "0" + minutes);
    const seconds = v % 60;
    const formattedSeconds = "" + (seconds > 9 ? seconds : "0" + seconds);
    if(!this.showOverDueTime){
      this.timeArr = [formattedHours, formattedMinutes, formattedSeconds];
    }

    const displayValue = `${formattedHours}:${formattedMinutes}:${formattedSeconds}`
    this.displayTime.emit({timeValue: displayValue, overdue:this.showOverDueTime, display: true});
    return displayValue;
  }

  calcProgress(total, done){
    const ratio = Math.round((done / total) * 100)
    this.completeness = ratio;
    this.progressBars[this.barIndex].style.width = ratio + '%';
  }

  public stop() {
    this.counterSub.unsubscribe();
    this.firstStart = true;
    // this.currentValue = "00:00:00";
    // this.counterState.emit("ABORTED");
    this.timeArr = ["00", "00", "00"];
  }

  public pause() {
    this.counterSub.unsubscribe();
    this.startAt = this.pauseAt;
  }

  // counterState = "counter is ticking";

  onKeySecond(event: any) {
    // without type info

    let temp = parseInt(event.target.value);
    this.secValue = temp;
  }

  onKeyMin(event: any) {
    // without type info

    let temp = parseInt(event.target.value);
    this.minValue = temp;
  }

  onKeyHr(event: any) {

    let temp = parseInt(event.target.value);
    this.hrValue = temp;
  }

  startTimer() {
    if (!this.isStarted) {
      console.log("second value: " + this.secValue);
      console.log("minute value: " + this.minValue);
      console.log("hour value: " + this.hrValue);

      if (isNaN(this.secValue)) {
        this.secValue = 0;
        console.log("changed sec: " + this.secValue);
      }

      if (isNaN(this.minValue)) {
        this.minValue = 0;
        console.log("changed min: " + this.minValue);
      }

      if (isNaN(this.hrValue)) {
        this.hrValue = 0;
        console.log("changed hr: " + this.hrValue);
      }

      if (this.pauseValue == false) {
        this.startAt = this.secValue + this.minValue * 60 + this.hrValue * 3600;
        console.log("startAt value: " + this.startAt + " seconds");
      }

      this.start();
      this.isStarted = true;
      this.showStartButton = false;
      // this.isRunning.emit({isRunning: true, isOverDue: false})
      this.launchDate.emit({startDate: new Date().getTime()})
    }
  }

  convertTime(): number{
    let newTime = 0;
    switch(this.receiveTimeUnit){
      case  'sec':
        newTime = this.receiveTime;
        break;

      case  'min':
        newTime = this.receiveTime * 60;
        break;

      case  'hr':
        newTime = this.receiveTime * 3600;
        break;
    }
    return newTime;
  }

  pauseTimer() {
    this.pause();
    this.pauseValue = true;
    this.isStarted = false;
  }

  stopTimer() {
    this.stop();
    this.pauseValue = false;
    this.isStarted = false;
    this.showStopButton = true;
  }

  playAudio() {
    let audio = new Audio();
    // audio.muted = true;
    audio.src = "../../../../../assets/eWorksheet/timerAlarmSound.mp3";
    audio.load();
    audio.play();
  }

  alert() {
    console.log("timer parent config data inside of alert function: ");
    console.log(this.parentConfig)

    setTimeout(this.openDialog, 5000, this.parentConfig, this.dialog)

  }

  killTimer(){
    if(this.overDueValue){
      this.timerValue = this.convertTime() + this.overDueValue;
    }else{
      this.timerValue = this.convertTime()
    }

    this.sendTime.emit({timerValue: this.timerValue});

    console.log("Timer is killed, timer's default value: " + this.receiveTime + " " + this.receiveTimeUnit + "; over due time: " + this.overDueValue + " secs" + "; total timer value: " + this.timerValue + " secs");
    this.timerSub.unsubscribe();
    this.displayTime.emit({timeValue: 0, overdue:false, display: false});
    // this.isRunning.emit({isRunning: false, isOverDue: false, isComplete: true})
  }

  calculateLaunchTime(){
    const loadDate = new Date().getTime();
    const timerTime = this.convertTime();
    const timeSpent = Math.round((loadDate - this.startDate)/1000);
    if(timeSpent >= timerTime){
      const initOverDueValue = timeSpent - timerTime;
      this.overDueTimeCounter(initOverDueValue);
    }else{
      const launchTime = timerTime - timeSpent;
      this.startAt = launchTime;
      this.start();
      this.isStarted = true;
      this.showStartButton = false;
      // this.isRunning.emit({isRunning: true, isOverDue: this.showOverDueTime})
    }
  }

  openDialog(parentConfig, dialog) {
    console.log("Timer dialog parentConfig body: ")
    console.log(parentConfig)
    // console.log("TIMER DISPLAY INDEX in the openDialog function: " + this.parentConfig.displayIndex)
    const message = `Step ${parentConfig.stepIndex} (${parentConfig.stepType})'s timer for incubation ${parentConfig.displayIndex} times up !!!`
    dialog.open(DialogComponent, {data: {message: message, disableChoice: true}});
  }

  setReceiveTime(timeValue){
    console.log("setReceiveTime is called with parameter: " + timeValue)
    if(!this.isStarted){
      this.receiveTime = timeValue;
      // this.changeDetector.detectChanges()
      this.initTimerConfig();
    }
  }

  setReceiveTimeUnit(timeUnit){
    console.log("setReceiveTimeUnit is called with parameter: " + timeUnit)
    if(!this.isStarted){
      this.receiveTimeUnit = timeUnit;
      // this.changeDetector.detectChanges()
      this.initTimerConfig();
    }
  }

  ngOnDestroy(): void {
    if(this.counterSub){
      this.counterSub.unsubscribe()
    }

    if(this.timerSub){
      this.timerSub.unsubscribe()
    }
  }


}
