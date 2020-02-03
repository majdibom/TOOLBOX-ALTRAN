import { Activity } from './activity';
import { DeliveryModel } from './delivery-model';

export class Project {

    id: number;
    title: string;
    activity: Activity;
    deliveryModel: DeliveryModel;
    constructor() { }
}
