export enum ExposureTitle {

    Acceptable = 'Acceptable',
    A_surveiller = 'A_surveiller',
    Trés_critique = 'Trés_critique'

}
export const ExposureTitleLabel: Record<ExposureTitle, string> = {
    [ExposureTitle.Acceptable]: 'Acceptable',
    [ExposureTitle.A_surveiller]: 'A surveiller',
    [ExposureTitle.Trés_critique]: 'Trés critique'
};

