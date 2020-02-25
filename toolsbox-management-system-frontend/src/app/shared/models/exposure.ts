export enum Exposure {

    Acceptable = 'Acceptable',
    A_surveiller = 'A_surveiller',
    Trés_critique = 'Trés_critique'

}
export const ExposureLabel: Record<Exposure, string> = {
    [Exposure.Acceptable]: 'Acceptable',
    [Exposure.A_surveiller]: 'A surveiller',
    [Exposure.Trés_critique]: 'Trés critique'
};

