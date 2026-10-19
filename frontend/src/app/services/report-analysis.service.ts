import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Injectable, inject } from '@angular/core';
import { Observable, catchError, throwError } from 'rxjs';
import { ReportAnalysisResponse } from '../models/report.models';

@Injectable({
    providedIn: 'root'
})
export class ReportAnalysisService {
    private readonly http = inject(HttpClient);
    private readonly apiUrl = '/api/v1/reports/analyze';

    analyzeReport(file: File, patientContext: string): Observable<ReportAnalysisResponse> {
        const formData = new FormData();
        formData.append('file', file);
        if (patientContext.trim()) {
            formData.append('patientContext', patientContext.trim());
        }

        return this.http.post<ReportAnalysisResponse>(this.apiUrl, formData).pipe(
            catchError(this.handleError)
        );
    }

    private handleError(error: HttpErrorResponse): Observable<never> {
        let errorMessage = 'We could not analyze this report. Please try again.';
        if (error.status === 0) {
            errorMessage = "We couldn't connect to the health service. Check your connection and try again.";
        } else if (error.status === 400) {
            errorMessage = error.error?.message || 'The report could not be processed. Check the file and try again.';
        } else if (error.status >= 500) {
            errorMessage = 'The health service is temporarily unavailable. Please try again in a moment.';
        } else if (error.error?.message) {
            errorMessage = error.error.message;
        }
        return throwError(() => new Error(errorMessage));
    }
}
