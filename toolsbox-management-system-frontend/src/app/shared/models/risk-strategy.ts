export enum RiskStrategy {

    Acceptation = 'Acceptation',
    Eradication = 'Eradication',
    Réduction = 'Réduction'
}

export const RiskStrategyLabel: Record<RiskStrategy, string> = {
    [RiskStrategy.Acceptation]: 'Acceptation',
    [RiskStrategy.Eradication]: 'Eradication',
    [RiskStrategy.Réduction]: 'Réduction',
};
