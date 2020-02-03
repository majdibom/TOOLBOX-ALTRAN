export enum Priority {

    Low= 'Low',
    Nomral= 'Nomral',
    High= 'High'

}
export const PriorityLabel: Record<Priority, string> = {
    [Priority.Low]: 'Low',
    [Priority.Nomral]: 'Nomral',
    [Priority.High]: 'High'
};
