import {Injectable} from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class ConfigService {

  private _api_url = 'http://localhost:8080/api';
  private _auth_url = 'http://localhost:8080/auth';
  private _user_url = this._api_url + '/user';

  private _refresh_token_url = this._api_url + '/refresh';

  get refresh_token_url(): string {
    return this._refresh_token_url;
  }

  private _login_url = this._auth_url + '/login';

  get login_url(): string {
    return this._login_url;
  }

  private _change_password_url = this._auth_url + '/change-password';

  get change_password_url(): string {
    return this._change_password_url;
  }

  private _whoami_url = this._api_url + '/whoami';

  get whoami_url(): string {
    return this._whoami_url;
  }

  private _users_url = this._user_url + '/all';

  get users_url(): string {
    return this._users_url;
  }

  private _foo_url = this._api_url + '/foo';

  get foo_url(): string {
    return this._foo_url;
  }

  private _signup_url = this._auth_url + '/signup';

  get signup_url(): string {
    return this._signup_url;
  }

}
