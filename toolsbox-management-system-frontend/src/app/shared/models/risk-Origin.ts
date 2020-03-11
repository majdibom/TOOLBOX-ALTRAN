export enum RiskOrigin {

    Projet= 'Projet',
    Process= 'Process'
  
}
export const RiskOriginLabel: Record<RiskOrigin, string> = {
    [RiskOrigin.Projet]: 'Projet',
    [RiskOrigin.Process]: 'Process'
    

};