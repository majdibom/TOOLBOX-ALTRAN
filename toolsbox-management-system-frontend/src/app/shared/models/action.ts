import { Status } from './status';
import { Priority } from './priority';
import { User } from './user';
import { TypeAction } from './type-action';
import { Origin } from './origin';
import { Process } from './process';
import { Gap } from './gap';

export class Action {

    id?: number;
    identificationDate: Date;
    deadline: Date;
    realizationDate: Date;
    percentage: number;
    cause: string;
    performanceCriteria: string;
    efficiencyCriteria: string;
    initialValue: string;
    finalValue: string;
    efficiency: string;
    remarks: string;
    notes: string;
    status: Status;
    priority: Priority;
    responsibleAction: User;
    typeAction: TypeAction;
    origin: Origin;
    processImpacts?: Array<Process> = [];
    gap?: Gap;

    constructor() { }
}
