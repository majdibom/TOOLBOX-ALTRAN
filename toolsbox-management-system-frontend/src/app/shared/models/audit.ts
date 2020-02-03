import { AuditReference } from './audit-reference';
import { Week } from './week';
import { Project } from './project';
import { Process } from './process';
import { User } from './user';
import { AuditReport } from './audit-report';

export class Audit {

    id: number;
    risks: string;
    duration: number;
    issues: string;
    auditTheme: string;
    reference: string;
    week: Week;
    project: Project;
    process: Process;
    processImpacts: Array<Process> = [];
    primaryAuditor: User;
    accompanyingAuditor: User;
    audited: Array<User> = [];
    auditReport: AuditReport;

    constructor() { }
}
