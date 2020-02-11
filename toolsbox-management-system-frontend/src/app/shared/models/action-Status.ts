export enum ActionStatus {

    Open= 'Open',
    Closed= 'Closed',
    Done= 'Done',
    Blocked= 'Blocked',
    Delayed= 'Delayed',
    Cancelled= 'Cancelled',

}
export const ActionstatusLabel: Record<ActionStatus, string> = {
    [ActionStatus.Open]: 'Open',
    [ActionStatus.Closed]: 'Closed',
    [ActionStatus.Done]: 'Done',
    [ActionStatus.Blocked]: 'Blocked',
    [ActionStatus.Delayed]: 'Delayed',
    [ActionStatus.Cancelled]: 'Cancelled',

};
