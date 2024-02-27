import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {catchError, tap} from "rxjs/operators";
import {Observable, throwError} from "rxjs";

import {CustomResponse} from "../interface/custom-response";

@Injectable({
  providedIn: 'root'
})
export class ServerService {
  private readonly apiUrl=`http://localhost:8080/server`;

  constructor(private http:HttpClient) { }
   servers$=<Observable<CustomResponse>>
     this.http.get<CustomResponse>(`${this.apiUrl}/list`)
       .pipe(
       tap(console.log),
        catchError(this.handleError)
       );

  handleError(): Observable<never> {
    return throwError(() => new Error('Method not implemented'));
  }


}
