export enum RiskStatus {

    Open = 'Open',
    On_Hold = 'On_Hold',
    Canseled = 'Canseled',
    Closed = 'Closed'
}

export const RiskStatusLabel: Record<RiskStatus, string> = {
    [RiskStatus.Open]: 'Open',
    [RiskStatus.On_Hold]: 'On Hold',
    [RiskStatus.Canseled]: 'Canseled',
    [RiskStatus.Closed]: 'Closed',
};
