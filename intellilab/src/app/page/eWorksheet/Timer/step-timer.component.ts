
import {
  Component,
  ChangeDetectionStrategy,
  Input,
  Output,
  EventEmitter,
  ChangeDetectorRef,
  OnInit,
  OnDestroy
} from "@angular/core";

import { Observable, interval, Subscription } from "rxjs";
import { take } from "rxjs/operators";
import { map } from "rxjs/operators";
import { MatDialog } from '@angular/material/dialog';
import { DialogComponent } from '../Dialog/dialog.component'

@Component({
  selector: 'app-step-timer',
  templateUrl: './step-timer.component.html',
  styleUrls: ['./step-timer.component.css']
})
export class StepTimerComponent implements OnInit, OnDestroy {

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
  worker: Worker;
  initOverDueValue: number = 0;

  constructor(private changeDetector: ChangeDetectorRef, public dialog: MatDialog) {}

  ngOnInit(): void {
    console.log("parent config data for timer: ");
    console.log(this.parentConfig)

    this.initWebWorker()
    this.initTimerConfig();
    if(this.startDate){
      const currentDate = new Date().getTime();
      console.log("timer current date: " + currentDate);
      console.log("timer start date: " + this.startDate)

      if(currentDate - this.startDate < this.convertTime() * 1000){ // the date substraction will return a value with milliseconds
        this.calculateLaunchTime();
        console.log("timer is launched during initialization")
        console.log("timer spent since last launch is " + this.formatValue(Math.round(currentDate - this.startDate)/1000) )
      }else if(currentDate - this.startDate > this.convertTime() * 1000){
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
  }

  initRAF(){
    let   rAF_ID
    const rAFCallback = function(callback) {
      let count = callback;
      // console.log(`New frame: ${count}`)
      rAF_ID = requestAnimationFrame( rAFCallback );
    }

    // request animation frame on render
    rAF_ID = requestAnimationFrame( rAFCallback );
  }


  initWebWorker(){
    if(this.worker){
      this.worker.terminate()
    }
    if (typeof Worker !== 'undefined') {
      // Create a new
      console.log('---> New timer is initialized <---')
      this.worker = new Worker(new URL('../Webworks/countdown-timer.worker', import.meta.url));
      this.worker.onmessage = (event) => {
        // console.log(`page got message: ${event.data}`);
        const count = event.data

        if(!this.showOverDueTime){
          const v = this.startAt - count
          this.currentValue = this.formatValue(v);

          if(v === 0){
            this.stopTimer();
            this.playAudio();
            this.alert();
            this.overDueTimeCounter();
          }
        }else{
          this.overDueValue = count + this.initOverDueValue;
          this.overDueStr = this.formatValue(this.overDueValue);
        }

      };

    }
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
    this.worker.postMessage('start timer');
  }

  overDueTimeCounter(initOverDueTime = 0){
    this.initOverDueValue = initOverDueTime
    console.log("initial overdue time value is: " + this.formatValue(this.initOverDueValue))
    this.showOverDueTime = true

    console.log(`Current timer is overdue, and terminated.`)
    this.worker.terminate()
    this.initWebWorker()
    this.worker.postMessage('start timer');
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

  public stop() {
    this.firstStart = true;
    this.timeArr = ["00", "00", "00"];
  }

  public pause() {
    this.counterSub.unsubscribe();
    this.startAt = this.pauseAt;
  }


  onKeySecond(event: any) {
    let temp = parseInt(event.target.value);
    this.secValue = temp;
  }

  onKeyMin(event: any) {
    let temp = parseInt(event.target.value);
    this.minValue = temp;
  }

  onKeyHr(event: any) {
    let temp = parseInt(event.target.value);
    this.hrValue = temp;
  }

  startTimer() {
    if (!this.isStarted) {
      this.playAttentionSound()
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
    audio.src = "../../../../assets/eWorksheet/timerAlarmSound.mp3";
    audio.load();
    audio.play();
  }

  playAttentionSound(){
    let audio = new Audio();
    audio.src = "../../../../assets/eWorksheet/attention_ding.wav";
    audio.load();
    audio.play();
  }

  alert() {
    console.log("timer parent config data inside of alert function: ");
    console.log(this.parentConfig)

    setTimeout(this.openDialog, 5000, this.parentConfig, this.dialog)

  }

  killTimer(){
    this.worker.terminate()
    if(this.overDueValue){
      this.timerValue = this.convertTime() + this.overDueValue;
    }else{
      this.timerValue = this.convertTime()
    }

    this.sendTime.emit({timerValue: this.timerValue});

    console.log("Timer is killed, timer's default value: " + this.receiveTime + " " + this.receiveTimeUnit + "; over due time: " + this.overDueValue + " secs" + "; total timer value: " + this.timerValue + " secs");
    this.displayTime.emit({timeValue: 0, overdue:false, display: false});
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
    }
  }

  openDialog(parentConfig, dialog) {
    console.log("Timer dialog parentConfig body: ")
    console.log(parentConfig)
    const message = `Step ${parentConfig.stepIndex} (${parentConfig.stepType})'s timer for incubation ${parentConfig.displayIndex} times up !!!`
    dialog.open(DialogComponent, {data: {message: message, disableChoice: true}});
  }

  setReceiveTime(timeValue){
    console.log("setReceiveTime is called with parameter: " + timeValue)
    if(!this.isStarted){
      this.receiveTime = timeValue;
      this.initTimerConfig();
    }
  }

  setReceiveTimeUnit(timeUnit){
    console.log("setReceiveTimeUnit is called with parameter: " + timeUnit)
    if(!this.isStarted){
      this.receiveTimeUnit = timeUnit;
      this.initTimerConfig();
    }
  }

  ngOnDestroy(): void {
    if(this.worker){
      this.worker.terminate()
    }
    if(this.counterSub){
      this.counterSub.unsubscribe()
    }

    if(this.timerSub){
      this.timerSub.unsubscribe()
    }
  }
}
