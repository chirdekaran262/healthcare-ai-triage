import { Component } from '@angular/core';
import { RouterLink, RouterLinkActive, RouterOutlet } from '@angular/router';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterLink, RouterLinkActive, RouterOutlet],
  template: `
    <div class="min-h-screen">
      <header class="sticky top-0 z-20 border-b border-[#e4e9e4] bg-[#fbfcfa]/95 backdrop-blur">
        <div class="mx-auto flex h-[76px] max-w-7xl items-center justify-between gap-4 px-4 sm:px-6 lg:px-10">
          <a class="flex shrink-0 items-center gap-3 text-[#203b2c] no-underline" routerLink="/"
            aria-label="Swasthya home">
            <span class="grid h-10 w-10 place-items-center rounded-2xl bg-[#1f6244] text-white shadow-sm"
              aria-hidden="true">
              <svg class="h-6 w-6" viewBox="0 0 24 24" fill="none">
                <path d="M10 3h4v7h7v4h-7v7h-4v-7H3v-4h7V3Z" fill="currentColor" />
              </svg>
            </span>
            <span class="font-serif text-[22px] font-semibold">Swasthya<span class="text-[#d17a3c]">.</span>
              <span class="ml-2 hidden align-middle font-sans text-[10px] font-semibold tracking-[1.5px] text-[#768178] sm:inline">HEALTH GUIDANCE</span>
            </span>
          </a>

          <nav class="flex items-center gap-1 rounded-xl bg-[#f0f4ef] p-1" aria-label="Main navigation">
            <a routerLink="/" routerLinkActive="bg-white text-[#1f6244] shadow-sm"
              [routerLinkActiveOptions]="{ exact: true }"
              class="rounded-lg px-3 py-2 text-xs font-semibold text-[#657268] transition hover:text-[#1f6244] sm:px-4 sm:text-sm">
              <span class="hidden sm:inline">Symptom checker</span><span class="sm:hidden">Symptoms</span>
            </a>
            <a routerLink="/reports" routerLinkActive="bg-white text-[#1f6244] shadow-sm"
              class="rounded-lg px-3 py-2 text-xs font-semibold text-[#657268] transition hover:text-[#1f6244] sm:px-4 sm:text-sm">
              <span class="hidden sm:inline">Health reports</span><span class="sm:hidden">Reports</span>
            </a>
          </nav>

          <span class="hidden items-center gap-2 text-xs font-medium text-[#53645a] md:flex">
            <span class="h-2 w-2 rounded-full bg-[#69a77a]"></span> Everyday health support
          </span>
        </div>
      </header>

      <router-outlet></router-outlet>

      <footer class="mx-auto mt-8 flex max-w-7xl flex-col gap-2 border-t border-[#e1e7df] px-4 py-5 text-xs leading-5 text-[#718077] sm:flex-row sm:justify-between sm:gap-6 sm:px-6 lg:px-10">
        <p><strong class="text-[#4c5f52]">Helpful guidance, not a diagnosis.</strong> If you feel seriously unwell or symptoms worsen, seek medical care.</p>
        <p>Discuss health concerns with a qualified professional.</p>
      </footer>
    </div>
  `
})
export class AppComponent { }