# Parsing Command Line Arguments

## C
```
#include <stdio.h>
#include <stdlib.h>
#include <getopt.h>
#include <string.h>
#include <sys/time.h>
#include <sys/types.h>
#include <unistd.h>

#define KEY_LENGTH 128

void
help (char *cmd)
{
  fprintf (stderr, "Usage: %s [-k key] [-t nsecs]\n", cmd);
}

void
version ()
{
  fprintf (stderr, "parse-cmdline version 1.0.0.\n");
}

int
main (int argc, char *argv[])
{
  fd_set rfds;
  struct timeval tv;
  int retval;
  int nsecs = 5;
  char key[KEY_LENGTH]; memset(key, 0, KEY_LENGTH);
  int c;
  int digit_optind = 0;

  while (1)
    {
      int this_option_optind = optind ? optind : 1;
      int option_index = 0;
      static struct option long_options[] = {
	{"key", required_argument, 0, 'k'},
	{"timeout", required_argument, 0, 't'},
	{"version", no_argument, 0, 'v'},
	{"help", no_argument, 0, 'h'},
	{0, 0, 0, 0}
      };

      c = getopt_long (argc, argv, "k:t:vh", long_options, &option_index);
      if (c == -1)
	break;

      switch (c)
	{
	case 'k':
	  if (strlen (optarg) >= KEY_LENGTH)
	    {
	      exit (EXIT_FAILURE);
	    }
	  else
	    {
	      strncpy (key, optarg, KEY_LENGTH - 1);
	    }
	  break;

	case 't':
	  nsecs = atoi (optarg);
	  break;

	case 'h':
	  help (argv[0]);
	  exit (EXIT_SUCCESS);

	case 'v':
	  version ();
	  exit (EXIT_SUCCESS);
	  break;

	default:
	  printf ("?? getopt returned character code 0%o ??\n", c);
	  exit (EXIT_FAILURE);
	}
    }

  printf("--key=%s\n", key);
  printf("--timeout=%d\n", nsecs);
  if (optind < argc)
    {
      printf ("non-option ARGV-elements: ");
      while (optind < argc)
	printf ("%s ", argv[optind++]);
      printf ("\n");
      exit (EXIT_FAILURE);
    }

  exit (EXIT_SUCCESS);
}
```
Compile & Run:
```
$ gcc -o parse-cmdline parse-cmdline.c
$ ./parse-cmdline
--key=
--timeout=5
$ ./parse-cmdline -k my-id -t 10
--key=my-id
--timeout=10
$ ./parse-cmdline -k my-id -t 10 file.txt
--key=my-id
--timeout=10
non-option ARGV-elements: file.txt 
$ ./parse-cmdline -h
Usage: ./parse-cmdline [-k key] [-t nsecs]
$
```

## C++

## Haskell

