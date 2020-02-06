export enum TypeAction {

    Préventive= 'Préventive',
    Protection= 'Protection',
    Amélioration= 'Amélioration',
    Transfert= 'Transfert'

}
export const TypeActionLabel: Record<TypeAction, string> = {
    [TypeAction.Préventive]: 'Préventive',
    [TypeAction.Protection]: 'Protection',
    [TypeAction.Amélioration]: 'Amélioration',
    [TypeAction.Transfert]: 'Transfert'
};
