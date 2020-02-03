export enum Severity {

    Significant_Problem = 'Significant_Problem',
    Serious_Problem = 'Serious_Problem',
    Worrying_Situation = 'Worrying_Situation',
    Crisis = 'Crisis'

}

export const SeverityLabel: Record<Severity, string> = {
    [Severity.Significant_Problem]: 'Significant Problem',
    [Severity.Serious_Problem]: 'Serious Problem',
    [Severity.Worrying_Situation]: 'Worrying Situation',
    [Severity.Crisis]: 'Crisis',
};
