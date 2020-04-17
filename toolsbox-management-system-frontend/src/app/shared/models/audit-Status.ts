export enum AuditStatus {

    Planned = 'Planned',
    Executed = 'Executed',
    Closed = 'Closed'

}
export const AuditStatusLabel: Record<AuditStatus, string> = {
    [AuditStatus.Planned]: 'Planned',
    [AuditStatus.Executed]: 'Executed',
    [AuditStatus.Closed]: 'Closed'

};