export class UserHistoryV2 {
  public userHistoryId: number;
  public activityTime: Date;
  public username: String;
  public component: String;
  public activity: String;
  public description: String;

  static copy(userHistory: UserHistoryV2) {
    return { ...userHistory } as UserHistoryV2;
  }

  public constructor(init?: Partial<UserHistoryV2>) {
    Object.assign(this, init);
  }
}
