FROM node:6.11-alpine

WORKDIR /mocks

ADD plan-catalog-service /mocks/plan-catalog-service
ADD fipe-service /mocks/fipe-service

ADD index.js /mocks/index.js

EXPOSE 8081 8905
EXPOSE 8082 8906

RUN npm install -g stubby

CMD ["node", "."]
