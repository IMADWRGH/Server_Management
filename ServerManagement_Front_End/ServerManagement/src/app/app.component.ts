import { Component, OnInit } from '@angular/core';
import { ServerService } from './Service/server.service';
import { catchError, map, Observable, of, startWith } from 'rxjs';
import { CustomResponse } from './interface/custom-response';
import { AppState } from './interface/app-state';
import { DataState } from './enum/data.state.enum';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
isPinging(arg0: any) {
throw new Error('Method not implemented.');
}
reloadServers() {
throw new Error('Method not implemented.');
}
handleServerForm() {
throw new Error('Method not implemented.');
}

  appState$: Observable<AppState<CustomResponse>>;

  readonly DataState = DataState;
isEditMode: any;

  constructor(private serverService: ServerService) { }

  ngOnInit(): void {
    this.appState$ = this.serverService.servers$.pipe(
      map(response => {
        return { dataState: DataState.LOADED_STATE, appData: response }
      }),
      startWith({ dataState: DataState.LOADING_STATE, appData: null }),
      catchError((error: string) => {
        return of({ dataState: DataState.ERROR_STATE, error })
      })
    );
  }




}
