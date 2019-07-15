const spawn = require("child_process").spawn;
const http = require("http");

const mocks = {
  "plan-catalog-service": [8081, 8905, 7443],
  "fipe-service": [8082, 8906, 7444]
};

for (const serviceName in mocks) {
  //register to Consul
  var req = http.request({
    host: "consul",
    port: 8500,
    path: "/v1/agent/service/register",
    method: "PUT"
  });
  req.write(
    JSON.stringify({
      Name: serviceName,
      Port: mocks[serviceName][0],
      Address: "localhost"
    })
  );
  req.on("error", console.error);
  req.on("data", console.log);
  req.end();

  // start stubby
  var child = spawn(
    "stubby",
    [
      "-d",
      `${serviceName}/mocks.yml`,
      "-s",
      mocks[serviceName][0],
      "-a",
      mocks[serviceName][1],
      "-t",
      mocks[serviceName][2],
      "-w"
    ],
    {
      detached: true
    }
  );
  child.stdout.on("data", chunk => console.log(chunk.toString()));
  child.stderr.on("data", chunk => console.error(chunk.toString()));
}
