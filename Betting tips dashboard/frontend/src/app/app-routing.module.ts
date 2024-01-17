import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AppComponent } from './app.component';
import { VipComponent } from './vip/vip.component';
import { FreeComponent } from './free/free.component';
import { UpdateUserComponent } from './update-user/update-user.component';
import { authGuard } from './service/auth.guard';
import { LoginComponent } from './login/login.component';
import { RegisterComponent } from './register/register.component';
import { AdminGuard } from './service/admin.guard';
import { ManageUserComponent } from './manage-user/manage-user.component';

const routes: Routes = [
  {
    path: '',
    component: FreeComponent,
    canActivate: [authGuard],
  },
  {
    path: 'vip',
    component: VipComponent,
    canActivate: [authGuard],
  },
  {
    path: 'free',
    component: FreeComponent,
    canActivate: [authGuard],
  },
  {
    path: 'update/:username',
    component: UpdateUserComponent,
    title: 'Update User',
    canActivate: [authGuard],
  },
  { path: 'login', component: LoginComponent, title: 'Login' },
  {
    path: 'register',
    component: RegisterComponent,
    title: 'Register',
    canActivate: [authGuard],
  },
  { path: '**', component: LoginComponent, title: 'Login' },
  {
    path: 'manage',
    component: ManageUserComponent,
    title: 'Manage User',
    canActivate: [authGuard, AdminGuard],
  },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
