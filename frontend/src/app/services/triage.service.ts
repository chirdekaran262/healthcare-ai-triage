import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Observable, catchError, throwError } from 'rxjs';
import { HealthQueryRequest, TriageResponse } from '../models/triage.models';

@Injectable({
    providedIn: 'root'
})
export class TriageService {
    private readonly http = inject(HttpClient);
    private readonly apiUrl = '/api/v1/triage/assess';

    assessSymptoms(request: HealthQueryRequest): Observable<TriageResponse> {
        return this.http.post<TriageResponse>(this.apiUrl, request).pipe(
            catchError(this.handleError)
        );
    }

    private handleError(error: HttpErrorResponse): Observable<never> {
        let errorMessage = 'We could not complete your request. Please try again.';
        if (error.status === 0) {
            errorMessage = 'We could not connect to the health service. Check that the backend is running, then try again.';
        } else if (error.status === 400) {
            errorMessage = error.error?.message || 'Please check your symptom details and try again.';
        } else if (error.status >= 500) {
            errorMessage = 'The health service is temporarily unavailable. Please try again in a moment.';
        } else if (error.error?.message) {
            errorMessage = error.error.message;
        }
        return throwError(() => new Error(errorMessage));
    }
}