A full, working sample at [https://github.com/apuex/haskell-services-gen](https://github.com/apuex/haskell-services-gen/blob/master/src/Main.hs)

## Java
A SOAP testing tool(SOAP client) sample using apache commons-cli for parsing command line. requires maven to build.

```
package com.github.apuex.jee.saaj;

import org.apache.commons.cli.*;

import javax.xml.namespace.QName;
import javax.xml.soap.*;
import java.io.FileInputStream;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Map;
import java.util.TreeMap;

public class Main {
    public static void main(String args[]) throws Exception {
        final Options options = options();

        CommandLineParser parser = new DefaultParser();
        CommandLine cmd = parser.parse(options, args);

        if (cmd.hasOption("h")) {
            HelpFormatter formatter = new HelpFormatter();
            formatter.printHelp("soap-client <options>", options);
            printOptions(defaultOptions());
        } else {
            final Map<String, String> params = defaultOptions();

            options.getOptions().stream()
                    .forEach(o -> {
                        if (cmd.hasOption(o.getOpt())) {
                            params.put(o.getLongOpt(), cmd.getOptionValue(o.getOpt()));
                        }
                    });

            System.out.println(invoke(params));
        }
    }

    public static Map<String, String> defaultOptions() {
        return new TreeMap<String, String>() {{
            put("endpoint-url", "http://my-service.example.com");
            put("namespace-uri", "http://my-service.example.com");
            put("method", "invoke");
            put("output", "invokeResponse");
            put("return", "invokeReturn");
            put("soap-version", "1.1");
            put("parameter-name", "xmlData");
            put("request-xml-file", "GetInfoRequest.xml");
        }};
    }

    public static void printOptions(Map<String, String> options) {
        System.out.println("current options are:");
        int maxLength = options.entrySet().stream()
                .map(x -> x.getKey().length())
                .max(Integer::compare)
                .get() + 1;

        options.entrySet().forEach(e -> System.out.printf("  %s = %s\n", paddingRight(e.getKey(), maxLength), e.getValue()));
    }

    public static String paddingRight(String s, int maxWidth) {
        int length = s.length();
        StringBuilder sb = new StringBuilder();
        sb.append(s);
        if (length < maxWidth) {
            for (int i = length; i < maxWidth; ++i) {
                sb.append(' ');
            }
        }
        return sb.toString();
    }

    public static Options options() {
        final Options options = new Options();
        options.addOption(new Option("e", "endpoint-url", true, "SOAP service endpoint URL."));
        options.addOption(new Option("n", "namespace-uri", true, "SOAP service namespace URI."));
        options.addOption(new Option("m", "method", true, "method to be invoked."));
        options.addOption(new Option("p", "parameter-name", true, "method parameter."));
        options.addOption(new Option("s", "soap-version", true, "soap specification version, valid options are 1.1, 1.2, 1.1, default is 1.1"));
        options.addOption(new Option("f", "request-xml-file", true, "name of file contains request xml content."));
        options.addOption(new Option("x", "request-xml-content", true, "request xml content."));
        options.addOption(new Option("v", "verbose", false, "print out options and transport details."));
        options.addOption(new Option("h", "help", false, "print help message."));
        return options;
    }

    public static String invoke(Map<String, String> params) throws Exception {
	throw new RuntimeException("not implemented.");
    }

}
```
package and run:
```
$ java -jar target/soap-client-1.0.3.jar -h
usage: soap-client <options>
 -e,--endpoint-url <arg>          SOAP service endpoint URL.
 -f,--request-xml-file <arg>      name of file contains request xml
                                  content.
 -h,--help                        print help message.
 -m,--method <arg>                method to be invoked.
 -n,--namespace-uri <arg>         SOAP service namespace URI.
 -p,--parameter-name <arg>        method parameter.
 -s,--soap-version <arg>          soap specification version, valid
                                  options are 1.1, 1.2, 1.1, default is
                                  1.1
 -v,--verbose                     print out options and transport details.
 -x,--request-xml-content <arg>   request xml content.
current options are:
  endpoint-url      = http://my-service.example.com
  method            = invoke
  namespace-uri     = http://my-service.example.com
  output            = invokeResponse
  parameter-name    = xmlData
  request-xml-file  = GetInfoRequest.xml
  return            = invokeReturn
  soap-version      = 1.1
$
```

## NodeJS

This sample requires package `commander`.

```
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
```
Run:
```
$ nodejs parse-cmdline.js --my-name=1 --my-id=1
options = {"myId":"1","myName":"1"}
Invalid command: missing required option.
Usage: parse-cmdline [options]

Options:
  -s, --server-ip <server ip>      server ip
  -p, --server-port <server port>  server port
      --my-id   <id>               my ID
      --my-name <name>             my Name
  -h, --help                       output usage information
$
```
## Python
```
from optparse import OptionParser

parser = OptionParser()
parser.add_option("-f", "--file", dest="filename",
    help="write report to FILE", metavar="FILE")
parser.add_option("-q", "--quiet",
    action="store_false", dest="verbose", default=True,
    help="don't print status messages to stdout")

(options, args) = parser.parse_args()
print options, args
```

Run:
```
$ python parse-cmdline.py
{'verbose': True, 'filename': None} []
$ python parse-cmdline.py file.txt
{'verbose': True, 'filename': None} ['file.txt']
$ python parse-cmdline.py --file my-secret.doc file.txt
{'verbose': True, 'filename': 'my-secret.doc'} ['file.txt']
$ python parse-cmdline.py -h
Usage: parse-cmdline.py [options]

Options:
  -h, --help            show this help message and exit
  -f FILE, --file=FILE  write report to FILE
  -q, --quiet           don't print status messages to stdout
$
```

## Shell
```
#! /bin/bash

# Web API test script template for Copy & Paste oriented programming ^_^||...

PRG_DIR=$(cd $(dirname "$0"); pwd)

SERVER_IP="192.168.0.197"
SERVER_PORT="9000"
MY_DATA_ID=""

OPTS=$(getopt -os:p:d: --long server-ip:,server-port:,my-data-id: -- $@)

if [[ $? -eq 0 ]]
then
  while true; do
    case "$1" in
      -s | --server-ip)
        SERVER_IP="$2"; shift 2;;
      -p | --server-port)
        SERVER_PORT="$2"; shift 2;;
      -d | --my-data-id)
        MY_DATA_ID="$2"; shift 2;;
      *) break;;
    esac
  done
else
  cat << EOF
Usage: <cmd> <options>
valid options are:
  -s, --server-ip <server-ip>      SERVER IP address
  -p, --server-port <server-port>  SERVER PORT
  -d, --my-data-id <my-data-id>    MY DATA ID, to be replaced with your own ^_^||...
EOF
  exit -1;
fi

REQUEST=\'$(cat << EOF
{
  "myDataId": "${MY_DATA_ID}"
}
EOF
)\'

echo SERVER_IP=${SERVER_IP}
echo SERVER_PORT=${SERVER_PORT}
echo MY_DATA_ID=${MY_DATA_ID}
echo REQUEST=${REQUEST}

CMD="curl -H 'Content-Type: application/json' -X POST -d ${REQUEST} http://${SERVER_IP}:${SERVER_PORT}/api/my-call"

echo ${CMD}
RESPONSE=$(echo ${CMD} | bash)

# implement your own code to check this ${RESPONSE}...

echo RESPONSE=${RESPONSE}
```
Run:
```
$ ./template.sh  -h
getopt: invalid option -- 'h'
Usage: <cmd> <options>
valid options are:
  -s, --server-ip <server-ip>      SERVER IP address
  -p, --server-port <server-port>  SERVER PORT
  -d, --my-data-id <my-data-id>    MY DATA ID, to be replaced with your own ^_^||...
$
```
