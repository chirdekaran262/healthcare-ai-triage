# Primary Healthcare AI Triage

An AI-powered healthcare triage application designed to help users understand their symptoms, receive preliminary urgency guidance, and learn about appropriate next steps for seeking medical care.

## About the Project

Primary Healthcare AI Triage is a web application built with Angular and Spring Boot that uses Google's Gemini API to assess user-submitted symptoms and provide structured health guidance.

The application categorizes the urgency of a user's situation into three levels: RED, YELLOW, and GREEN. It provides immediate actions, general care advice, follow-up recommendations, and a medical disclaimer.

The application is intended to support health awareness and preliminary triage. It does not replace a qualified healthcare professional or provide a definitive medical diagnosis.

## Features

- **Symptom Assessment:** Submit symptoms for AI-generated preliminary triage guidance.
- **Urgency Classification:** Organizes assessment results into RED, YELLOW, and GREEN levels.
- **Immediate Actions:** Provides suggested actions based on the assessment.
- **Care Advice:** Displays general guidance related to the reported symptoms.
- **Follow-up Recommendations:** Suggests appropriate next steps for seeking care.
- **Additional Patient Information:** Supports optional age, biological sex, and known medical conditions.
- **Quick Symptom Prompts:** Provides sample symptom descriptions to help users get started.
- **Responsive User Interface:** Built with Angular for an interactive web experience.
- **Medical Disclaimer:** Emphasizes that AI-generated guidance is not a substitute for professional medical care.

## Technology Stack

### Frontend
- Angular
- TypeScript
- HTML
- CSS
- Reactive Forms
- Angular Signals
- RxJS

### Backend
- Java
- Spring Boot
- Spring WebFlux / WebClient
- Maven
- REST API

### AI Integration
- Google Gemini API
- Structured JSON responses
- Schema-based response formatting

## Project Structure

```text
Health_AI/
├── frontend/
│   ├── src/
│   ├── angular.json
│   ├── package.json
│   └── package-lock.json
├── src/
│   └── main/
│       ├── java/
│       └── resources/
├── pom.xml
├── .gitignore
└── README.md
```

The backend files are shown at the repository root in this example. If the backend is moved into a separate `backend/` directory, update the structure accordingly.

## Prerequisites

Install the following before running the application:

- Java Development Kit (JDK) compatible with the Spring Boot project
- Maven, or the project's Maven Wrapper
- Node.js and npm
- A Google Gemini API key with access to a supported model

## Configuration

Configure the Gemini API key through an environment variable rather than committing the key to source control.

For example, if the backend configuration uses `GEMINI_API_KEY`, set it in PowerShell:

```powershell
$env:GEMINI_API_KEY = "YOUR_GEMINI_API_KEY"
```

Ensure that the Spring Boot configuration references the environment variable, for example:

```properties
gemini.api.key=${GEMINI_API_KEY}
```

Use a Gemini model that is available to your API key and supports the `generateContent` method.

Never commit actual API keys, passwords, or other secrets to GitHub.

## Getting Started

### 1. Clone the Repository

```bash
git clone YOUR_GITHUB_REPOSITORY_URL
cd Health_AI
```

Replace `YOUR_GITHUB_REPOSITORY_URL` with your repository's actual URL.

### 2. Start the Backend

From the repository root, run:

```powershell
.\mvnw.cmd spring-boot:run
```

If the Maven Wrapper is unavailable, use an installed Maven version:

```bash
mvn spring-boot:run
```

The backend is configured to run on port `8081`.

### 3. Start the Frontend

Open another terminal:

```bash
cd frontend
npm install
npx ng serve
```

Open the local frontend in your browser:

http://localhost:4200

### 4. Test the API

The symptom assessment endpoint is:

```http
POST http://localhost:8081/api/v1/triage/assess
```

The request body should match the request model implemented by the backend. A typical request includes symptoms and may include age, biological sex, and known medical conditions.

Ensure that the frontend's API URL matches the backend address and that the required cross-origin configuration is in place for local development.

## Triage Levels

| Level | Meaning |
|---|---|
| RED | Potentially urgent situation requiring immediate attention |
| YELLOW | Symptoms that may require timely medical assessment |
| GREEN | Lower apparent urgency based on the information provided |

These levels are preliminary guidance, not a guarantee that a condition is safe or harmless. Severe or rapidly worsening symptoms require urgent medical attention regardless of the displayed category.

## Medical Disclaimer

This application is an educational and health-information support project. Its output is generated using AI and may be incomplete or inaccurate. It must not be used as a substitute for professional medical assessment, diagnosis, or treatment.

If someone experiences a medical emergency or severe symptoms, they should contact local emergency services or seek immediate medical attention rather than relying on this application.

## Future Improvements

Potential areas for further development include:

- Automated frontend and backend testing
- Improved error handling and service availability
- Secure deployment and environment-specific configuration
- Accessibility and multilingual support
- Enhanced validation and monitoring of AI-generated responses

## Contributing

Contributions and suggestions are welcome. Before submitting changes, test the affected frontend or backend functionality and avoid committing secrets or local configuration files.

## License

A license has not yet been specified. Add an appropriate open-source license before distributing the project under specific reuse terms.
