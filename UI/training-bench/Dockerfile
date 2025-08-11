# Stage 1: Build the Angular application
FROM node:18-alpine as build
WORKDIR /app
COPY package.json package-lock.json ./
#plus adapté pour l'automatisation, clean install
RUN npm ci 
COPY . .
RUN npm run build

# Stage 2: Serve the application with Nginx
FROM nginx:alpine
COPY --from=build /app/ddist/training-bench /usr/share/nginx/html