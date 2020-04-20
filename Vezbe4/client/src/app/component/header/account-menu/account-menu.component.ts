import {Component, OnInit} from '@angular/core';
import {AuthService, ConfigService, UserService} from '../../../service';

@Component({
  selector: 'app-account-menu',
  templateUrl: './account-menu.component.html',
  styleUrls: ['./account-menu.component.scss']
})
export class AccountMenuComponent implements OnInit {

  // TODO define user interface
  user: any;

  constructor(
    private authService: AuthService,
    private userService: UserService
  ) {
  }

  ngOnInit() {
    this.user = this.userService.currentUser;
  }

  logout() {
    this.authService.logout();
  }
}
