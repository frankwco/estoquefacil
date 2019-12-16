export interface IIgreja {
  id?: number;
  nomeLocalidade?: string;
  numeroLocalidade?: string;
}

export class Igreja implements IIgreja {
  constructor(public id?: number, public nomeLocalidade?: string, public numeroLocalidade?: string) {}
}
