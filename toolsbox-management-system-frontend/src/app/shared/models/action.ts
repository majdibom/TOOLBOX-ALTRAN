import { Status } from './status';
import { Priority } from './priority';
import { User } from './user';
import { TypeAction } from './type-action';
import { Origin } from './origin';
import { Process } from './process';
import { Gap } from './gap';

export class Action {

    id?: number;
    Description: string;
    effMesCriterion: string;
    openDate: Date;
    dueDate: Date;
    updatedDueDate: Date;
    effMesDate: Date;
    realisationDate: Date;
    comments: string;
    efficiency: string;
    status: Status;
    priority: Priority;
    responsibleAction: User;
    typeAction: TypeAction;
    origin: Origin;
    processImpacts?: Array<Process> = [];
    gap?: Gap;

    constructor() { }
}
