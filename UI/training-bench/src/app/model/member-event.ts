export class MemberEvent {
  id!: string;
  op!: string;
  ts_us: number = 0;
  ts_ns: number = 0;
  ts_Ms: number = 0;
  after!: MemberEvent.After;
}

export namespace MemberEvent {
  export class After {
    id!: number;
    email!: string;
    isactive!: boolean;
    nom!: string;
    timestamp!: number;
  }
}