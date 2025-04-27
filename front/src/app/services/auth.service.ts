import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  private username: string = '';

  constructor() {}

  login(username: string) {
    this.username = username;
  }

  getUsername(): string {
    return this.username;
  }

  logout() {
    this.username = '';
  }
}
