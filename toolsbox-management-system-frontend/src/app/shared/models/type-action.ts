export enum TypeAction {

    Préventive= 'Préventive',
    Curative= 'Curative',
    Amélioration= 'Amélioration',
    Corrective= 'Corrective'

}
export const TypeActionLabel: Record<TypeAction, string> = {
    [TypeAction.Préventive]: 'Préventive',
    [TypeAction.Curative]: 'Curative',
    [TypeAction.Amélioration]: 'Amélioration',
    [TypeAction.Corrective]: 'Corrective'
};
