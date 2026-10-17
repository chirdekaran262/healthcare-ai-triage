# Stage 1: Build Angular application using Node 20
FROM node:20-alpine AS build
WORKDIR /app

COPY package*.json ./
RUN npm ci

COPY . .
RUN npm run build --configuration=production

# Stage 2: Serve using Nginx Alpine
FROM nginx:alpine
WORKDIR /usr/share/nginx/html

# Clean default static assets
RUN rm -rf ./*

# Copy built browser bundle from Angular build output
COPY --from=build /app/dist/health-triage-frontend/browser ./
COPY nginx.conf /etc/nginx/conf.d/default.conf

EXPOSE 80

CMD ["nginx", "-g", "daemon off;"]