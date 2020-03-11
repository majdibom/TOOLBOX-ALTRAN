export enum Cost {

    NA = 'NA',
    Yes = 'Yes',
    No = 'No',
}
export const CostLabel: Record<Cost, string> = {
    [Cost.NA]: 'NA',
    [Cost.Yes]: 'Yes',
    [Cost.No]: 'No',
};