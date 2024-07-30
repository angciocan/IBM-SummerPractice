import {ApplicationConfig} from '@angular/core';
import { provideRouter } from '@angular/router';
import {provideHttpClient, withFetch} from '@angular/common/http';
import routeConfig from './app.routes';
import { provideAnimationsAsync } from '@angular/platform-browser/animations/async';

export const appConfig: ApplicationConfig = {
  providers: [provideRouter(routeConfig), provideAnimationsAsync(),
    provideHttpClient(withFetch())],
};
