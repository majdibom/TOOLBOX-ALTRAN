export enum Severity {

    Mineur = 'Mineur',
    Moyen = 'Moyen',
    Important = 'Important',
    Trés_Important = 'Trés_Important'

}

export const SeverityLabel: Record<Severity, string> = {
    [Severity.Mineur]: 'Mineur',
    [Severity.Moyen]: 'Moyen',
    [Severity.Important]: 'Important',
    [Severity.Trés_Important]: 'Trés_Important',
};
