import { Activity } from './activity';
import { DeliveryModel } from './delivery-model';

export class Project {

    idProject: number;
    title: string;
    activity: Activity = new Activity();
    deliveryModel: DeliveryModel = new DeliveryModel();

    constructor() { }
}
