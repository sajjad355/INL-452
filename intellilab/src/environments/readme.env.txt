We are currently only configuring two envrionments - dev and prod.
For details on how these two files get used by the code in different builds see:
https://indepth.dev/becoming-an-angular-environmentalist/

If we ever get an external client using IntelliLab with their own production deploy, we will need to revist configuration
and utilize one of the following options:
1) Seperate file based (i.e not typescrript) config for the app that can be changed as needed

This would be the preferrable option for reasons discussed here:
https://davembush.github.io/where-to-store-angular-configurations/

In a nutshell, having to do ng build and build a different version of your app for each one of your installs is ugly. 
Similar for ng serve - you present the option for running the wrong way for your different clients and you make it more
complicated as well.

2) Add an /envrionments/envrionment.clientname.prod.ts with appropriate configuration and then update angular.json as follows:
   "configurations": {
            "production": {
              "optimization": true,
              "outputHashing": "all",
              "sourceMap": false,
              "extractCss": true,
              "namedChunks": false,
              "aot": true,
              "extractLicenses": true,
              "vendorChunk": false,
              "buildOptimizer": true,
              "fileReplacements": [
                {
                  "replace": "src/environments/environment.ts",
                  "with": "src/environments/environment.prod.ts"
                }
              ]
            }
            "clientnameproduction": {
              "optimization": true,
              "outputHashing": "all",
              "sourceMap": false,
              "extractCss": true,
              "namedChunks": false,
              "aot": true,
              "extractLicenses": true,
              "vendorChunk": false,
              "buildOptimizer": true,
              "fileReplacements": [
                {
                  "replace": "src/environments/environment.ts",
                  "with": "src/environments/environment.clientname.prod.ts"
                }
              ]
            }
          }