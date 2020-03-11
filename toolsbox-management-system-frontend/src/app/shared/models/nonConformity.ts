export enum NonConformity {

    NA = 'NA',
    Yes = 'Yes',
    No = 'No',
}
export const NonConformityLabel: Record<NonConformity, string> = {
    [NonConformity.NA]: 'NA',
    [NonConformity.Yes]: 'Yes',
    [NonConformity.No]: 'No',
};