import { RiskActionId } from './risk-Action-Id';
import { Risk } from './risk';
import { Action } from './action';

export class RiskAction {

    id: RiskActionId = new RiskActionId;
    risk: Risk = new Risk();
    action: Action = new Action();

    constructor() { }
}