import { Role } from './role';
import { Activity } from './activity';

export class User {

    id: number;
    firstName: String;
    lastName: String;
    email: String;
    address: String;
    phoneNumber: any;
    birthDate: any;
    username: string;
    password: string;
    roles: Array<Role> = [];
    activity: Activity = new Activity();
    activated: any;

    constructor() { }

}
