// This file can be replaced during build by using the `fileReplacements` array.
// `ng build ---prod` replaces `environment.ts` with `environment.prod.ts`.
// The list of file replacements can be found in `angular.json`.

export const environment = {
  production: false,
  correios_key: 'OGlm8fLTJ72JEwVuHCumG5IfOe0AU3Rg',
  correios_secret: 'iPxx331QxsieQOqSQ87SngDoFnq7TTDnlNbq8nmV7oUKvc28',
  correios_url: 'https://webmaniabr.com/api/1/cep/',
  api_url: 'http://localhost:8080/',
  tokenWhitelistedDomains: [ new RegExp('localhost:*') ],
  tokenBlacklistedRoutes: [ new RegExp('\/oauth\/token') ]
};

/*
 * In development mode, for easier debugging, you can ignore zone related error
 * stack frames such as `zone.run`/`zoneDelegate.invokeTask` by importing the
 * below file. Don't forget to comment it out in production mode
 * because it will have a performance impact when errors are thrown
 */
// import 'zone.js/dist/zone-error';  // Included with Angular CLI.
