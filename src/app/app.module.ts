import { BrowserModule } from '@angular/platform-browser';
import { NgModule, APP_INITIALIZER} from '@angular/core';
import { FormsModule } from '@angular/forms';
import { HttpClientModule, HttpRequest, HTTP_INTERCEPTORS } from '@angular/common/http';

import { AuthService } from './auth.service';
import { NoteService } from './note.service';
import { NoteElementService } from './note-element.service';

import { AppRoutingModule } from './app-routing.module';

import { AppComponent } from './app.component';
import { HomeComponent } from './home/home.component';
import { FooterComponent } from './footer/footer.component';
import { HeaderComponent } from './header/header.component';
import { AuthComponent } from './auth/auth.component';
import { PageNotFoundComponent } from './page-not-found/page-not-found.component';
import { NoteComponent } from './note/note.component';
import { NoteEditorComponent } from './note-editor/note-editor.component';
import { NoteElementComponent } from './note-element/note-element.component';
import { NoteElementEditorComponent } from './note-element-editor/note-element-editor.component';
import { AuthGuard } from './auth-guard.service';
import { XhrInterceptor } from './xhr-interceptor';

@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    FooterComponent,
    HeaderComponent,
    AuthComponent,
    PageNotFoundComponent,
    NoteComponent,
    NoteEditorComponent,
    NoteElementComponent,
    NoteElementComponent,
    NoteElementEditorComponent,
    NoteElementEditorComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    FormsModule,
    AppRoutingModule
  ],
  providers: [
    AuthService,
    {
      provide: APP_INITIALIZER,
      useFactory: (authService: AuthService) => () => authService.authenticate(null),
      deps: [AuthService],
      multi: true
    },
    AuthGuard,
    NoteService,
    NoteElementService,
    { provide: HTTP_INTERCEPTORS, useClass: XhrInterceptor, multi: true }
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
