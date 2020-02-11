import { ActionStatus } from './action-Status';
import { Priority } from './priority';
import { User } from './user';
import { TypeAction } from './type-action';
import { Origin } from './origin';
import { Process } from './process';
import { Gap } from './gap';

export class Action {

    id?: number;
    description: string;
    effMeasCriterion: string;
    openDate: Date;
    dueDate: Date;
    updatedDueDate: Date;
    effMeasDate: Date;
    realizationDate: Date;
    comments: string;
    responsibleAction: User;
    processImpacts?: Array<Process> = [];
    gap?: Gap;
    actionStatus: ActionStatus;
    priority: Priority;
    typeAction: TypeAction;
    origin: Origin;

    constructor() { }
}
