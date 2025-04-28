import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { SessionInformation } from '../interfaces/sessionInformation.interface';
import { environment } from '../../../environments/environment';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  private pathService = 'http://' + environment.baseUrl + '/auth';

  constructor(private httpClient: HttpClient) {}

  public login(id: string): Observable<SessionInformation> {
    return this.httpClient.get<SessionInformation>(
      `${this.pathService}/login/${id}`
    );
  }
}
