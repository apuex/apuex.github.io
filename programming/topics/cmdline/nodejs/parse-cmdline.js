/*
 * Web API test script template for Copy & Paste oriented programming ^_^||...
 */
var http = require('axios');
var program = require('commander');
const assert = require('assert').strict;

program
  .option('-s, --server-ip <server ip>', 'server ip')
  .option('-p, --server-port <server port>', 'server port')
  .option('    --my-id   <id>', 'my ID')
  .option('    --my-name <name>', 'my Name');
       
program.parse(process.argv);

var opts = program.opts();
checkOpts(opts);

var requestBody = {
    myId: opts.myId,
    myIp: opts.myName
};

var url = `http://${opts.serverIp}:${opts.serverPort}/api/my/path`; 

console.log(`request = ${JSON.stringify(requestBody)}`);

var request = http.post(url, requestBody)
  .then(function (response) {
     console.log(`response = ${JSON.stringify(response.data)}`);
     assert.equal(response.data.myId, opts.myId, "my ID does not match.");
     assert.equal(response.data.myName, opts.myName, "my Name does not match.");
     assert.equal(response.data.result, 'SUCCESS', "Result does not match, expected value is 'SUCCESS'.");
  })
  .catch(function (error) {
    console.log("there're complicated situations...");
    console.log(JSON.stringify(error));
    process.exit(-1);
  });

function checkOpts(opts) {
  console.log(`options = ${JSON.stringify(opts)}`);;
  if(!(opts.serverIp 
              && opts.serverPort 
              && opts.myId
              )) {
    console.error('Invalid command: missing required option.');
    program.outputHelp();
    process.exit(-1);
  }
}

