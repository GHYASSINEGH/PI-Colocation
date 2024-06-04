import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { PostsListComponent } from './pages/posts-list/posts-list.component';
import { AddPostComponent } from './pages/add-post/add-post.component';
import { EditPostComponent } from './pages/edit-post/edit-post.component';
import { PostsStatisticComponent } from './pages/posts-statistic/posts-statistic.component';

const routes: Routes = [
  { path: 'posts', component: PostsListComponent },
  { path: 'posts/add/:userId', component: AddPostComponent },
  { path: 'posts/update/:id', component: EditPostComponent },
  { path: 'posts/statistics', component: PostsStatisticComponent },
  { path: '', redirectTo: '/posts', pathMatch: 'full' }, 
  { path: '**', redirectTo: '/posts', pathMatch: 'full' } 
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
