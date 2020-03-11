export enum Satisfaction {

    NA = 'NA',
    Yes = 'Yes',
    No = 'No',
}
export const SatisfactionLabel: Record<Satisfaction, string> = {
    [Satisfaction.NA]: 'NA',
    [Satisfaction.Yes]: 'Yes',
    [Satisfaction.No]: 'No',
};