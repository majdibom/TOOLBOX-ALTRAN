
import { Probability } from './probability';
import { Severity } from './severity';
import { Exposure } from './exposure';
import { RiskStatus } from './risk-status';
import { Action } from './action';

export class Risk {

    id: number;
    probability: Probability;
    severity: Severity;
    exposure: Exposure;
    riskStatus: RiskStatus;
    detectionDate: Date;
    closureDate: Date;
    strategy: String;
    actions: Array<Action> = [];
    contingencyPlan: Array<Action> = [];
    mitigationApproach: Array<Action> = [];

    constructor() { }
}
