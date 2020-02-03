import { Audit } from './audit';
import { User } from './user';
import { Gap } from './gap';

export class AuditReport {

    id: number;
    realizationDate: Date;
    duration: number;
    examinedPoints: string;
    strongPoints: string;
    validationAuditor: string;
    validationAuditorDate: Date;
    validationAudited: string;
    validationAuditedDate: Date;
    audit: Audit;
    auditor: User;
    gaps: Array<Gap> = [];

    constructor() { }
}
