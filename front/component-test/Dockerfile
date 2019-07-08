# Dockerfile
FROM node:6.11.2

# Create app directory
RUN mkdir -p /usr/src/app
WORKDIR /usr/src/app

# Bundle app source
COPY ./app /usr/src/app

EXPOSE 9001

# defined in package.json
CMD [ "npm", "run", "start:server" ]