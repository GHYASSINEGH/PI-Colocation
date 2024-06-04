import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Announcement } from 'src/app/models/announcement';
import { Comment } from 'src/app/models/comment';

import { PostService } from 'src/app/services/post.service';
import * as XLSX from 'xlsx';
@Component({
  selector: 'app-posts-list',
  templateUrl:'./posts-list.component.html' ,
  styleUrls: ['./posts-list.component.css']
})
export class PostsListComponent implements OnInit {

  announcements: Announcement[] = [];
  userId = 2;
  users:any;
  comment!:Comment;
  comments: { [key: number]: Comment[] } = {};
  newComment: any = '';
  

  constructor(private announcementService: PostService , private router: Router
    
  ) {}

  ngOnInit(): void {
    this.loadAnnouncements();
    this.findUser();
   // this.getPostComments();
   
  }

  loadAnnouncements(): void {
    this.announcementService.getAnnouncements().subscribe(
      
      data => this.announcements = data,
      error => console.error('Error fetching announcements', error)
      
    );
    console.log(this.announcements);
  }

  deleteAnnouncement(postId: number): void {
    this.announcementService.deleteAnnouncement(postId).subscribe(
      () => {
        this.announcements = this.announcements.filter(a => a.id !== postId);
        console.log('Announcement deleted successfully');
        
      },
      error => console.error('Error deleting announcement', error)
    );
  }

  likeAnnouncement(postId: number, userId: number): void {
    this.announcementService.likeAnnouncement(postId, userId).subscribe(
      () => {
        console.log('Announcement liked successfully');
        this.loadAnnouncements();
      },
      error => console.error('Error liking announcement', error)
    );
  }


  getPostComments(postId: number): void {
    if (!this.comments[postId]) {
      this.announcementService.getPostComments(postId).subscribe(
        data => this.comments[postId] = data,
        error => console.error('Error fetching comments', error)
      );
    }
  }
  


  addComment(postId: number): void {
    if (this.newComment.trim()) {
      this.announcementService.addComment(postId, this.userId, this.newComment.trim()).subscribe(
        (newComment: Comment) => {
          console.log('Comment added successfully');
          if (!this.comments[postId]) {
            this.comments[postId] = [];
            
          }
          this.comments[postId].push(newComment);  // Add new comment to the list
          this.newComment = '';  // Clear the input field
          
        },
        error => console.error('Error adding comment', error)
      );
    } else {
      console.log('Comment is empty');
    }
   
  }


  findUser(): void {
    this.announcementService.getuserById(this.userId).subscribe(
      data => this.users=data,
      error => console.error('Error fetching user', error)
    );
    
  }


  searchAnnouncements(keyword: string): void {
    if (keyword.trim()) {
      this.announcementService.SearchPostByTitleOrContent(keyword.trim()).subscribe(
        data => this.announcements = data,
        error => console.error('Error searching announcements', error)
      );
    } else {
      this.loadAnnouncements();  
    }
  }


   /**Default name for excel file when download**/
   fileName = 'ExcelSheet.xlsx';

   exportexcel() {
     /**passing table id**/
     let data = document.getElementById('table-data');
     const ws: XLSX.WorkSheet = XLSX.utils.table_to_sheet(data);
 
     /**Generate workbook and add the worksheet**/
     const wb: XLSX.WorkBook = XLSX.utils.book_new();
     XLSX.utils.book_append_sheet(wb, ws, 'Sheet1');
 
     /*save to file*/
     XLSX.writeFile(wb, this.fileName);
   }
  
}
