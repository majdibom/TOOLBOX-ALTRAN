export enum Probability {

    Faible = 'Faible',
    Moyenne = 'Moyenne',
    Très_probable = 'Très_probable',
    Presque_sur = 'Presque_sur'

}

export const ProbabilityLabel: Record<Probability, string> = {
    [Probability.Faible]: 'Faible',
    [Probability.Moyenne]: 'Moyenne',
    [Probability.Très_probable]: 'Très probable',
    [Probability.Presque_sur]: 'Presque sur',
};
