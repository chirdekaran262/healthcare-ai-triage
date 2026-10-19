import { CommonModule } from '@angular/common';
import { Component, computed, inject, signal } from '@angular/core';
import { HttpErrorResponse } from '@angular/common/http';
import { TimeoutError, timeout } from 'rxjs';
import { ReportAnalysisResponse } from '../../models/report.models';
import { ReportAnalysisService } from '../../services/report-analysis.service';

const MAX_BYTES = 10 * 1024 * 1024;
const ALLOWED_TYPES = ['application/pdf', 'image/jpeg', 'image/png'];
const ALLOWED_EXTENSIONS = ['pdf', 'jpg', 'jpeg', 'png'];
const SEVERITY: Record<string, number> = { CRITICAL: 0, ATTENTION_NEEDED: 1 };

@Component({
    selector: 'app-report-upload',
    standalone: true,
    imports: [CommonModule],
    templateUrl: './report-upload.component.html'
})
export class ReportUploadComponent {
    private readonly reportAnalysisService = inject(ReportAnalysisService);

    readonly selectedFile = signal<File | null>(null);
    readonly validationMessage = signal<string | null>(null);
    readonly isDragging = signal(false);
    readonly patientContext = signal('');
    readonly isLoading = signal(false);
    readonly errorMessage = signal<string | null>(null);
    readonly analysis = signal<ReportAnalysisResponse | null>(null);

    /** Most severe findings first. */
    readonly findings = computed(() =>
        [...(this.analysis()?.keyFindings ?? [])]
            .sort((a, b) => (SEVERITY[a.status] ?? 2) - (SEVERITY[b.status] ?? 2)));
    readonly criticalCount = computed(() => this.findings().filter(f => f.status === 'CRITICAL').length);
    readonly attentionCount = computed(() => this.findings().filter(f => f.status === 'ATTENTION_NEEDED').length);
    readonly normalCount = computed(() => this.findings().filter(f => f.status === 'NORMAL').length);

    onFileSelected(event: Event): void {
        const input = event.target as HTMLInputElement;
        const file = input.files?.[0];
        if (file) this.setFile(file);
        input.value = '';
    }

    onDrop(event: DragEvent): void {
        event.preventDefault();
        this.isDragging.set(false);
        const file = event.dataTransfer?.files[0];
        if (file) this.setFile(file);
    }

    removeFile(): void {
        this.selectedFile.set(null);
        this.validationMessage.set(null);
    }

    onPatientContextChange(event: Event): void {
        this.patientContext.set((event.target as HTMLTextAreaElement).value);
    }

    analyzeReport(): void {
        const file = this.selectedFile();
        if (!file || this.isLoading()) return;

        this.isLoading.set(true);
        this.errorMessage.set(null);
        this.analysis.set(null);

        this.reportAnalysisService.analyzeReport(file, this.patientContext())
            .pipe(timeout({ first: 120000 }))
            .subscribe({
                next: (result) => {
                    if (this.isValidResponse(result)) {
                        this.analysis.set(result);
                        queueMicrotask(() => document.getElementById('analysis-heading')?.scrollIntoView({ behavior: 'smooth', block: 'start' }));
                    } else {
                        this.errorMessage.set('The health service returned an unexpected response. Please try again.');
                    }
                    this.isLoading.set(false);
                },
                error: (error: unknown) => {
                    this.errorMessage.set(this.toMessage(error));
                    this.isLoading.set(false);
                }
            });
    }

    startNewAnalysis(): void {
        this.analysis.set(null);
        this.errorMessage.set(null);
        this.selectedFile.set(null);
        this.patientContext.set('');
        window.scrollTo({ top: 0, behavior: 'smooth' });
    }

    print(): void {
        window.print();
    }

    formatFileSize(size: number): string {
        return size < 1024 * 1024
            ? `${Math.max(1, Math.round(size / 1024))} KB`
            : `${(size / (1024 * 1024)).toFixed(1)} MB`;
    }

    statusClasses(status: string): string {
        switch (status) {
            case 'NORMAL': return 'bg-[#eaf4eb] text-[#326b43]';
            case 'ATTENTION_NEEDED': return 'bg-[#fff4df] text-[#8b5b13]';
            case 'CRITICAL': return 'bg-[#fff0ed] text-[#9b3e32]';
            default: return 'bg-[#f0f2ef] text-[#58645b]';
        }
    }

    statusLabel(status: string): string {
        switch (status) {
            case 'NORMAL': return 'In range';
            case 'ATTENTION_NEEDED': return 'Needs attention';
            case 'CRITICAL': return 'Critical';
            default: return status.replaceAll('_', ' ').toLowerCase();
        }
    }

    private isValidResponse(r: ReportAnalysisResponse): boolean {
        return !!r
            && typeof r.executiveSummary === 'string'
            && typeof r.reportType === 'string'
            && Array.isArray(r.keyFindings)
            && Array.isArray(r.lifestyleAndCareNotes)
            && Array.isArray(r.questionsForYourDoctor)
            && typeof r.nextSteps === 'string'
            && typeof r.clinicalDisclaimer === 'string';
    }

    private toMessage(error: unknown): string {
        if (error instanceof TimeoutError) return 'The analysis is taking longer than expected. Please try again.';
        if (error instanceof HttpErrorResponse) return `The health service ran into a problem (error ${error.status}). Please try again.`;
        if (error instanceof Error) return error.message;
        return 'Something went wrong. Please try again in a moment.';
    }

    private setFile(file: File): void {
        if (this.isLoading()) return;

        const extension = file.name.split('.').pop()?.toLowerCase() ?? '';
        if (!ALLOWED_TYPES.includes(file.type) && !ALLOWED_EXTENSIONS.includes(extension)) {
            this.selectedFile.set(null);
            this.validationMessage.set('Choose a PDF, JPG, or PNG report.');
            return;
        }
        if (file.size > MAX_BYTES) {
            this.selectedFile.set(null);
            this.validationMessage.set('This file is larger than 10 MB. Choose a smaller report.');
            return;
        }
        this.validationMessage.set(null);
        this.selectedFile.set(file);
        this.analysis.set(null);
        this.errorMessage.set(null);
    }
}