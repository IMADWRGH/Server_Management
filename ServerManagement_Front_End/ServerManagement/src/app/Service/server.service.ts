import {Injectable} from '@angular/core';
import {HttpClient, HttpErrorResponse} from "@angular/common/http";
import {catchError, filter, tap} from "rxjs/operators";
import {Observable, throwError} from "rxjs";

import {CustomResponse} from "../interface/custom-response";
import {Server} from "../interface/server";
import {Status} from "../enum/status.enum";

@Injectable({
  providedIn: 'root'
})
export class ServerService {
  private readonly apiUrl=`http://localhost:8080/server`;
  private s? :Server;
  constructor(private http:HttpClient) { }
   servers$=<Observable<CustomResponse>>
     this.http.get<CustomResponse>(`${this.apiUrl}/list`)
       .pipe(
       tap(console.log),
        catchError(this.handleError)
       );

  save$=(server:Server)=> <Observable<CustomResponse>>
    this.http.post<CustomResponse>(`${this.apiUrl}/list`,server)
      .pipe(
        tap(console.log),
        catchError(this.handleError)
      );

  ping$=(ipAddress:String)=> <Observable<CustomResponse>>
    this.http.get<CustomResponse>(`${this.apiUrl}/ping/${ipAddress}`)
      .pipe(
        tap(console.log),
        catchError(this.handleError)
      );

  filter$ = (status: Status, response: CustomResponse) =>
    new Observable<CustomResponse>(subscriber => {
      console.log(response);

      if (response.data?.servers) {
        const filteredServers = response.data.servers.filter(
          server => server.status === status
        );
        subscriber.next({
          ...response,
          message: status === Status.All
            ? `Servers filtered by ${status} status`
            : filteredServers.length > 0
              ? `Servers filtered by ${
                status === Status.SERVER_UP ? 'SERVER UP' : 'SERVER DOWN'
              } status`
              : `No servers of ${status} found`,
          data: {
            servers: filteredServers
          }
        });
      } else {
        subscriber.next({
          ...response,
          message: "No servers found",
          data: {
            servers: [],
          },
        });
      }

      subscriber.complete();
    })
      .pipe(
        tap(console.log),
        catchError(this.handleError)
      );

  delete$=(serverId:number)=> <Observable<CustomResponse>>
    this.http.delete<CustomResponse>(`${this.apiUrl}/delete/${serverId}`)
      .pipe(
        tap(console.log),
        catchError(this.handleError)
      );


  private handleError(error:HttpErrorResponse): Observable<never> {
    console.log(error);
    return throwError(() => new Error(`Error :${error.status}`));
  }



  ///// filter on syntax diff
  // filter$ = (status:Status, response:CustomResponse)=> <Observable<CustomResponse>>
  //   new Observable<CustomResponse>(
  //     subscriber => {
  //       console.log(response);
  //
  //       subscriber.next(
  //         status === Status.All ? {...response, message: `Servers filtered by ${status} status`} :
  //           {
  //             ...response,
  //             message: response.data.servers?.filter(server => server.status === status).length > 0 ?
  //               `Servers filter by${status === Status.SERVER_UP ?
  //                 `SERVER UP` : `SERVER DOWN`} status`
  //               : `No servers of ${status} found`,
  //             data: {
  //               servers: response.data.servers?.filter(server => server.status === status)
  //             }
  //           }
  //       );
  //       subscriber.complete();
  //     }
  //   ).pipe(
  //     tap(console.log),
  //     catchError(this.handleError)
  //   );


}
