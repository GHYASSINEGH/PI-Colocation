import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatNativeDateModule } from '@angular/material/core';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { PostsListComponent } from './pages/posts-list/posts-list.component';
import { AddPostComponent } from './pages/add-post/add-post.component';
import { provideHttpClient, withInterceptorsFromDi } from '@angular/common/http';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { EditPostComponent } from './pages/edit-post/edit-post.component';
import { PostsStatisticComponent } from './pages/posts-statistic/posts-statistic.component';
//import { NotificationModule } from './notification.module';
import { NgCircleProgressModule } from 'ng-circle-progress';

@NgModule({ 
    declarations: [
        AppComponent,
        PostsListComponent,
        AddPostComponent,
        EditPostComponent,
        PostsStatisticComponent
    ],
    bootstrap: [
        AppComponent], 
    imports: [
        BrowserModule,
        AppRoutingModule,
        ReactiveFormsModule,
        MatNativeDateModule,
        MatDatepickerModule,
        MatFormFieldModule,
        FormsModule,
   //     NotificationModule,
   NgCircleProgressModule.forRoot({
    "radius": 60,
    "space": -10,
    "outerStrokeGradient": true,
    "outerStrokeWidth": 10,
    "outerStrokeColor": "#4882c2",
    "outerStrokeGradientStopColor": "#53a9ff",
    "innerStrokeColor": "#e7e8ea",
    "innerStrokeWidth": 10,
    "title": "Likes %",
    "animateTitle": false,
    "animationDuration": 1000,
    "showUnits": false,
    "showBackground": false,
    "clockwise": false,
    "startFromZero": false,
    "lazy": true}),

//...

         
         
    BrowserAnimationsModule,
    MatInputModule,
    MatProgressSpinnerModule,
   
       
    ], 
    providers: [provideHttpClient(withInterceptorsFromDi())] })
export class AppModule { }
