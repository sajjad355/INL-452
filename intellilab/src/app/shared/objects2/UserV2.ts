export class UserV2 {
  public userId = -1;
  public name = '';
  public password = '';
  public email = '';
  public invalidlogincount = 0;
  public active = false;
  public editedBy: string;
  // public jwt: string;
  public modifiedOn = new Date();
  public userRoles: UserRoleV2[] = [];

  public static copy(user: UserV2) {
    const newUser = {
      ...user,
    } as UserV2;
    if ( user.userRoles ) {
      newUser.userRoles = user.userRoles.map(r => UserRoleV2.copy(r));
    }
    return newUser;
  }

  static hasRole(user: UserV2, roleName: string): boolean {
    return user.userRoles.some( role => role.roleName == roleName );
  }

  static isAllowedToPerform(user: UserV2, requestedOperation: string): boolean {
    return user.userRoles.some(role => role.operations.some(operation => operation.operationName == requestedOperation));
  }

  constructor(init?: Partial<UserV2>) {
    Object.assign(this, init);
  }
}

export class UserRoleV2 {
  public userRoleId: number;
  public roleName = '';
  public operations: OperationV2[] = [];

  public static copy( userRole: UserRoleV2 ) { return { ...userRole }; }

  constructor( init?: Partial<UserRoleV2> ) { Object.assign(this, init); }
}

export class OperationV2 {
  public operationId = -1;
  public operationName = '';


  public static copy( operation: OperationV2 ) { return { ...operation }; }

  constructor( init?: Partial<OperationV2>) { Object.assign(this, init); }
}
