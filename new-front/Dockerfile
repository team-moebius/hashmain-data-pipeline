# Build
FROM node:10.13.0 as builder
WORKDIR /app
COPY . ./
RUN yarn
RUN yarn build

# Deployment
FROM nginx:1.17.2-alpine
COPY nginx.conf /etc/nginx/conf.d/default.conf
COPY --from=builder /app/build /usr/share/nginx/html
EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]
