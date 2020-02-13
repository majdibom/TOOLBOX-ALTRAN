export enum Exposure {

    Low= 'Low',
    Nomral= 'Nomral',
    High= 'High'

}
export const ExposureLabel: Record<Exposure, string> = {
    [Exposure.Low]: 'Low',
    [Exposure.Nomral]: 'Medium',
    [Exposure.High]: 'High'
};

