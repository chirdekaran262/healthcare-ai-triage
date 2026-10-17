export type TriageLevel = 'RED' | 'YELLOW' | 'GREEN';

export interface HealthQueryRequest {
    symptoms: string;
    age?: number;
    biologicalSex?: string;
    knownConditions?: string;
}

export interface TriageResponse {
    triageLevel: TriageLevel;
    disclaimer: string;
    immediateActions: string[];
    careAdvice: string;
    followUpRecommendation: string;
}