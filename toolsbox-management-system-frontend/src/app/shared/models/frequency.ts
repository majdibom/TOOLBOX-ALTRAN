export enum Frequency {

    Weekly = 'Weekly',
    Monthly = 'Monthly',
    Quarterly = 'Quarterly',
    Yearly = 'Yearly'
}
export const FrequencyLabel: Record<Frequency, string> = {
    [Frequency.Weekly]: 'Weekly',
    [Frequency.Monthly]: 'Monthly',
    [Frequency.Quarterly]: 'Quarterly',
    [Frequency.Yearly]: 'Yearly'
};