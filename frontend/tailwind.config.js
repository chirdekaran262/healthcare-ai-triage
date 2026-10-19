/** @type {import('tailwindcss').Config} */
module.exports = {
  content: [
    "./src/**/*.{html,ts}",
  ],
  theme: {
    extend: {
      colors: {
        brand: { 50: '#f3f8f2', 100: '#edf4ed', 200: '#dce8dc', 600: '#39724c', 700: '#246442', 800: '#1b5235', 900: '#1d3428' },
        ink: { DEFAULT: '#34463a', muted: '#69766c', faint: '#7b877e' },
        line: '#e1e7df',
        triage: {
          red: '#DC2626',
          redDark: '#991B1B',
          yellow: '#D97706',
          yellowDark: '#B45309',
          green: '#059669',
          greenDark: '#047857',
        },
      },
      boxShadow: { card: '0 12px 32px rgba(36,58,43,.06)' },
      fontFamily: { serif: ['Fraunces', 'Georgia', 'serif'], sans: ['Inter', 'Segoe UI', 'sans-serif'] },
      animation: {
        'pulse-fast': 'pulse 1.2s cubic-bezier(0.4, 0, 0.6, 1) infinite',
      },
    },
  },
  plugins: [],
};
