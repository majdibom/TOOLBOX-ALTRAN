export enum RiskNature {

    Risk= 'Risk',
    Opportunity= 'Opportunity'
  
}
export const RiskNatureLabel: Record<RiskNature, string> = {
    [RiskNature.Risk]: 'Risk',
    [RiskNature.Opportunity]: 'Opportunity'
    

};