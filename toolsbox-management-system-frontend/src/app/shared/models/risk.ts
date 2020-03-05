
import { Probability } from './probability';
import { Severity } from './severity';
import { RiskStatus } from './risk-status';
import { Action } from './action';
import { RiskNature } from './risk-nature';
import { Priority } from './priority';
import { RiskStrategy } from './risk-strategy';
import { User } from './user';
import { RiskAction } from './risk-Action';
import { Exposures } from './exposures';

export class Risk {

    id?: number;
    probability: Probability;
    severity: Severity;
    exposures: Array<Exposures> = [];
    exposureValue: number;
    riskNature: RiskNature;
    riskPriority: Priority;
    riskStrategy: RiskStrategy;
    riskPilote: User;
    riskStatus: RiskStatus;
    detectionDate: Date;
    closureDate: Date;
    impact: String;
    factorsIdentif: String;
    actions: Array<RiskAction> = [];
    contingencyPlan: Array<Action> = [];
    mitigationApproach: Array<Action> = [];
    
    constructor() { }
}
