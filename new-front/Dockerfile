# Build
FROM node:10.1.0 as builder
COPY . .
RUN yarn install --production && yarn build
WORKDIR ./server

# Deployment
FROM nginx:1.17.2-alpine
COPY nginx.conf /etc/nginx/conf.d/default.conf
COPY --from=builder ./server/build /usr/share/nginx/html
EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]
