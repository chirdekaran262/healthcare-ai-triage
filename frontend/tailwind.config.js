/** @type {import('tailwindcss').Config} */
module.exports = {
  content: [
    "./src/**/*.{html,ts}",
  ],
  theme: {
    extend: {
      colors: {
        triage: {
          red: '#DC2626',
          redDark: '#991B1B',
          yellow: '#D97706',
          yellowDark: '#B45309',
          green: '#059669',
          greenDark: '#047857'
        }
      },
      animation: {
        'pulse-fast': 'pulse 1.2s cubic-bezier(0.4, 0, 0.6, 1) infinite',
      }
    },
  },
  plugins: [],
}