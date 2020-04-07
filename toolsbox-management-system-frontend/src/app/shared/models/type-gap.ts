export enum TypeGap {

    Major_gaps = 'Major_gaps',
    Minor_gaps = 'Minor_gaps',
    Sensitive_points = 'Sensitive_points',
    Improvements_track = 'Improvements_track'

}
export const TypeGapLabel: Record<TypeGap, string> = {
    [TypeGap.Major_gaps]: 'Major_gaps',
    [TypeGap.Minor_gaps]: 'Minor_gaps',
    [TypeGap.Sensitive_points]: 'Sensitive_points',
    [TypeGap.Improvements_track]: 'Improvements_track',

};