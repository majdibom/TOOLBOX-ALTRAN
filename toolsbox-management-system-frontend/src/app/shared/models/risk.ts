
import { Probability } from './probability';
import { Severity } from './severity';
import { Exposure } from './exposure';
import { RiskStatus } from './risk-status';
import { Action } from './action';
import { RiskNature } from './risk-nature';
import { Priority } from './priority';
import { RiskStrategy } from './risk-strategy';
import { User } from './user';

export class Risk {

    id: number;
    probability: Probability;
    severity: Severity;
    exposure: Exposure;
    riskNature: RiskNature;
    riskPriority: Priority;
    riskStrategy: RiskStrategy;
    riskPilote: User;
    riskStatus: RiskStatus;
    detectionDate: Date;
    closureDate: Date;
    impact: String;
    factorsIdentif: String;
    actions: Array<Action> = [];
    contingencyPlan: Array<Action> = [];
    mitigationApproach: Array<Action> = [];
    createdBy: User;
    createdAt: Date;
    updatedAt: Date;
    constructor() { }
}
