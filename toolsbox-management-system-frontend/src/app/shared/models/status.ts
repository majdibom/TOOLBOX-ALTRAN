export enum Status {

    Open= 'Open',
    Closed= 'Closed'

}
export const ActionstatusLabel: Record<Status, string> = {
    [Status.Open]: 'Open',
    [Status.Closed]: 'Closed',
};
