export enum Status {

    OPEN= 'OPEN',
    CLOSED= 'CLOSED',
    DONE= 'DONE',
    BLOCKED= 'BLOCKED',
    DELAYED= 'DELAYED',
    CANCELLED= 'CANCELLED',

}
export const ActionstatusLabel: Record<Status, string> = {
    [Status.OPEN]: 'OPEN',
    [Status.CLOSED]: 'CLOSED',
    [Status.DONE]: 'DONE',
    [Status.BLOCKED]: 'BLOCKED',
    [Status.DELAYED]: 'DELAYED',
    [Status.CANCELLED]: 'CANCELLED',

};
