export enum RiskStatus {

    Open = 'Open',
    Closed = 'Closed'
}

export const RiskStatusLabel: Record<RiskStatus, string> = {
    [RiskStatus.Open]: 'Open',
    [RiskStatus.Closed]: 'Closed',
};
