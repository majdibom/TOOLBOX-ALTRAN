export enum Origin {

    Audit= 'Audit',
    Risk= 'Risk'

}
export const OriginLabel: Record<Origin, string> = {
    [Origin.Audit]: 'Audit',
    [Origin.Risk]: 'Risk',
};
