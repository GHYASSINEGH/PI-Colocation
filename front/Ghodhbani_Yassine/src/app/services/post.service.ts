import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Announcement } from '../models/announcement';
import { Likes } from '../models/likes';
import { Comment } from '../models/comment';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class PostService {
  private apiUrl = 'http://localhost:8080/posts';

  constructor(private http: HttpClient) {}

  addAnnouncement( announcement: any): Observable<any> {
    return this.http.post<any>(`${this.apiUrl}/addPostWithPhoto`, announcement);
  }

  updateAnnouncement(postId: number, announcement: Announcement): Observable<Announcement> {
    return this.http.put<Announcement>(`${this.apiUrl}/updatePost/${postId}`, announcement);
  }

  deleteAnnouncement(postId: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/delete/${postId}`);
  }

  getAnnouncements(): Observable<Announcement[]> {
    return this.http.get<Announcement[]>(`${this.apiUrl}/allPost`);
  }

  getAnnouncementById(id: number): Observable<Announcement> {
    return this.http.get<Announcement>(`${this.apiUrl}/postby/${id}`);
  }

  likeAnnouncement(postId: number, userId: number): Observable<void> {
    return this.http.post<void>(`${this.apiUrl}/${postId}/${userId}/like`, {});
  }

  getPostLikes(): Observable<Likes[]> {
    return this.http.get<Likes[]>(`${this.apiUrl}/allPostlikes`);
  }

  getPostLikeById(id: number): Observable<Likes> {
    return this.http.get<Likes>(`${this.apiUrl}/postlike-by/${id}`);
  }

  getTopLikedAnnouncements(): Observable<Announcement[]> {
    return this.http.get<Announcement[]>(`${this.apiUrl}/top-liked`);
  }

  addComment(postId: number, userId: number, content: any): Observable<any> {
    const comment = { content };
    const headers = new HttpHeaders({
      'Content-Type': 'application/json'
    });
    
    return this.http.post<Comment>(`${this.apiUrl}/${postId}/${userId}/addComment`, comment, { headers });
  }

  getPostComments(postId:any): Observable<Comment[]> {
    return this.http.get<Comment[]>(`${this.apiUrl}/allComment/${postId}`);
  }

  getPostCommentById(id: number): Observable<Comment> {
    return this.http.get<Comment>(`${this.apiUrl}/comment/${id}`);
  }

//get User By id

getuserById(id: number): Observable<Announcement> {
  return this.http.get<Announcement>(`${this.apiUrl}/userById/${id}`);
}


SearchPostByTitleOrContent(keyword: string): Observable<any> {
  return this.http.get<any>(`${this.apiUrl}/search`, {
    params: { keyword }
  });
}

updatePost(body:any,postId: number): Observable<any> {
  const headers = new HttpHeaders({
    'Content-Type': 'application/json'
  });
  
 
  return this.http.patch<any>(`${this.apiUrl}/update/${postId}`,body,{
    headers: headers
  });
}


getPostDetails(id:number){ 
  const headers = new HttpHeaders({
    'Content-Type': 'application/json'
  });
   

  return this.http.get(`${this.apiUrl}/postby/${id}`,{
    headers: headers
  });
}


getPostsProgression(){
 
  const headers = new HttpHeaders({
    'Content-Type': 'application/json'
  });

  return this.http.get(`${this.apiUrl}/details/likePercentage`,{
    headers: headers
  });
}

}