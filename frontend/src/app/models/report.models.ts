export type MarkerStatus = 'NORMAL' | 'ATTENTION_NEEDED' | 'CRITICAL';

export interface LabMarkerAnalysis {
    markerName: string;
    reportedValue: string;
    referenceRange: string;
    status: MarkerStatus;
    plainLanguageExplanation: string;
}

export interface ReportAnalysisResponse {
    executiveSummary: string;
    reportType: string;
    keyFindings: LabMarkerAnalysis[];
    lifestyleAndCareNotes: string[];
    questionsForYourDoctor: string[];
    nextSteps: string;
    clinicalDisclaimer: string;
}
