import { Component, signal, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HttpErrorResponse } from '@angular/common/http';
import { ReactiveFormsModule, FormBuilder, Validators } from '@angular/forms';
import { timeout, TimeoutError } from 'rxjs';
import { TriageService } from '../../services/triage.service';
import { TriageResponse } from '../../models/triage.models';

@Component({
    selector: 'app-chat',
    standalone: true,
    imports: [CommonModule, ReactiveFormsModule],
    templateUrl: './chat.component.html'
})
export class ChatComponent {
    private readonly fb = inject(FormBuilder);
    private readonly triageService = inject(TriageService);

    readonly assessment = signal<TriageResponse | null>(null);
    readonly isLoading = signal<boolean>(false);
    readonly errorMessage = signal<string | null>(null);

    readonly triageForm = this.fb.group({
        symptoms: ['', [Validators.required, Validators.minLength(5), Validators.maxLength(2000)]],
        age: [null as number | null, [Validators.min(0), Validators.max(120)]],
        biologicalSex: [''],
        knownConditions: ['']
    });

    readonly quickPrompts = [
        { label: 'Fever', text: 'I have had a fever since yesterday and feel tired. I can drink fluids and I am breathing normally.' },
        { label: 'Cough', text: 'I have had a dry cough for four days with a mild sore throat. I do not have trouble breathing.' },
        { label: 'Headache', text: 'I have had a mild headache since this morning. It came on gradually and I have no other symptoms.' }
    ];

    setQuickPrompt(promptText: string): void {
        this.triageForm.patchValue({ symptoms: promptText });
    }

    submitTriage(): void {
        if (this.isLoading()) return;
        if (this.triageForm.invalid) {
            this.triageForm.markAllAsTouched();
            return;
        }

        this.isLoading.set(true);
        this.errorMessage.set(null);
        this.assessment.set(null);

        const v = this.triageForm.value;
        this.triageService.assessSymptoms({
            symptoms: v.symptoms!,
            age: v.age ?? undefined,
            biologicalSex: v.biologicalSex || undefined,
            knownConditions: v.knownConditions || undefined
        }).pipe(timeout({ first: 65000 })).subscribe({
            next: (res) => {
                if (!res?.triageLevel || !Array.isArray(res.immediateActions)) {
                    this.errorMessage.set('The health service returned an unexpected response. Please try again.');
                } else {
                    this.assessment.set(res);
                }
                this.isLoading.set(false);
            },
            error: (err: unknown) => {
                this.errorMessage.set(this.toMessage(err));
                this.isLoading.set(false);
            }
        });
    }

    private toMessage(err: unknown): string {
        if (err instanceof TimeoutError) {
            return 'The health service is taking longer than expected. Please try again.';
        }
        if (err instanceof HttpErrorResponse) {
            if (err.status === 0) return 'We can’t reach the health service. Please check your connection and try again.';
            if (err.status === 400) return 'Some details weren’t accepted. Please check your entries and try again.';
            return `The health service ran into a problem (error ${err.status}). Please try again.`;
        }
        return 'Something went wrong. Please try again in a moment.';
    }
}