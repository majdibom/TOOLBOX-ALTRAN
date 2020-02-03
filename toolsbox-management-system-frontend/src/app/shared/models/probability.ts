export enum Probability {

    Unlikely = 'Unlikely',
    Likely = 'Likely',
    Very_Likely = 'Very_Likely',
    Almost_Sure = 'Almost_Sure'

}

export const ProbabilityLabel: Record<Probability, string> = {
    [Probability.Unlikely]: 'Unlikely',
    [Probability.Likely]: 'Likely',
    [Probability.Very_Likely]: 'Very Likely',
    [Probability.Almost_Sure]: 'Almost Sure',
};
