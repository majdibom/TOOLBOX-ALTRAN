import { Action } from './action';
import { AuditReport } from './audit-report';
import { TypeGap } from './type-gap';

export class Gap {

    id?: number;
    description: string;
    justification: string;
    improvementClue: string;
    identifiedCauses: string;
    auditReport?: AuditReport;
    actions?: Array<Action> = [];
    typeGap: TypeGap;

    constructor() { }
}
