import { Routes } from '@angular/router';

export const routes: Routes = [
    {
        path: '',
        title: 'Symptom checker | Swasthya',
        loadComponent: () => import('./components/chat/chat.component').then(module => module.ChatComponent)
    },
    {
        path: 'reports',
        title: 'Health reports | Swasthya',
        loadComponent: () => import('./components/report-upload/report-upload.component').then(module => module.ReportUploadComponent)
    },
    { path: '**', redirectTo: '' }
];
